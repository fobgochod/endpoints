package com.fobgochod.endpoints.domain.spring

import com.fobgochod.endpoints.domain.HttpMethod

enum class RequestMethod(
    val qualifiedName: String,
    val method: HttpMethod
) {

    RequestMapping("org.springframework.web.bind.annotation.RequestMapping", HttpMethod.REQUEST),
    GetMapping("org.springframework.web.bind.annotation.GetMapping", HttpMethod.GET),
    PostMapping("org.springframework.web.bind.annotation.PostMapping", HttpMethod.POST),
    PutMapping("org.springframework.web.bind.annotation.PutMapping", HttpMethod.PUT),
    DeleteMapping("org.springframework.web.bind.annotation.DeleteMapping", HttpMethod.DELETE),
    PatchMapping("org.springframework.web.bind.annotation.PatchMapping", HttpMethod.PATCH);

    companion object {

        fun getByQualifiedName(qualifiedName: String?): RequestMethod? {
            return values().firstOrNull { it.qualifiedName == qualifiedName }
        }

        fun getByShortName(requestMapping: String?): RequestMethod? {
            return values().firstOrNull { requestMapping != null && it.qualifiedName.endsWith(requestMapping) }
        }
    }
}
