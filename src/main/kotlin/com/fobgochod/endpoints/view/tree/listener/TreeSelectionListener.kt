package com.fobgochod.endpoints.view.tree.listener

import com.fobgochod.endpoints.framework.PsiFileUtils
import com.fobgochod.endpoints.view.EndpointsManager
import com.fobgochod.endpoints.view.tree.node.EndpointNode
import com.intellij.openapi.project.Project
import javax.swing.event.TreeSelectionEvent
import javax.swing.event.TreeSelectionListener

class TreeSelectionListener(val project: Project) : TreeSelectionListener {

    override fun valueChanged(e: TreeSelectionEvent?) {
        val view = EndpointsManager.getView(project)

        view.testPanel.empty()
        val selectedPath = PsiFileUtils.getSelectedPath(project)
        if (selectedPath is EndpointNode) {
            val entity = selectedPath.source
            entity.reset(false)
            view.testPanel.apply(entity)
        }
    }
}
