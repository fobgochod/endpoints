package com.fobgochod.endpoints.view.tree.node

import com.fobgochod.endpoints.domain.NodeType
import com.fobgochod.endpoints.domain.node.BaseEntity
import com.fobgochod.endpoints.view.tree.Navigatable
import com.intellij.ui.SimpleTextAttributes
import javax.swing.Icon
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.MutableTreeNode

/**
 * Endpoint Tree BaseNode
 * @author fobgochod
 * @date 2023/6/30 11:06
 * @see com.intellij.psi.PsiDirectory
 * @see com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
 */
abstract class BaseNode<T : BaseEntity>(val source: T) : DefaultMutableTreeNode(source), Navigatable {

    override fun add(newChild: MutableTreeNode) {
        super.add(newChild as BaseNode<*>)
    }

    open fun getIcon(selected: Boolean): Icon {
        return source.icon
    }

    open fun getFragment(): String {
        return source.name
    }

    fun getAttributes(): SimpleTextAttributes {
        return SimpleTextAttributes.REGULAR_ATTRIBUTES
    }

    open fun getWeight(): Int {
        return NodeType.BASE.ordinal
    }
}
