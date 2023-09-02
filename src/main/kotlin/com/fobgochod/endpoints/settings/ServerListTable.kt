package com.fobgochod.endpoints.settings

import com.intellij.execution.util.ListTableWithButtons
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import javax.swing.DefaultCellEditor
import javax.swing.table.TableCellEditor

/**
 *  commit scope table
 *
 * @author fobgochod
 * @date 2023/6/18 21:16
 * @see com.intellij.ide.browsers.BrowserSettingsPanel
 */
class ServerListTable : ListTableWithButtons<ServerListTable.Item>() {

    data class Item(var value: String)

    companion object {
        private val state = EndpointsSettings.instance
        private val VALUE_COLUMN = object : ColumnInfo<Item, String>("") {

            override fun valueOf(item: Item): String {
                return item.value
            }

            override fun getEditor(item: Item): TableCellEditor {
                val cellEditor = JBTextField()
                cellEditor.putClientProperty(DarculaUIUtil.COMPACT_PROPERTY, true)
                return DefaultCellEditor(cellEditor)
            }

            override fun isCellEditable(item: Item): Boolean {
                return true
            }

            override fun setValue(item: Item, value: String) {
                item.value = value
            }
        }
    }

    override fun createListModel(): ListTableModel<String> {
        return ListTableModel<String>(VALUE_COLUMN)
    }

    override fun createElement(): Item {
        return Item("")
    }

    override fun isEmpty(element: Item): Boolean {
        return element.value.isEmpty()
    }

    override fun isUpDownSupported(): Boolean {
        return true
    }

    override fun cloneElement(variable: Item): Item {
        return variable.copy()
    }

    override fun canDeleteElement(selection: Item): Boolean {
        return true
    }

    override fun setValues(envVariables: List<Item>) {
        super.setValues(envVariables)
        tableView.listTableModel.fireTableDataChanged()
    }

    fun reset() {
        val rows = state.httpServers.map { Item(it) }
        setValues(rows)
    }

    fun apply() {
        state.httpServers.clear()
        elements.forEach { state.httpServers.add(it.value) }
    }

    fun isModified(): Boolean {
        val rows = elements.map { it.value }
        return state.httpServers != rows
    }
}
