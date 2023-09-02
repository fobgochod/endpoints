package com.fobgochod.endpoints.view.tree

import com.fobgochod.endpoints.action.tree.TreeActionGroup
import com.fobgochod.endpoints.domain.node.RootEntity
import com.fobgochod.endpoints.framework.TreeUtils
import com.fobgochod.endpoints.view.tree.listener.TreeCellRenderer
import com.fobgochod.endpoints.view.tree.listener.TreeMouseListener
import com.fobgochod.endpoints.view.tree.listener.TreeSelectionListener
import com.fobgochod.endpoints.view.tree.node.BaseNode
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.ide.TreeExpander
import com.intellij.ide.ui.customization.CustomizationUtil
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.util.ui.tree.TreeUtil
import javax.swing.JTree
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode
import javax.swing.tree.TreePath

/**
 * EndpointTreePanel
 *
 * @author fobgochod
 * @date 2023/6/24 23:14
 * @see com.intellij.ide.projectView.impl.AbstractProjectViewPane
 */
class EndpointsTreePane(val project: Project, val tree: JTree = SimpleTree()) : JBScrollPane(), TreeExpander {

    init {
        tree.cellRenderer = TreeCellRenderer()
        tree.isRootVisible = true
        tree.showsRootHandles = false

        setViewportView(tree)

        CustomizationUtil.installPopupHandler(tree, TreeActionGroup::class.java.name, ActionPlaces.POPUP)

        this.tree.addTreeSelectionListener(TreeSelectionListener(project))
        this.tree.addMouseListener(TreeMouseListener(tree))
    }

    override fun expandAll() {
        TreeUtil.expandAll(tree)
    }

    override fun canExpand(): Boolean {
        return tree.rowCount > 0
    }

    override fun collapseAll() {
        TreeUtil.collapseAll(tree, -1)
    }

    override fun canCollapse(): Boolean {
        return tree.rowCount > 0
    }

    fun renderAll(root: BaseNode<*>, expand: Boolean) {
        resetRoot(root)
        if (expand) {
            expandAll()
        } else {
            collapseAll()
        }
    }

    fun navigationToTree(psiMethod: PsiMethod) {
        val endpointNode = TreeUtils.endpointNodes[psiMethod] ?: return
        val treeModel = tree.model
        if (treeModel is DefaultTreeModel) {
            val nodes = treeModel.getPathToRoot(endpointNode)
            val path = TreePath(nodes)
            tree.expandPath(path) // request to expand found path
            TreeUtil.selectPath(tree, path) // select and scroll to center
        }
    }

    private fun resetRoot(root: BaseNode<*>) {
        val treeModel = tree.model
        if (treeModel is DefaultTreeModel) {
            val leafCount = leafCount(root)
            (root.source as RootEntity).rename(leafCount)
            treeModel.setRoot(root)
        }
    }

    private fun leafCount(node: TreeNode): Int {
        var count = 0
        for (child in node.children()) {
            if (child is EndpointNode) {
                count++
            } else {
                count += leafCount(child)
            }
        }
        return count
    }
}
