package com.fobgochod.endpoints.view.http

import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.GsonUtils
import com.fobgochod.endpoints.view.http.editor.HttpTextField
import com.fobgochod.endpoints.view.http.listener.SendRequestListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import java.util.*
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextPane

class EndpointsTestPane(val project: Project) : JPanel(BorderLayout()) {

    private val state = EndpointsSettings.instance

    private val topPane = JPanel(BorderLayout())
    val httpMethod = ComboBox(DefaultComboBoxModel(Vector(HttpMethod.values().toList())))
    val httpServer = JBTextField(state.httpServers.firstOrNull())
    private val sendRequest = JButton(message("endpoint.http.test.button.text"))

    val tabbedPane = JBTabbedPane()
    val paramsPane = HttpTextField(project)
    val pathsPane = HttpTextField(project)
    val headersPane = HttpTextField(project)
    val bodyPane = HttpTextField(project)
    val responsePane = HttpTextField(project)
    val console = JTextPane()

    init {
        topPane.add(httpMethod, BorderLayout.WEST)
        topPane.add(httpServer, BorderLayout.CENTER)
        topPane.add(sendRequest, BorderLayout.EAST)
        add(topPane, BorderLayout.NORTH)

        sendRequest.addActionListener(SendRequestListener(this))

        tabbedPane.add(message("endpoint.http.test.tab1.title"), paramsPane)
        tabbedPane.add(message("endpoint.http.test.tab2.title"), pathsPane)
        tabbedPane.add(message("endpoint.http.test.tab3.title"), headersPane)
        tabbedPane.add(message("endpoint.http.test.tab4.title"), bodyPane)
        tabbedPane.add(message("endpoint.http.test.tab5.title"), responsePane)
        tabbedPane.add(message("endpoint.http.test.tab6.title"), console)
        add(tabbedPane, BorderLayout.CENTER)
        updateUI()
    }

    fun reset(entity: EndpointEntity) {
        httpMethod.selectedItem = entity.method
        httpServer.text = entity.getRequestUrl()

        paramsPane.text = GsonUtils.toJson(entity.params)
        pathsPane.text = GsonUtils.toJson(entity.paths)
        headersPane.text = GsonUtils.toJson(entity.headers)
        bodyPane.text = entity.body
    }

    fun apply(entity: EndpointEntity) {
        entity.clear()
        entity.params.putAll(GsonUtils.toMap(paramsPane.text))
        entity.paths.putAll(GsonUtils.toMap(pathsPane.text))
        entity.headers.putAll(GsonUtils.toMap(headersPane.text))
        entity.body = bodyPane.text
    }

    fun empty() {
        httpMethod.selectedItem = HttpMethod.GET
        httpServer.text = state.getUri("")

        paramsPane.text = "{}"
        pathsPane.text = "{}"
        headersPane.text = "{}"
        bodyPane.text = ""
        responsePane.text = ""
    }
}

