package com.fobgochod.endpoints.action.toolbar.tree

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class CollapseTreeAction : EndpointsAction() {

    init {
        templatePresentation.text = EndpointsBundle.message("action.toolbar.tree.collapse.all.text")
        templatePresentation.icon = AllIcons.Actions.Collapseall
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val view = EndpointsManager.getView(project)
        view.treePanel.collapseAll()
    }
}
