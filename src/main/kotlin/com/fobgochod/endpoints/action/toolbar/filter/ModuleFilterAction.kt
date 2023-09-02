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

class ModuleFilterAction : EndpointsAction() {

    init {
        templatePresentation.text = message("action.toolbar.filter.module.text")
        templatePresentation.icon = AllIcons.Actions.Properties
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val module = FilterPanel(project, FilterType.MODULE_MAP)
        val button = e.inputEvent.component
        module.popup.show(RelativePoint(button, Point(0, button.height)))
    }
}



