@file:Suppress("UnstableApiUsage")

package com.fobgochod.endpoints.action.editor

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 *  @see com.intellij.ide.projectView.impl.SelectFileAction
 *  @see com.intellij.ide.projectView.impl.AsyncProjectViewSupport#selectPaths()
 *  @see com.intellij.ui.tree.CachingTreePath
 *
 * @author fobgochod
 * @date 2023/6/28 21:34
 */
class SelectEndpointAction : EndpointsAction() {

    init {
        templatePresentation.text = EndpointsBundle.message("action.editor.navigate.view.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val psiMethod = PsiFileUtils.getPsiMethod(e) ?: return
        val view = EndpointsManager.getView(project)
        EndpointsManager.show(project) { view.treePanel.navigationToTree(psiMethod) }
    }

    override fun isEnabled(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasPsiMethod(e)
    }
}
