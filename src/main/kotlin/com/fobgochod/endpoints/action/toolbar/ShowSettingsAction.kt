package com.fobgochod.endpoints.action.toolbar

import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.intellij.icons.AllIcons
import com.intellij.ide.IdeBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil

class ShowSettingsAction : AnAction() {

    init {
        templatePresentation.text = IdeBundle.message("settings.entry.point.tooltip")
        templatePresentation.icon = AllIcons.General.Settings
    }

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.project, message("configurable.display.name"))
    }
}
