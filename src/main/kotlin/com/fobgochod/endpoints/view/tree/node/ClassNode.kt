package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.ClassEntity

class ClassNode(source: ClassEntity) : BaseNode<ClassEntity>(source) {

    override fun navigate(requestFocus: Boolean) {
        source.psiClass.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return true
    }

    override fun getWeight(): Int {
        return NodeType.CLASS.ordinal
    }
}
