package com.fobgochod.endpoints.domain.spring

enum class RequestParams(
    val qualifiedName: String
) {

    RequestParam("org.springframework.web.bind.annotation.RequestParam"),
    RequestBody("org.springframework.web.bind.annotation.RequestBody"),
    PathVariable("org.springframework.web.bind.annotation.PathVariable"),
    RequestHeader("org.springframework.web.bind.annotation.RequestHeader");

    companion object {

        fun getByQualifiedName(qualifiedName: String?): RequestParams? {
            return RequestParams.values().firstOrNull { it.qualifiedName == qualifiedName }
        }

        fun getByShortName(requestMapping: String?): RequestParams? {
            return RequestParams.values()
                .firstOrNull { requestMapping != null && it.qualifiedName.endsWith(requestMapping) }
        }
    }
}
