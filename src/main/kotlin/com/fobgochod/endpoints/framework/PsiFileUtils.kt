package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsNotify
import com.fobgochod.endpoints.util.EndpointsToolkit
import com.fobgochod.endpoints.view.EndpointsManager
import com.fobgochod.endpoints.view.tree.node.BaseNode
import com.fobgochod.endpoints.view.tree.listener.TreePopupCellRenderer
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.util.PsiUtilBase
import java.awt.Dimension

object PsiFileUtils {

    fun hasProject(context: DataContext): Boolean {
        return getProject(context) != null
    }

    private fun getProject(context: DataContext): Project? {
        return CommonDataKeys.PROJECT.getData(context)
    }

    fun hasPsiMethod(event: AnActionEvent): Boolean {
        return getPsiMethod(event) != null
    }

    fun getPsiMethod(event: AnActionEvent): PsiMethod? {
        val psiIdentifier = getEditorElement(event) ?: return null
        val psiMethod = psiIdentifier.context
        return if (psiMethod is PsiMethod) {
            psiMethod
        } else null
    }

    fun hasPsiClass(event: AnActionEvent): Boolean {
        return getPsiClass(event) != null
    }

    fun getPsiClass(event: AnActionEvent): PsiClass? {
        val psiFile = getPsiFile(event)
        if (psiFile != null) {
            return PsiJavaUtils.getClass(psiFile)
        }
        return null
    }

    private fun getPsiFile(event: AnActionEvent): PsiFile? {
        val psiIdentifier = getEditorElement(event)
        if (psiIdentifier != null) {
            return psiIdentifier.containingFile
        }
        return null
    }

    fun getSelectedPath(project: Project): BaseNode<*>? {
        val view = EndpointsManager.getView(project)
        return TreeUtils.getSelectedPath(view.treePanel.tree)
    }

    fun copyPath(project: Project?, endpoint: EndpointEntity, full: Boolean) {
        if (!full) {
            EndpointsToolkit.copy(endpoint.path)
            EndpointsNotify.info(message("action.editor.copy.path.success"), project)
        } else {
            EndpointsToolkit.copy(endpoint.getRequestUrl())
            EndpointsNotify.info(message("action.editor.copy.url.success"), project)
        }
    }

    fun copyPath(event: AnActionEvent, full: Boolean) {
        val endpoints = getMethodEndpoints(event)
        if (endpoints.isEmpty()) {
            EndpointsNotify.warning("Cannot find this endpoint")
        } else if (endpoints.size == 1) {
            copyPath(event.project, endpoints[0], full)
        } else {
            val popup = JBPopupFactory.getInstance()
                .createPopupChooserBuilder(endpoints)
                .setItemChosenCallback { selected -> copyPath(event.project, selected, full) }
                .setRenderer(TreePopupCellRenderer())
                .setTitle(message("action.editor.copy.select.one"))
                .setResizable(true)
                .setMovable(true)
                .setAdText(JBPopupFactory.ActionSelectionAid.SPEEDSEARCH.name)
                .setNamerForFiltering(EndpointEntity::path)
                .createPopup()
            popup.setMinimumSize(Dimension(300, 180))
            popup.showInBestPositionFor(event.dataContext)

        }
    }

    private fun getEditorElement(event: AnActionEvent): PsiElement? {
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return null
        return PsiUtilBase.getElementAtCaret(editor)
    }

    /**
     * 获取方法的API，Spring可能一个方法有多个API
     */
    private fun getMethodEndpoints(event: AnActionEvent): List<EndpointEntity> {
        val psiMethod = getPsiMethod(event)
        return EntityUtils.getMethodEndpoints(psiMethod)
    }
}
