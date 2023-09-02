package com.fobgochod.endpoints.domain.node

import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsIcons
import javax.swing.Icon

class RootEntity(name: String, icon: Icon = EndpointsIcons.LOGO) : BaseEntity(name, icon) {

    companion object {
        val EMPTY = RootEntity(message("endpoint.tree.root.empty.name"))
        val EMPTY_CLASS = RootEntity(message("endpoint.tree.root.empty.name"))
    }

    fun rename(num: Int) {
        name = message("endpoint.tree.root.count.name", num)
    }
}
