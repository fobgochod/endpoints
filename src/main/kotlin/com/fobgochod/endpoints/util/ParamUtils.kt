package com.fobgochod.endpoints.util

import com.fobgochod.endpoints.domain.spring.RequestParams
import com.fobgochod.endpoints.framework.PsiJavaUtils
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.mock.Mock
import com.fobgochod.mock.enums.BaseType
import com.fobgochod.mock.enums.PrimitiveBean
import com.intellij.lang.jvm.JvmParameter
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiClassReferenceType
import kotlin.reflect.jvm.jvmName

@Suppress("UnstableApiUsage")
object ParamUtils {

    private val state = EndpointsSettings.instance

    fun classToJson(psiClass: PsiClass): String {
        val jsonMap = mockClass(psiClass, state.recursionDepth)
        return GsonUtils.toJson(jsonMap)
    }

    fun getRequestBody(psiMethod: PsiMethod, requestParams: RequestParams): String {
        val parameters = psiMethod.parameters
        val jvmType = parameters.filter { getParameterAnnotation(it, requestParams) != null }.map { it.type }.firstOrNull()
        if (jvmType is PsiType) {
            val json = mockField(jvmType, state.recursionDepth)
            return GsonUtils.toJson(json)
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
        if (!state.mockData) {
            return PrimitiveBean.of(paramType)
        }
        val baseType = BaseType.get(paramType) ?: return null
        return Mock.mock(baseType.clazz)
    }

    private fun getParameterAnnotation(parameter: JvmParameter, annotation: RequestParams): PsiAnnotation? {
        return parameter.annotations.filter { it.qualifiedName == annotation.qualifiedName }.map { it as PsiAnnotation }.firstOrNull()
    }

    private fun mockClass(psiClass: PsiClass, depth: Int): Map<String, Any?> {
        val attributes: MutableMap<String, Any?> = mutableMapOf()
        for (field in psiClass.fields) {
            val fieldType = field.type
            val fieldName = field.name

            if (isSelf(fieldType, psiClass)) {
                if (depth <= 0) {
                    attributes[fieldName] = getNullValue(fieldType)
                } else {
                    attributes[fieldName] = mockField(fieldType, depth - 1)
                }
            } else {
                attributes[fieldName] = mockField(fieldType, depth)
            }
        }
        return attributes
    }

    private fun getNullValue(fieldType: PsiType): Any? {
        if (fieldType is PsiArrayType) {
            return emptyArray<String>()
        } else if (fieldType is PsiClassReferenceType) {
            val fieldClass = fieldType.resolve()
            if (fieldClass == null) {
                return null
            } else if (isClass(fieldClass, fieldType, List::class.jvmName)) {
                return emptyList<String>()
            } else if (isClass(fieldClass, fieldType, Set::class.jvmName)) {
                return emptySet<String>()
            } else if (isClass(fieldClass, fieldType, Map::class.jvmName)) {
                return emptyMap<String, String>()
            }
        }
        return null
    }

    private fun isSelf(fieldType: PsiType, psiClass: PsiClass): Boolean {
        if (fieldType is PsiArrayType) {
            return isSelf(fieldType.componentType, psiClass)
        } else if (fieldType is PsiClassReferenceType) {
            val fieldClass = fieldType.resolve()
            return if (fieldClass == null) {
                false
            } else if (isClass(fieldClass, fieldType, List::class.jvmName)) {
                isSelf(fieldType.parameters[0], psiClass)
            } else if (isClass(fieldClass, fieldType, Set::class.jvmName)) {
                isSelf(fieldType.parameters[0], psiClass)
            } else if (isClass(fieldClass, fieldType, Map::class.jvmName)) {
                isSelf(fieldType.parameters[1], psiClass)
            } else {
                fieldClass == psiClass
            }
        }
        return false
    }

    private fun mockField(fieldType: PsiType, depth: Int = 0): Any? {
        val qualifiedName = fieldType.canonicalText

        if (fieldType is PsiPrimitiveType) {
            return getJavaBaseTypeValue(qualifiedName)
        } else if (BaseType.contains(qualifiedName)) {
            return getJavaBaseTypeValue(qualifiedName)
        } else if (fieldType is PsiArrayType) {
            val psiType = fieldType.componentType
            val value = mockField(psiType, depth)
            return if (value == null) emptyList() else listOf(value)
        } else if (fieldType is PsiClassReferenceType) {
            val fieldClass = fieldType.resolve()
            if (fieldClass == null) {
                return null
            } else if (fieldClass.isEnum) {
                val ordinal = (0 until fieldClass.fields.size).random()
                val psiField = fieldClass.fields[ordinal]
                return psiField.name
            } else if (isClass(fieldClass, fieldType, List::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val value = mockField(parameters[0], depth)
                    return if (value == null) emptyList() else listOf(value)
                }
            } else if (isClass(fieldClass, fieldType, Set::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val value = mockField(parameters[0], depth)
                    return if (value == null) emptySet() else setOf(value)
                }
            } else if (isClass(fieldClass, fieldType, Map::class.jvmName)) {
                val parameters = fieldType.parameters
                if (parameters.isNotEmpty()) {
                    val key = mockField(parameters[0], depth)
                    val value = mockField(parameters[1], depth)
                    return mapOf(key to value)
                }
            } else {
                return mockClass(fieldClass, depth)
            }
        }
        return null
    }

    /**
     * 字段是否为指定类型
     */
    private fun isClass(fieldClass: PsiClass, psiFieldType: PsiClassReferenceType, qualifiedName: String): Boolean {
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
