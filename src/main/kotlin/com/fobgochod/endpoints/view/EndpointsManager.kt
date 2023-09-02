package com.fobgochod.endpoints.view

import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager

object EndpointsManager {

    fun getView(event: AnActionEvent) = event.project?.let { getView(it) }

    fun getView(project: Project): EndpointsView {
        return project.service<EndpointsView>()
    }

    private fun getWindow(project: Project): ToolWindow? {
        return ToolWindowManager.getInstance(project).getToolWindow(message("plugin.name"))
    }

    fun show(project: Project, runnable: Runnable?) {
        getWindow(project)?.show(runnable)
    }
}
