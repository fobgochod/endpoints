package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.EndpointEntity
import javax.swing.Icon

class EndpointNode(source: EndpointEntity) : BaseNode<EndpointEntity>(source) {

    override fun navigate(requestFocus: Boolean) {
        source.psiMethod.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return true
    }

    override fun getIcon(selected: Boolean): Icon {
        return if (selected) {
            source.getSelectIcon()
        } else {
            source.icon
        }
    }

    override fun getFragment(): String {
        return source.path
    }

    override fun getWeight(): Int {
        return NodeType.ENDPOINT.ordinal
    }
}
