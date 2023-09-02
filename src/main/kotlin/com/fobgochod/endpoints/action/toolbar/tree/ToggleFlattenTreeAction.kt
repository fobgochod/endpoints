package com.fobgochod.endpoints.action.toolbar.tree

import com.fobgochod.endpoints.action.EndpointsToggleAction
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class ToggleFlattenTreeAction : EndpointsToggleAction() {

    private val state = EndpointsSettings.instance

    init {
        templatePresentation.text = message("action.toolbar.tree.flatten.endpoint.text")
        templatePresentation.icon = AllIcons.Actions.Minimap
    }

    override fun doIsSelected(e: AnActionEvent): Boolean {
        return state.showClass
    }

    override fun setSelected(e: AnActionEvent, project: Project, state: Boolean) {
        this.state.showClass = state
        val toolWindow = EndpointsManager.getView(project)
        toolWindow.refresh()
    }
}
