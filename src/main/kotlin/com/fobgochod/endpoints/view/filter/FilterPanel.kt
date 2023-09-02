package com.fobgochod.endpoints.view.filter

import com.fobgochod.endpoints.domain.HttpMethod
import com.fobgochod.endpoints.view.EndpointsManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.JBPopupListener
import com.intellij.openapi.ui.popup.LightweightWindowEvent
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import javax.swing.JPopupMenu

class FilterPanel<T>(val project: Project, items: MutableMap<T, Boolean>) : JPopupMenu() {

    private val root: DialogPanel = panel {
        val checkBoxes = mutableListOf<JBCheckBox>()
        for (entry in items.entries) {
            row {
                checkBox(entry.key.toString()).applyToComponent {
                    checkBoxes.add(this)
                }.bindSelected(
                    { items.getOrDefault(entry.key, false) },
                    { items[entry.key] = it }
                )
            }
        }
        row {
            button("Select All") {
                checkBoxes.forEach { checkBox -> checkBox.isSelected = true }
            }
            button("UnSelect All") {
                checkBoxes.forEach { checkBox -> checkBox.isSelected = false }
            }
            button("Close") { popup.closeOk(null) }
        }
    }

    val popup: JBPopup = JBPopupFactory.getInstance()
        .createComponentPopupBuilder(root, null)
        .setProject(project)
        .createPopup()

    init {
        val listener = object : JBPopupListener {
            override fun onClosed(event: LightweightWindowEvent) {
                root.apply()
                for ((key, value) in items) {
                    if (key is HttpMethod) {
                        FilterType.METHOD_MAP[key] = value
                    } else if (key is Module) {
                        FilterType.MODULE_MAP[key] = value
                    }
                }
                val toolWindow = EndpointsManager.getView(project)
                toolWindow.refresh()
            }
        }
        popup.addListener(listener)
    }
}
