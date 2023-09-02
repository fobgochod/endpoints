package com.fobgochod.endpoints.action.tree

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class CopyFullUrlAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Actions.Copy
        templatePresentation.text = message("action.editor.copy.url.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val selectPath = PsiFileUtils.getSelectedPath(project)
        if (selectPath is EndpointNode) {
            PsiFileUtils.copyPath(project, selectPath.source, true)
        }
    }

    override fun isVisible(e: AnActionEvent): Boolean {
        val selectPath = e.project?.let { PsiFileUtils.getSelectedPath(it) }
        return selectPath is EndpointNode
    }
}
