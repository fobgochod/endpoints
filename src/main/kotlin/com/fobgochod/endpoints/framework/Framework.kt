package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project

interface Framework<C> {

    companion object {
        fun getJava(): List<Java> {
            return listOf(Spring.getInstance())
        }
    }

    /**
     * 是否是Restful的项目
     *
     * @param project project
     * @param module  module
     * @return bool
     */
    fun isRestfulProject(project: Project, module: Module): Boolean

    /**
     * 根据模块获取所有的Services
     *
     * @param project 项目
     * @param module  模块
     * @return Collection
     */
    fun getMethod(project: Project, module: Module): Collection<EndpointEntity>

    /**
     * 根据文件获取所有的Services
     *
     * @param clazz class文件
     * @return Collection
     */
    fun getMethod(clazz: C): Collection<EndpointEntity>

    /**
     * 检测当前 PsiClass 是否含有`RestController` | `Controller` | `Path`
     *
     * @param clazz class文件
     * @return bool
     */
    fun hasRestful(clazz: C): Boolean
}
