package com.fobgochod.endpoints.action

import com.fobgochod.endpoints.framework.PsiFileUtils
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware

open class EndpointsActionGroup : DefaultActionGroup(), DumbAware {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabledAndVisible = isAvailable(e)
    }

    protected open fun isAvailable(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasProject(e.dataContext)
    }
}
