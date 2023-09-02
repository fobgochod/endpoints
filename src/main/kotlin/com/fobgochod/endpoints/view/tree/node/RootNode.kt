package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.RootEntity

class RootNode(source: RootEntity) : BaseNode<RootEntity>(source) {

    override fun getWeight(): Int {
        return NodeType.ROOT.ordinal
    }
}
