package com.fobgochod.endpoints.view.tree.listener

import com.fobgochod.endpoints.framework.TreeUtils
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JTree
import javax.swing.SwingUtilities

class TreeMouseListener(private val tree: JTree) : MouseAdapter() {

    override fun mouseClicked(event: MouseEvent) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            if (event.clickCount == 2) {
                val selectedPath = TreeUtils.getSelectedPath(tree)
                if (selectedPath is EndpointNode) {
                    selectedPath.navigate(true)
                }
            }
        }
    }
}
