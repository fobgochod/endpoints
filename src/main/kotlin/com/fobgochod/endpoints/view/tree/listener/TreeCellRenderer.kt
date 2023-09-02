package com.fobgochod.endpoints.view.tree.listener

import com.fobgochod.endpoints.view.tree.node.BaseNode
import com.intellij.ui.ColoredTreeCellRenderer
import javax.swing.JTree

class TreeCellRenderer : ColoredTreeCellRenderer() {

    override fun customizeCellRenderer(
        tree: JTree,
        value: Any,
        selected: Boolean,
        expanded: Boolean,
        leaf: Boolean,
        row: Int,
        hasFocus: Boolean
    ) {
        if (value is BaseNode<*>) {
            icon = value.getIcon(selected)
            append(value.getFragment(), value.getAttributes())
        }
    }
}
