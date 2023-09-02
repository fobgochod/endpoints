package com.fobgochod.endpoints.action.editor

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.domain.node.RootEntity
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.framework.TreeUtils
import com.fobgochod.endpoints.view.tree.EndpointsTreePane
import com.fobgochod.endpoints.view.tree.node.RootNode
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.treeStructure.SimpleTree
import java.awt.Dimension

class ShowClassEndpointAction : EndpointsAction() {

    private val singleton = "singleton"
    private val singletonPane = mutableMapOf<String, EndpointsTreePane>()

    init {
        templatePresentation.text = EndpointsBundle.message("action.editor.show.current.class.endpoints.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val psiClass = PsiFileUtils.getPsiClass(e) ?: return

        val root = RootNode(RootEntity.EMPTY_CLASS)
        TreeUtils.buildTree(root, psiClass)

        val treePanel = getSingleton(project)
        treePanel.renderAll(root, true)
        val popup = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(treePanel, null)
            .setTitle(psiClass.name ?: root.source.name)
            .setResizable(true)
            .setMovable(true)
            .createPopup()
        popup.setMinimumSize(Dimension(300, 0))
        popup.showInFocusCenter()
    }

    override fun isEnabled(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasPsiClass(e)
    }

    private fun getSingleton(project: Project): EndpointsTreePane {
        var treePane = singletonPane[singleton]
        if (treePane == null) {
            treePane = EndpointsTreePane(project, SimpleTree())
            singletonPane[singleton] = treePane
        }
        return treePane
    }


}
