package com.fobgochod.endpoints.framework

import com.fobgochod.endpoints.domain.PackageTree
import com.fobgochod.endpoints.domain.node.ClassEntity
import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.domain.node.MethodEntity
import com.fobgochod.endpoints.domain.node.PackageEntity
import com.fobgochod.endpoints.settings.EndpointsSettings
import com.fobgochod.endpoints.view.tree.node.BaseNode
import com.fobgochod.endpoints.view.tree.TreeNodeComparator
import com.fobgochod.endpoints.view.tree.node.ClassNode
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.fobgochod.endpoints.view.tree.node.MethodNode
import com.fobgochod.endpoints.view.tree.node.PackageNode
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.intellij.util.ui.tree.TreeUtil
import javax.swing.JTree
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreeNode

object TreeUtils {

    private val state = EndpointsSettings.instance

    @Transient
    val endpointNodes = mutableMapOf<PsiMethod, EndpointNode>()

    fun buildTree(node: BaseNode<*>, moduleClasses: List<PsiClass>) {
        val packageTrees = mutableSetOf<PackageTree>()

        moduleClasses.map {
            PsiJavaUtils.getPackageName(it)
        }.distinct().forEach {
            packageTrees.addAll(PackageTree.build(it))
        }

        val packageNodes = mutableMapOf("" to node)
        packageTrees.groupBy { it.level }.forEach { (_, packages) ->
            packages.forEach {
                val packageNode = PackageNode(PackageEntity(it.name))
                packageNodes[it.parentName]?.add(packageNode)
                packageNodes[it.compactedName] = packageNode
            }
        }

        moduleClasses.forEach {
            val qualifiedName = PsiJavaUtils.getPackageName(it)
            val packageNode = packageNodes[qualifiedName]
            if (packageNode != null) {
                buildTree(packageNode, it)
            }
        }

        flattenPackages(node.getChildAt(0) as BaseNode<*>)
        TreeUtil.sort(node, TreeNodeComparator())
    }

    fun buildTree(node: BaseNode<*>, psiClass: PsiClass) {
        if (EndpointsSettings.instance.showClass) {
            val classNode = ClassNode(ClassEntity(psiClass))
            node.add(classNode)
            doBuildTree(classNode, psiClass)
            if (classNode.childCount == 0) {
                node.remove(classNode)
            }
        } else {
            doBuildTree(node, psiClass)
        }
        TreeUtil.sort(node, TreeNodeComparator())
    }

    private fun doBuildTree(node: BaseNode<*>, psiClass: PsiClass) {
        val classEndpoints = EntityUtils.getClassEndpoints(psiClass)
        if (state.showMethod) {
            classEndpoints.groupBy { it.psiMethod }.forEach { (method, methodEndpoints) ->
                if (methodEndpoints.size == 1) {
                    val endpoint = methodEndpoints[0]
                    val endpointNode = EndpointNode(endpoint)
                    node.add(endpointNode)
                    endpointNodes[endpoint.psiMethod] = endpointNode
                } else {
                    val methodNode = MethodNode(MethodEntity(method))
                    methodEndpoints.forEach {
                        val endpointNode = EndpointNode(it)
                        methodNode.add(endpointNode)
                        endpointNodes[it.psiMethod] = endpointNode
                    }
                    node.add(methodNode)
                }
            }
        } else {
            flattenTree(classEndpoints).forEach(node::add)
        }

        PsiJavaUtils.getInnerClass(psiClass).forEach {
            buildTree(node, it)
        }
    }

    private fun flattenPackages(parent: BaseNode<*>) {
        if (!state.flattenPackages) {
            return
        }
        if (parent is PackageNode && parent.childCount == 1) {
            val treeNode: TreeNode = parent.getChildAt(0)
            if (treeNode is PackageNode) {
                parent.removeAllChildren()
                parent.source.name = parent.source.name + "." + treeNode.source.name
                for (i in treeNode.getChildCount() - 1 downTo 0) {
                    parent.add(treeNode.getChildAt(i) as MutableTreeNode)
                }
                flattenPackages(parent)
            }
        }
    }

    private fun flattenTree(endpoints: List<EndpointEntity>): List<BaseNode<*>> {
        return endpoints.map {
            val endpointNode = EndpointNode(it)
            endpointNodes[it.psiMethod] = endpointNode
            endpointNode
        }.sortedBy { it.toString() }.toList()
    }

    fun getSelectedPath(tree: JTree): BaseNode<*>? {
        val treePath = TreeUtil.getSelectedPathIfOne(tree)
        val component = if (treePath != null) {
            treePath.lastPathComponent
        } else {
            tree.lastSelectedPathComponent
        }
        if (component is BaseNode<*>) {
            return component
        }
        return null
    }
}
