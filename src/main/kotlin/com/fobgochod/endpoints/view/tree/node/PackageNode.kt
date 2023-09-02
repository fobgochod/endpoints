package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.PackageEntity

class PackageNode(source: PackageEntity) : BaseNode<PackageEntity>(source) {

    override fun getWeight(): Int {
        return NodeType.PACKAGE.ordinal
    }
}
