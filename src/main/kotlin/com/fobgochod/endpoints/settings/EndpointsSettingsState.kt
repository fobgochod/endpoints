package com.fobgochod.endpoints.settings

import com.fobgochod.endpoints.domain.HttpHeader
import com.intellij.openapi.components.BaseState
import com.intellij.util.xmlb.annotations.OptionTag
import com.intellij.util.xmlb.annotations.Tag
import com.intellij.util.xmlb.annotations.XCollection

class EndpointsSettingsState : BaseState() {

    @get:OptionTag("SCAN_LIBRARY")
    var scanLibrary by property(false)

    @get:OptionTag("EXPAND_TREE")
    var expandTree by property(false)

    @get:OptionTag("FLATTEN_PACKAGES")
    var flattenPackages by property(true)

    @get:OptionTag("SHOW_CLASS")
    var showClass by property(true)

    @get:OptionTag("SHOW_METHOD")
    var showMethod by property(true)

    @get:OptionTag("HTTP_TIMEOUT")
    var httpTimeout by property(60)

    @get:OptionTag("CACHE_PARAM")
    var cacheParam by property(true)

    @get:OptionTag("HTTP_PORT")
    var httpPort by property(8080)

    @get:Tag("HTTP_SERVERS")
    @get:XCollection(style = XCollection.Style.v2)
    var httpServers by list<String>()

    @get:Tag("HTTP_HEADERS")
    @get:XCollection(style = XCollection.Style.v2)
    var httpHeaders by list<HttpHeader>()
}

