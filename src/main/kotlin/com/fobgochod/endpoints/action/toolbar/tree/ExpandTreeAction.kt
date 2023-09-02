package com.fobgochod.endpoints.action.toolbar.tree

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class ExpandTreeAction : EndpointsAction() {

    init {
        templatePresentation.text = EndpointsBundle.message("action.toolbar.tree.expand.all.text")
        templatePresentation.icon = AllIcons.Actions.Expandall
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val view = EndpointsManager.getView(project)
        view.treePanel.expandAll()
    }
}
