package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.EndpointAttribute
import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.domain.spring.RequestMethod
import com.fobgochod.endpoints.domain.spring.SpringController
import com.fobgochod.endpoints.util.PathUtils
import com.fobgochod.endpoints.view.filter.FilterType
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex

class Spring : Java {

    companion object {
        private val INSTANCE = Spring()

        fun getInstance(): Spring {
            return INSTANCE
        }
    }

    override fun isRestfulProject(project: Project, module: Module): Boolean {
        SpringController.values().forEach {
            JavaAnnotationIndex.getInstance()
                .get(it.name, project, module.moduleScope)
                .forEach { annotation ->
                    if (it.qualifiedName == annotation.qualifiedName) {
                        return true
                    }
                }
        }
        return false
    }

    override fun getMethod(project: Project, module: Module): Collection<EndpointEntity> {
        return PsiJavaUtils.getClass(project, module)
            .flatMap { getMethod(it) }.toList()
    }

    override fun getMethod(clazz: PsiClass): Collection<EndpointEntity> {
        // 查询类上的注解 RequestMapping
        val parent = getClassAttributes(clazz)
        // 查询方法上注解
        val children = clazz.allMethods.map { getMethodAttributes(it) }.filter { it.isNotEmpty() }.toList()

        // 合并结果
        val parentPaths = parent.paths
        val parentMethods = parent.methods

        val endpoints = mutableListOf<EndpointEntity>()
        parentPaths.forEach { parentPath ->
            children.forEach { child ->
                child.entries.forEach { (psiMethod, attribute) ->
                    val methods = attribute.methods
                    val paths = attribute.paths
                    // 处理Request和标准http方法
                    methods.addAll(parentMethods)
                    if (methods.size > 1) {
                        methods.remove(HttpMethod.REQUEST)
                    }
                    // 组装EndpointEntity对象
                    methods.forEach { method ->
                        paths.forEach { path ->
                            endpoints.add(EndpointEntity(method, parentPath + path, psiMethod))
                        }
                    }
                }
            }
        }
        return endpoints.distinct().filter {
            FilterType.METHOD_MAP[it.method] == true
        }
    }

    private fun getClassAttributes(clazz: PsiClass): EndpointAttribute {
        // 查询类上的注解 RequestMapping
        val requestMappingAnnotation = PsiJavaUtils.getClassAnnotation(
            clazz,
            RequestMethod.RequestMapping.qualifiedName,
            RequestMethod.RequestMapping.name
        ) ?: return EndpointAttribute.EMPTY
        return getAnnotationAttribute(requestMappingAnnotation, HttpMethod.REQUEST)
    }

    private fun getMethodAttributes(psiMethod: PsiMethod): Map<PsiMethod, EndpointAttribute> {
        val attribute = PsiJavaUtils.getMethodAnnotations(psiMethod)
            .firstNotNullOfOrNull { getAnnotationAttribute(it) } ?: return emptyMap()
        return mutableMapOf(psiMethod to attribute)
    }

    private fun getAnnotationAttribute(psiAnnotation: PsiAnnotation): EndpointAttribute? {
        // 获取Spring标准注解和自定义注解
        var endpointAnnotation = RequestMethod.getByQualifiedName(psiAnnotation.qualifiedName)
        if (psiAnnotation.resolveScope.isSearchInLibraries) {
            endpointAnnotation = RequestMethod.getByShortName(psiAnnotation.qualifiedName)
        }
        return if (endpointAnnotation == null) {
            null
        } else {
            getAnnotationAttribute(psiAnnotation, endpointAnnotation.method)
        }
    }

    /**
     * 获取注解属性值
     *
     * @param psiAnnotation annotation
     */
    private fun getAnnotationAttribute(psiAnnotation: PsiAnnotation, httpMethod: HttpMethod): EndpointAttribute {
        val methods = mutableSetOf(httpMethod)
        val paths = mutableSetOf<String>()
        for (attribute in psiAnnotation.attributes) {
            if (methods.contains(HttpMethod.REQUEST)) {
                // RequestMapping 可以指定多个 method
                val methodValue: Any? = PsiJavaUtils.getAnnotationValue(attribute, "method")
                if (methodValue != null) {
                    methods.remove(HttpMethod.REQUEST)
                    if (methodValue is String) {
                        HttpMethod.resolve(methodValue)?.let { methods.add(it) }
                    } else if (methodValue is List<*>) {
                        methodValue
                            .map { it.toString() }
                            .mapNotNull { HttpMethod.resolve(it) }
                            .forEach { methods.add(it) }
                    }
                    continue
                }
            }

            val pathValue: Any? = PsiJavaUtils.getAnnotationValue(attribute, "path", "value")
            if (pathValue != null) {
                if (pathValue is String) {
                    paths.add(PathUtils.formatPath(pathValue))
                } else if (pathValue is List<*>) {
                    pathValue.map { it.toString() }.forEach { paths.add(PathUtils.formatPath(it)) }
                }
            }
        }
        if (paths.isEmpty()) {
            // 是否为隐式的path（未定义value或者path）
            paths.add("")
        }
        return EndpointAttribute(paths, methods)
    }

    override fun hasRestful(clazz: PsiClass): Boolean {
        return SpringController.values().any { clazz.hasAnnotation(it.qualifiedName) }
    }
}
