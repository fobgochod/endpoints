package com.fobgochod.endpoints.action.toolbar

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class RefreshTreeAction : EndpointsAction() {

    init {
        templatePresentation.text = message("action.toolbar.refresh.text")
        templatePresentation.icon = AllIcons.Actions.Refresh
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val toolWindow = EndpointsManager.getView(project)
        toolWindow.refresh()
    }
}
