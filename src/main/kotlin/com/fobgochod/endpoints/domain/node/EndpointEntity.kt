package com.fobgochod.endpoints.domain.node

import com.fobgochod.endpoints.action.tool.JsonHelper
import com.fobgochod.endpoints.domain.HttpHeader
import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.domain.spring.RequestParams
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsIcons
import com.intellij.psi.PsiMethod
import javax.swing.Icon

class EndpointEntity(
    var method: HttpMethod = HttpMethod.REQUEST,
    var path: String = "",
    val psiMethod: PsiMethod,
    icon: Icon = EndpointsIcons.getMethodIcon(method)
) : BaseEntity(path, icon) {

    private val state = EndpointsSettings.instance

    private var cache: Boolean = false
    val params: MutableList<HttpHeader> = mutableListOf()
    val paths: MutableList<HttpHeader> = mutableListOf()
    val headers: MutableList<HttpHeader> = mutableListOf()
    var body: String = ""


    fun getSelectIcon(): Icon {
        return EndpointsIcons.getMethodIcon(method, true)
    }

    fun getRequestUrl(): String {
        return state.getUri(path)
    }

    fun apply() {
        if (this.cache) {
            return
        }
        val params = JsonHelper.findRequestParam(psiMethod, RequestParams.RequestParam).map {
            HttpHeader(it.key, it.value.toString())
        }
        val paths = JsonHelper.findRequestParam(psiMethod, RequestParams.PathVariable).map {
            HttpHeader(it.key, it.value.toString())
        }
        val headers = state.httpHeaders.stream().map {
            HttpHeader(it.key, it.value)
        }.toList()
        val body = JsonHelper.getRequestBody(psiMethod)

        this.params.addAll(params)
        this.paths.addAll(paths)
        this.headers.addAll(headers)
        this.body = body
        this.cache = true
    }

    fun reset() {
        this.params.clear()
        this.paths.clear()
        this.headers.clear()
        this.body = ""
    }

    override fun toString(): String {
        return "$path ${method.name}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EndpointEntity

        if (method != other.method) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = method.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }
}
