package com.fobgochod.endpoints.action.toolbar.tree

import com.fobgochod.endpoints.action.EndpointsToggleAction
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class ToggleScanLibraryAction : EndpointsToggleAction() {

    private val state = EndpointsSettings.instance

    init {
        templatePresentation.text = message("action.toolbar.tree.enable.library.scanning.text")
        templatePresentation.icon = AllIcons.ObjectBrowser.ShowLibraryContents
    }

    override fun doIsSelected(e: AnActionEvent): Boolean {
        return state.scanLibrary
    }

    override fun setSelected(e: AnActionEvent, project: Project, state: Boolean) {
        this.state.scanLibrary = state
        val toolWindow = EndpointsManager.getView(project)
        toolWindow.refresh()
    }
}
