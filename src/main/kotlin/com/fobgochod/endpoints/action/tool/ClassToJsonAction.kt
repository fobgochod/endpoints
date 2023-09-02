package com.fobgochod.endpoints.action.tool

import com.fobgochod.endpoints.action.EndpointsAction
import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.util.EndpointsBundle
import com.fobgochod.endpoints.util.EndpointsToolkit
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.asJava.LightClassUtil.canGenerateLightClass
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.psi.KtClassOrObject

class ClassToJsonAction : EndpointsAction() {

    init {
        templatePresentation.icon = AllIcons.Json.Object
        templatePresentation.text = EndpointsBundle.message("action.tool.class.json.text")
    }

    override fun actionPerformed(e: AnActionEvent, project: Project) {
        val psiElement = e.getData(CommonDataKeys.PSI_ELEMENT) ?: return
        val psiClass = getPsiClass(psiElement) ?: return

        val json = JsonHelper.classToJson(psiClass)
        EndpointsToolkit.copy(json)
    }

    private fun getPsiClass(psiElement: PsiElement): PsiClass? {
        if (psiElement is PsiClass) {
            return psiElement
        } else if (psiElement is KtClassOrObject) {
            if (canGenerateLightClass(psiElement)) {
                return psiElement.toLightClass()
            }
        }
        return null
    }

    override fun isEnabled(e: AnActionEvent): Boolean {
        return PsiFileUtils.hasPsiClass(e)
    }
}
