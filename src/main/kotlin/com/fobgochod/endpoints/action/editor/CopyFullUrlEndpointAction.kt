package com.fobgochod.endpoints.action.editor

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class CopyFullUrlEndpointAction : EndpointsAction() {

    init {
        templatePresentation.text = EndpointsBundle.message("action.editor.copy.url.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        PsiFileUtils.copyPath(e, true)
    }

    override fun isEnabled(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasPsiMethod(e)
    }
}
