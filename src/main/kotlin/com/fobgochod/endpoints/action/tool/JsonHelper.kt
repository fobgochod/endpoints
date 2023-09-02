package com.fobgochod.endpoints.action.tool

import com.fobgochod.endpoints.domain.spring.RequestParams
import com.fobgochod.endpoints.framework.PsiJavaUtils
import com.fobgochod.endpoints.util.GsonUtils
import com.fobgochod.mock.Mock
import com.fobgochod.mock.enums.BaseType
import com.intellij.lang.jvm.types.JvmType
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiClassReferenceType
import kotlin.reflect.jvm.jvmName

@Suppress("UnstableApiUsage")
object JsonHelper {

    fun classToJson(psiClass: PsiClass): String {
        val jsonMap = classToMap(psiClass, true)
        return GsonUtils.toJson(jsonMap)
    }

    fun getRequestBody(psiMethod: PsiMethod): String {
        val jvmType = findRequestBody(psiMethod)
        if (jvmType is PsiType) {
            return if (jvmType is PsiClassReferenceType) {
                val jsonMap = mockField(jvmType, jvmType.resolve(), true)
                GsonUtils.toJson(jsonMap)
            } else {
                val jsonMap = mockField(jvmType)
                GsonUtils.toJson(jsonMap)
            }
        }
        return ""
    }

    private fun getJavaBaseTypeValue(paramType: String): Any? {
        val baseType = BaseType.get(paramType) ?: return null
        return Mock.mock(baseType.clazz)
    }

    private fun findRequestBody(psiMethod: PsiMethod): JvmType? {
        val parameters = psiMethod.parameters
        return parameters.filter {
            it.annotations.any { anno ->
                anno.qualifiedName == RequestParams.RequestBody.qualifiedName
            }
        }.map { it.type }.firstOrNull()
    }

    fun findRequestParam(psiMethod: PsiMethod, requestParams: RequestParams): Map<String, Any> {
        val parameters = psiMethod.parameters
        val params = mutableMapOf<String, Any>()
        for (parameter in parameters) {
            val annotation = parameter.annotations.filter { anno ->
                anno.qualifiedName == requestParams.qualifiedName
            }.map { it as PsiAnnotation }.firstOrNull()

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
