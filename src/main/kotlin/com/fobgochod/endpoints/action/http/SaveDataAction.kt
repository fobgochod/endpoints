package com.fobgochod.endpoints.action.http

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.EndpointsManager
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class SaveDataAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Actions.MenuSaveall
        templatePresentation.text = EndpointsBundle.message("action.http.test.save.data.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val selectPath = PsiFileUtils.getSelectedPath(project)
        if (selectPath is EndpointNode) {
            val view = EndpointsManager.getView(project)
            view.testPanel.apply(selectPath.source)
        }
    }
}
