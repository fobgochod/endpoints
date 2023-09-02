package com.fobgochod.endpoints.action

import com.fobgochod.endpoints.framework.PsiFileUtils
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project

abstract class EndpointsAction : AnAction(), DumbAware {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val p = e.presentation
        p.isEnabled = isEnabled(e)
        p.isVisible = isVisible(e)
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        this.actionPerformed(e, project)
    }

    protected open fun isEnabled(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasProject(e.dataContext)
    }

    protected open fun isVisible(e: AnActionEvent): Boolean {
        return true
    }

    protected open fun actionPerformed(e: AnActionEvent, project: Project) {
    }
}
