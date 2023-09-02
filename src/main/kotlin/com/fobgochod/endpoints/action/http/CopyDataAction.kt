package com.fobgochod.endpoints.action.http

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.util.EndpointsToolkit
import com.fobgochod.endpoints.view.http.editor.HttpTextField
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.project.Project

class CopyDataAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Actions.Copy
        templatePresentation.text = EndpointsBundle.message("action.http.test.copy.data.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val component = e.dataContext.getData(PlatformCoreDataKeys.CONTEXT_COMPONENT)
        if (component is HttpTextField) {
            EndpointsToolkit.copy(component.text)
        }
    }
}
