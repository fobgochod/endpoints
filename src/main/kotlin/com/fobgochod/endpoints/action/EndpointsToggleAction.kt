package com.fobgochod.endpoints.action

import com.fobgochod.endpoints.framework.PsiFileUtils
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project

abstract class EndpointsToggleAction : ToggleAction(), DumbAware {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabled = isAvailable(e)
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        return if (!isAvailable(e)) false else doIsSelected(e)
    }

    abstract fun doIsSelected(e: AnActionEvent): Boolean

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val project = e.project ?: return
        this.setSelected(e, project, state)
    }

    protected open fun isAvailable(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasProject(e.dataContext)
    }

    protected open fun setSelected(e: AnActionEvent, project: Project, state: Boolean) {
    }
}
