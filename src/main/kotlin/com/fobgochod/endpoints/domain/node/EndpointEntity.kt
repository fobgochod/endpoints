package com.fobgochod.endpoints.domain.node

import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.domain.spring.RequestParams
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.util.EndpointsIcons
import com.fobgochod.endpoints.util.ParamUtils
import com.intellij.psi.PsiMethod
import javax.swing.Icon

class EndpointEntity(
        var method: HttpMethod = HttpMethod.REQUEST,
        var path: String = "",
        val psiMethod: PsiMethod,
        icon: Icon = EndpointsIcons.getMethodIcon(method)
) : BaseEntity(path, icon) {

    private val state = EndpointsSettings.instance

    private var first = true
    val params = mutableMapOf<String, Any?>()
    val paths = mutableMapOf<String, Any?>()
    val headers = mutableMapOf<String, Any?>()
    var body: String = ""

    fun getSelectIcon(): Icon {
        return EndpointsIcons.getMethodIcon(method, true)
    }

    fun getRequestUrl(): String {
        return state.getUri(path)
    }

    fun reset(force: Boolean = false) {
        if (force || first || !state.cacheParam) {
            this.reset()
            first = false
        }
    }

    fun reset() {
        val params = ParamUtils.getRequestParams(psiMethod, RequestParams.RequestParam)
        val paths = ParamUtils.getRequestParams(psiMethod, RequestParams.PathVariable)
        val headers = state.httpHeaders.associate { it.key to it.value }
        val body = ParamUtils.getRequestBody(psiMethod, RequestParams.RequestBody)

        this.clear()
        this.params.putAll(params)
        this.paths.putAll(paths)
        this.headers.putAll(headers)
        this.body = body
    }

    fun clear() {
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
