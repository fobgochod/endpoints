package com.fobgochod.endpoints.action.toolbar.filter

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.view.filter.FilterPanel
import com.fobgochod.endpoints.view.filter.FilterType
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.ui.awt.RelativePoint
import java.awt.Point

class HttpMethodFilterAction : EndpointsAction() {

    init {
        templatePresentation.text = message("action.toolbar.filter.http.method.text")
        templatePresentation.icon = AllIcons.General.Filter
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val method = FilterPanel(project, FilterType.METHOD_MAP)
        val button = e.inputEvent.component
        method.popup.show(RelativePoint(button, Point(0, button.height)))
    }
}



