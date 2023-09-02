package com.fobgochod.endpoints.view.tree

import com.fobgochod.endpoints.view.tree.node.BaseNode

class TreeNodeComparator : Comparator<BaseNode<*>> {

    override fun compare(p1: BaseNode<*>, p2: BaseNode<*>): Int {
        if (p1.getWeight() != p2.getWeight()) {
            return p1.getWeight() - p2.getWeight()
        }

        val o1: String = p1.source.name
        val o2: String = p2.source.name
        return o1.compareTo(o2, ignoreCase = true)
    }
}
