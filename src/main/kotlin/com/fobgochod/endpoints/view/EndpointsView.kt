package com.fobgochod.endpoints.view

import com.fobgochod.endpoints.action.toolbar.ToolbarActionGroup
import com.fobgochod.endpoints.domain.node.ModuleEntity
import com.fobgochod.endpoints.domain.node.RootEntity
import com.fobgochod.endpoints.framework.EntityUtils
import com.fobgochod.endpoints.framework.TreeUtils
import com.fobgochod.endpoints.view.filter.FilterType
import com.fobgochod.endpoints.view.http.EndpointsTestPane
import com.fobgochod.endpoints.view.tree.EndpointsTreePane
import com.fobgochod.endpoints.view.tree.node.ModuleNode
import com.fobgochod.endpoints.view.tree.node.RootNode
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.components.Service
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.psi.PsiClass
import com.intellij.ui.JBSplitter
import com.intellij.util.concurrency.AppExecutorUtil
import java.util.concurrent.Callable
import java.util.function.Consumer

/**
 * EndpointsView
 *
 * @author fobgochod
 * @date 2023/7/24 21:24
 * @see com.intellij.packageDependencies.ui.FileTreeModelBuilder
 * @see com.intellij.ide.projectView.impl.nodes.ProjectViewDirectoryHelper
 */
@Service(Service.Level.PROJECT)
class EndpointsView(val project: Project) : SimpleToolWindowPanel(true), Disposable {

    val treePanel: EndpointsTreePane
    val testPanel: EndpointsTestPane

    init {
        val toolbar = initToolbar()
        setToolbar(toolbar.component)

        treePanel = EndpointsTreePane(project)
        testPanel = EndpointsTestPane(project)

        val content = JBSplitter(true, EndpointsView::class.java.name, 0.5f)
        content.firstComponent = treePanel
        content.secondComponent = testPanel


        setContent(content)

        DumbService.getInstance(project).smartInvokeLater {
            FilterType.init(project)
            refresh()
        }
    }

    fun refresh() {
        ReadAction.nonBlocking(Callable { EntityUtils.getModuleClasses(project) })
            .inSmartMode(project)
            .finishOnUiThread(ModalityState.defaultModalityState(), this::refreshTree)
            .submit(AppExecutorUtil.getAppExecutorService())
    }

    private fun initToolbar(): ActionToolbar {
        val actionGroup =
            ActionManager.getInstance().getAction(ToolbarActionGroup::class.java.name) as ActionGroup
        val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, actionGroup, true)
        toolbar.targetComponent = this
        return toolbar
    }

    private fun refreshTree(moduleClassMap: Map<String, List<PsiClass>>) {
        ProgressManager.getInstance()
            .run(object : Task.Backgroundable(project, "Reload endpoints", false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = false

                    val producer = Callable<RootNode> {
                        if (indicator.isCanceled) {
                            return@Callable null
                        }
                        val root = RootNode(RootEntity.EMPTY)
                        indicator.text = "Initialize"

                        moduleClassMap.forEach { (module, moduleClasses) ->
                            val moduleNode = ModuleNode(ModuleEntity(module))
                            TreeUtils.buildTree(moduleNode, moduleClasses)
                            root.add(moduleNode)
                        }

                        indicator.text = "Waiting to re-render"
                        root
                    }

                    val consumer = Consumer<RootNode> {
                        treePanel.renderAll(it, true)
                    }

                    ReadAction.nonBlocking(producer)
                        .inSmartMode(project)
                        .finishOnUiThread(ModalityState.defaultModalityState(), consumer)
                        .submit(AppExecutorUtil.getAppExecutorService())
                }
            })
    }

    override fun dispose() {
        TreeUtils.endpointNodes.clear()
        FilterType.clear()
    }
}
