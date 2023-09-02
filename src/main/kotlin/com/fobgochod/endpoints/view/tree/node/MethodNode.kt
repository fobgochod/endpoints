package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.MethodEntity

class MethodNode(source: MethodEntity) : BaseNode<MethodEntity>(source) {

    override fun navigate(requestFocus: Boolean) {
        source.psiMethod.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return true
    }

    override fun getWeight(): Int {
        return NodeType.METHOD.ordinal
    }
}
