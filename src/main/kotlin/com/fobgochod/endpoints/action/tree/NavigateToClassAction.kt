package com.fobgochod.endpoints.action.tree

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.tree.node.ClassNode
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * NavigateToClassAction
 *
 * @author fobgochod
 * @date 2023/7/23 19:04
 * @see com.intellij.ide.projectView.impl.SelectFileAction
 */
class NavigateToClassAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Nodes.Class
        templatePresentation.text = EndpointsBundle.message("action.tree.navigate.class.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        PsiFileUtils.getSelectedPath(project)?.navigate(true)
    }

    override fun isVisible(e: AnActionEvent): Boolean {
        val selectPath = e.project?.let { PsiFileUtils.getSelectedPath(it) }
        return selectPath is ClassNode
    }
}
