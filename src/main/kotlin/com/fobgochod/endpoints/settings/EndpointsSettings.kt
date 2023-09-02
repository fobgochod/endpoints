package com.fobgochod.endpoints.settings

import com.fobgochod.endpoints.constant.EndpointsConstant
import com.fobgochod.endpoints.domain.HttpHeader
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = EndpointsSettings.NAME, storages = [Storage(EndpointsSettings.STORAGES)], category = SettingsCategory.TOOLS)
class EndpointsSettings : PersistentStateComponent<EndpointsSettingsState> {

    private val state = EndpointsSettingsState()

    override fun getState() = state

    override fun loadState(state: EndpointsSettingsState) = XmlSerializerUtil.copyBean(state, this.state)

    init {
        state.httpServers = mutableListOf(EndpointsConstant.DEFAULT_SERVER)
        state.httpHeaders = HttpHeader.getPredefinedBrowsers()
    }

    var scanLibrary: Boolean
        get() = state.scanLibrary
        set(value) {
            state.scanLibrary = value
        }

    var expandTree: Boolean
        get() = state.expandTree
        set(value) {
            state.expandTree = value
        }

    var flattenPackages: Boolean
        get() = state.flattenPackages
        set(value) {
            state.flattenPackages = value
        }

    var showClass: Boolean
        get() = state.showClass
        set(value) {
            state.showClass = value
        }

    var showMethod: Boolean
        get() = state.showMethod
        set(value) {
            state.showMethod = value
        }

    var httpTimeout: Int
        get() = state.httpTimeout
        set(value) {
            state.httpTimeout = value
        }

    var httpPort: Int
        get() = state.httpPort
        set(value) {
            state.httpPort = value
        }

    var httpServers: MutableList<String>
        get() = state.httpServers
        set(value) {
            state.httpServers = value
        }

    var httpHeaders: MutableList<HttpHeader>
        get() = state.httpHeaders
        set(value) {
            state.httpHeaders = value
        }


    fun getUri(path: String): String {
        return if (httpServers.isEmpty()) {
            EndpointsConstant.DEFAULT_SERVER + path
        } else {
            httpServers[0] + path
        }
    }

    companion object {

        const val NAME = "EndpointTool"
        const val STORAGES = "endpoint.tool.xml"

        @JvmStatic
        val instance: EndpointsSettings
            get() = service()
    }
}
