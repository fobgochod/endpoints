package com.fobgochod.endpoints.domain

import com.fobgochod.endpoints.constant.HttpHeaders
import com.fobgochod.endpoints.constant.MediaType

data class HttpHeader(
    var key: String = "",
    var value: String = ""
) {

    companion object {
        fun getPredefinedBrowsers(): MutableList<HttpHeader> {
            return mutableListOf(HttpHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        }
    }
}
