package com.fobgochod.endpoints.action.http

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.EndpointsManager
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class ResetDataAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.General.Reset
        templatePresentation.text = EndpointsBundle.message("action.http.test.reset.data.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val selectPath = PsiFileUtils.getSelectedPath(project)
        if (selectPath is EndpointNode) {
            selectPath.source.reset(true)
            val view = EndpointsManager.getView(project)
            view.testPanel.apply(selectPath.source)
        }
    }
}
