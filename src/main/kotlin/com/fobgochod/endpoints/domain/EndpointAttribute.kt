package com.fobgochod.endpoints.domain

class EndpointAttribute(
    val paths: MutableSet<String> = mutableSetOf(),
    val methods: MutableSet<HttpMethod> = mutableSetOf()
) {

    companion object {
        val EMPTY = EndpointAttribute(mutableSetOf(""))
    }
}
