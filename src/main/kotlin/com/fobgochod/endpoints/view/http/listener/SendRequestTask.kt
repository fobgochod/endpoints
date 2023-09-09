package com.fobgochod.endpoints.view.http.listener

import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsNotify
import com.fobgochod.endpoints.util.GsonUtils
import com.fobgochod.endpoints.util.PathUtils
import com.fobgochod.endpoints.view.http.EndpointsTestPane
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.json.JsonFileType
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.util.ExceptionUtil
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration

/**
 *  send http request
 *
 * @author fobgochod
 * @date 2023/8/20 21:11
 * @see com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.InstallPluginTask
 * @see com.intellij.packageDependencies.ui.FileTreeModelBuilder
 * @see com.intellij.diagnostic.hprof.action.HeapDumpSnapshotRunnable.CaptureHeapDumpTask
 */
internal class SendRequestTask(private val testPane: EndpointsTestPane) :
        Task.Backgroundable(testPane.project, message("endpoint.http.test.task.title"), true) {

    private val state = EndpointsSettings.instance
    private var method: HttpMethod = HttpMethod.GET
    private var url = ""
    private lateinit var response: HttpResponse<String>
    private val sendingRunnable = Runnable {
        val indicator = ProgressManager.getInstance().progressIndicator
        if (indicator != null) {
            indicator.isIndeterminate = false
        }

        val uri = testPane.httpServer.text
        val body = testPane.bodyPane.text
        val params = GsonUtils.toMap(testPane.paramsPane.text)
        val param = if (params.isEmpty()) "" else "?" + params.entries.joinToString("&") {
            it.key + "=" + it.value
        }

        val paths = GsonUtils.toMap(testPane.pathsPane.text).entries.associate {
            it.key to it.value.toString()
        }

        val headers = GsonUtils.toMap(testPane.headersPane.text).entries.associate {
            it.key to it.value.toString()
        }

        method = testPane.httpMethod.selectedItem as HttpMethod
        url = PathUtils.replace(uri, paths) + param

        val builder = HttpRequest.newBuilder().uri(URI.create(url))
                .method(method.name, HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))

        val timeout = state.httpTimeout.toLong()
        if (timeout > 0) {
            builder.timeout(Duration.ofSeconds(timeout))
        }

        headers.forEach { builder.header(it.key, it.value) }
        response = HttpClient.newHttpClient()
                .send(builder.build(), HttpResponse.BodyHandlers.ofString())
    }


    override fun run(indicator: ProgressIndicator) {
        sendingRunnable.run()
    }

    override fun onSuccess() {
        testPane.tabbedPane.selectedComponent = testPane.responsePane.component
        testPane.responsePane.setText(response.body(), JsonFileType.INSTANCE)

        val message = method.name + " - " + response.statusCode() + " - " + url
        EndpointsNotify.info(testPane.console, message)
        EndpointsNotify.info(message)

        val selectedPath = PsiFileUtils.getSelectedPath(project)
        if (selectedPath is EndpointNode) {
            testPane.apply(selectedPath.source)
        }
    }

    override fun onThrowable(error: Throwable) {
        testPane.tabbedPane.selectedComponent = testPane.responsePane.component
        testPane.responsePane.setText(ExceptionUtil.getThrowableText(error), JsonFileType.INSTANCE)
        EndpointsNotify.error(testPane.console, method.name + " - " + url)
        EndpointsNotify.error(method.name + " - " + url)

    }
}