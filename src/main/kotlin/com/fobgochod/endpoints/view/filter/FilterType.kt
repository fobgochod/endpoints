package com.fobgochod.endpoints.view.filter

import com.fobgochod.endpoints.domain.HttpMethod
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project

object FilterType {

    val MODULE_MAP = mutableMapOf<Module, Boolean>()
    val METHOD_MAP = mutableMapOf<HttpMethod, Boolean>()

    init {
        HttpMethod.values().forEach { value -> METHOD_MAP[value] = true }
    }

    fun init(project: Project) {
        ModuleManager.getInstance(project).modules.forEach { module ->
            MODULE_MAP[module] = true
        }
    }

    fun clear(){
        MODULE_MAP.clear()
        METHOD_MAP.clear()
    }
}
