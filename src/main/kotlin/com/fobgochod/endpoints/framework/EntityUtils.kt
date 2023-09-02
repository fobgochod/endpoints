package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.view.filter.FilterType
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

object EntityUtils {

    private fun getModules(): Array<Module> {
        return FilterType.MODULE_MAP.entries.stream()
            .filter { it.value }
            .map { it.key }
            .toList()
            .toTypedArray()
    }

    fun getModuleClasses(project: Project): Map<String, List<PsiClass>> {
        val moduleClassMap = mutableMapOf<String, List<PsiClass>>()
        for (module in getModules()) {
            val classes = PsiJavaUtils.getClass(project, module)
            if (classes.isNotEmpty()) {
                moduleClassMap[module.name] = classes
            }
        }
        return moduleClassMap
    }

    fun getClassEndpoints(psiClass: PsiClass): List<EndpointEntity> {
        return Framework.getJava().flatMap {
            it.getMethod(psiClass)
        }
    }

    fun getMethodEndpoints(psiMethod: PsiMethod?): List<EndpointEntity> {
        val containingClass = psiMethod?.containingClass ?: return listOf()
        return getClassEndpoints(containingClass)
            .filter { it.psiMethod == psiMethod }
            .toList()
    }
}
