package com.fobgochod.endpoints.action.tree

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.fobgochod.endpoints.view.tree.node.MethodNode
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class NavigateToMethodAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Nodes.Method
        templatePresentation.text = EndpointsBundle.message("action.tree.navigate.method.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        PsiFileUtils.getSelectedPath(project)?.navigate(true)
    }

    override fun isVisible(e: AnActionEvent): Boolean {
        val selectPath = e.project?.let { PsiFileUtils.getSelectedPath(it) }
        return selectPath is MethodNode || selectPath is EndpointNode
    }
}
