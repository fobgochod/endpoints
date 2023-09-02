package com.fobgochod.endpoints.view.tree.listener

import com.fobgochod.endpoints.domain.node.EndpointEntity
import com.fobgochod.endpoints.util.EndpointsIcons
import com.intellij.util.ui.UIUtil
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class TreePopupCellRenderer : JLabel(), ListCellRenderer<EndpointEntity> {

    init {
        isOpaque = true
    }

    override fun getListCellRendererComponent(
            list: JList<out EndpointEntity>,
            value: EndpointEntity,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean
    ): Component {

        text = value.path
        icon = EndpointsIcons.getMethodIcon(value.method, isSelected)
        if (isSelected) {
            background = UIUtil.getListSelectionBackground(cellHasFocus)
            foreground = UIUtil.getListSelectionForeground(cellHasFocus)
        } else {
            background = list.background
            foreground = list.foreground
        }
        return this
    }
}
