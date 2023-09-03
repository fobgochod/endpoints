package com.fobgochod.endpoints.util

import com.fobgochod.endpoints.domain.spring.RequestParams
import com.fobgochod.endpoints.framework.PsiJavaUtils
import com.fobgochod.mock.Mock
import com.fobgochod.mock.enums.BaseType
import com.intellij.lang.jvm.JvmParameter
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiClassReferenceType
import kotlin.reflect.jvm.jvmName

@Suppress("UnstableApiUsage")
object ParamUtils {

    fun classToJson(psiClass: PsiClass): String {
        val jsonMap = classToMap(psiClass, true)
        return GsonUtils.toJson(jsonMap)
    }

    fun getRequestBody(psiMethod: PsiMethod, requestParams: RequestParams): String {
        val parameters = psiMethod.parameters
        val jvmType = parameters
                .filter { getParameterAnnotation(it, requestParams) != null }
                .map { it.type }
                .firstOrNull()
        if (jvmType is PsiType) {
            val obj = if (jvmType is PsiClassReferenceType) {
                mockField(jvmType, jvmType.resolve(), true)
            } else {
                mockField(jvmType)
            }
            GsonUtils.toJson(obj)
        }
        return ""
    }

    fun getRequestParams(psiMethod: PsiMethod, requestParams: RequestParams): Map<String, Any> {
        val parameters = psiMethod.parameters
        val params = mutableMapOf<String, Any>()
        for (parameter in parameters) {
            val annotation = getParameterAnnotation(parameter, requestParams)
            val jvmType = parameter.type
            if (annotation != null && jvmType is PsiType) {
                val name = PsiJavaUtils.getAnnotationValue(annotation, "name") ?: parameter.name
                val value = mockField(jvmType)
                if (name != null && value != null) {
                    params[name] = value
                }
            }
        }
        return params
    }

    private fun getJavaBaseTypeValue(paramType: String): Any? {
        val baseType = BaseType.get(paramType) ?: return null
        return Mock.mock(baseType.clazz)
    }

    private fun getParameterAnnotation(parameter: JvmParameter, annotation: RequestParams): PsiAnnotation? {
        return parameter.annotations
                .filter { it.qualifiedName == annotation.qualifiedName }
                .map { it as PsiAnnotation }
                .firstOrNull()
    }


    private fun classToMap(psiClass: PsiClass, recursion: Boolean): Map<String, Any?> {
        val attributes: MutableMap<String, Any?> = mutableMapOf()
        for (field in psiClass.fields) {
            val psiFieldType = field.type
            val fieldName = field.name

            val filedValue = mockField(psiFieldType, psiClass, recursion)
            attributes[fieldName] = filedValue
        }
        return attributes
    }

    private fun mockField(fieldType: PsiType, psiClass: PsiClass? = null, recursion: Boolean = false): Any? {
        val qualifiedName = fieldType.canonicalText

        if (fieldType is PsiPrimitiveType) {
            return getJavaBaseTypeValue(qualifiedName)
        } else if (BaseType.contains(qualifiedName)) {
            return getJavaBaseTypeValue(qualifiedName)
        } else if (fieldType is PsiArrayType) {
            val psiType = fieldType.componentType
            val value = mockField(psiType)
            return if (value == null) emptyList() else listOf(value)
        } else if (fieldType is PsiClassReferenceType) {
            val fieldClass = fieldType.resolve()
            if (fieldClass == null) {
                return null
            } else if (fieldClass.isEnum) {
                val ordinal = (0 until fieldClass.fields.size).random()
                val psiField = fieldClass.fields[ordinal]
                return psiField.name
            } else if (isClass(fieldType, List::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val value = mockField(parameters[0])
                    return if (value == null) emptyList() else listOf(value)
                }
            } else if (isClass(fieldType, Set::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val value = mockField(parameters[0])
                    return if (value == null) emptySet() else setOf(value)
                }
            } else if (isClass(fieldType, Map::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val key = mockField(parameters[0])
                    val value = mockField(parameters[1])
                    return mapOf(key to value)
                }
            } else {
                if (fieldClass == psiClass && !recursion) {
                    // if (isClass(fieldType, psiClass.qualifiedName) && !recursion) {
                    // self recursion
                } else {
                    return classToMap(fieldClass, false)
                }
            }
        }
        return null
    }

    /**
     * 字段是否为指定类型
     */
    private fun isClass(psiFieldType: PsiClassReferenceType, qualifiedName: String?): Boolean {
        val fieldClass = psiFieldType.resolve() ?: return false
        if (fieldClass.qualifiedName == qualifiedName) {
            return true
        }

        for (psiType in psiFieldType.rawType().superTypes) {
            if (psiType.canonicalText == qualifiedName) {
                return true
            }
        }
        return false
    }
}
