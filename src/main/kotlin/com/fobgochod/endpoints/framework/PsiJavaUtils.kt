package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.spring.SpringController
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.intellij.lang.jvm.annotation.*
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.impl.PsiClassImplUtil
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.containers.stream
import kotlin.jvm.optionals.getOrNull

object PsiJavaUtils {

    fun findPsiClass(project: Project, qualifiedName: String): PsiClass? {
        return JavaPsiFacade.getInstance(project)
                .findClass(qualifiedName, GlobalSearchScope.allScope(project))
    }

    fun getPackageName(psiClass: PsiClass): String {
        return when (val psiElement = psiClass.parent) {
            is PsiJavaFile -> psiElement.packageName
            is PsiClass -> getPackageName(psiElement)
            else -> ""
        }
    }

    fun getClass(project: Project, module: Module): List<PsiClass> {
        val moduleScope = if (EndpointsSettings.instance.scanLibrary) {
            module.moduleWithLibrariesScope
        } else {
            module.moduleScope
        }
        return SpringController.values()
                .flatMap { anno ->
                    JavaAnnotationIndex.getInstance()
                            .get(anno.name, project, moduleScope)
                            .filter { it.parent is PsiModifierList && it.parent.parent is PsiClass }
                            .map { it.parent.parent as PsiClass }
                }.toList()
    }

    fun getClass(psiFile: PsiFile): PsiClass? {
        return psiFile.children.stream()
                .filter { it is PsiClass }
                .map { it as PsiClass }
                .findFirst().getOrNull()
    }

    fun getInnerClass(psiClass: PsiClass): List<PsiClass> {
        return PsiClassImplUtil.getAllInnerClasses(psiClass).stream()
                .filter { !it.isAnnotationType && !it.isEnum }
                .toList()
    }

    /**
     * 获取方法的所有注解（包括父类）
     * 当子类和父类方法同时存在注解时只保留子类注解(即实现类的方法注解)
     */
    fun getMethodAnnotations(psiMethod: PsiMethod): List<PsiAnnotation> {
        val annotations = psiMethod.modifierList.annotations.toMutableList()
        psiMethod.findSuperMethods()
                .flatMap { getMethodAnnotations(it) }
                .filter { !annotations.contains(it) }
                .forEach(annotations::add)
        return annotations
    }

    /**
     * 根据注解名称或者全限定名获取Class上注解
     */
    fun getClassAnnotation(psiClass: PsiClass, vararg qualifiedName: String): PsiAnnotation? {
        // 当前类上查找
        val annotation = qualifiedName.firstNotNullOfOrNull { psiClass.getAnnotation(it) }
        if (annotation != null) {
            return annotation
        }
        // 父类上查找
        val annotation2 = psiClass.superClass?.let { getClassAnnotation(it, *qualifiedName) }
        if (annotation2 != null) {
            return annotation2
        }
        // 接口上查找
        return psiClass.interfaces.firstNotNullOfOrNull { getClassAnnotation(it, *qualifiedName) }
    }

    fun getAnnotationValue(annotation: PsiAnnotation, attrName: String): String? {
        return annotation.attributes
                .filter { it.attributeName == attrName }
                .map { getAttributeValue(it.attributeValue) }
                .filterIsInstance<String>()
                .firstOrNull()
    }

    /**
     * 获取指定注解指定属性的值
     */
    fun getAnnotationValue(attribute: JvmAnnotationAttribute, vararg attrNames: String): Any? {
        if (!attrNames.contains(attribute.attributeName)) {
            return null
        }
        return getAttributeValue(attribute.attributeValue)
    }

    /**
     * 获取注解属性的值
     *
     * @param attributeValue Psi属性
     * @return {Object | List}
     */
    private fun getAttributeValue(attributeValue: JvmAnnotationAttributeValue?): Any? {
        when (attributeValue) {
            null -> {
                return null
            }

            is JvmAnnotationConstantValue -> {
                return attributeValue.constantValue
            }

            is JvmAnnotationEnumFieldValue -> {
                return attributeValue.fieldName
            }

            is JvmAnnotationArrayValue -> {
                val values = attributeValue.values
                val list: MutableList<Any> = java.util.ArrayList(values.size)
                values.forEach {
                    val value = getAttributeValue(it)
                    if (value != null) {
                        list.add(value)
                    } else {
                        try {
                            // 如果是jar包里的JvmAnnotationConstantValue则无法正常获取值
                            val clazz: Class<out JvmAnnotationAttributeValue> = it.javaClass
                            val myElement = clazz.superclass.getDeclaredField("myElement")
                            myElement.isAccessible = true
                            val ele = myElement[it]
                            if (ele is PsiExpression) {
                                list.add(ele.text)
                            }
                        } catch (ignore: Exception) {
                        }
                    }
                }
                return list
            }

            is JvmAnnotationClassValue -> {
                return attributeValue.qualifiedName
            }

            else -> {
                return null
            }
        }
    }
}
