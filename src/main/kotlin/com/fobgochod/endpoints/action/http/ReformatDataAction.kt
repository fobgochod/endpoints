package com.fobgochod.endpoints.action.http

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.util.GsonUtils
import com.fobgochod.endpoints.view.http.editor.HttpTextField
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.project.Project

class ReformatDataAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Json.Object
        templatePresentation.text = EndpointsBundle.message("action.http.test.json.format.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val component = e.dataContext.getData(PlatformCoreDataKeys.CONTEXT_COMPONENT)
        if (component is HttpTextField) {
            component.text = GsonUtils.format(component.text)
        }
    }
}
