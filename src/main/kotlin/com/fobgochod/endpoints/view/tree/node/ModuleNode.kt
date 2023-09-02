package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.ModuleEntity

class ModuleNode(source: ModuleEntity) : BaseNode<ModuleEntity>(source) {

    override fun getWeight(): Int {
        return NodeType.MODULE.ordinal
    }
}
