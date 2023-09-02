package com.fobgochod.endpoints.domain

enum class HttpMethod {

    REQUEST, GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    companion object {
        private val mappings: MutableMap<String, HttpMethod> = mutableMapOf()

        init {
            for (httpMethod in values()) {
                mappings[httpMethod.name] = httpMethod
            }
        }

        fun resolve(method: String?): HttpMethod? {
            return if (method != null) mappings[method] else null
        }
    }
}
