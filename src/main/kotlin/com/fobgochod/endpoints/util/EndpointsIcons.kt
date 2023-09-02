package com.fobgochod.endpoints.util

import com.fobgochod.endpoints.domain.HttpMethod
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object EndpointsIcons {

    @JvmField
    val LOGO = load("/icons/logo.svg")
    @JvmField
    val LOGO_12X12 = load("/icons/logo_12x12.svg")

    private fun load(path: String): Icon {
        return IconLoader.getIcon(path, EndpointsIcons::class.java)
    }

    fun getMethodIcon(method: HttpMethod): Icon {
        return getMethodIcon(method, false)
    }

    fun getMethodIcon(method: HttpMethod, selected: Boolean): Icon {
        if (selected) {
            return load("/icons/method/default/" + method.name + "_select.svg")
        }
        return load("/icons/method/default/" + method.name + ".svg")
    }
}
