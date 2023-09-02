package com.fobgochod.endpoints.settings

import com.fobgochod.endpoints.domain.HttpHeader
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.intellij.execution.util.ListTableWithButtons
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel

/**
 *  commit scope table
 *
 * @author fobgochod
 * @date 2023/6/18 21:16
 */
class HeaderListTable : ListTableWithButtons<HttpHeader>() {

    companion object {
        private val state = EndpointsSettings.instance
        private val KEY_COLUMN = object : ColumnInfo<HttpHeader, String>(message("settings.type.table.column.1")) {

            override fun valueOf(item: HttpHeader): String {
                return item.key
            }

            override fun isCellEditable(item: HttpHeader): Boolean {
                return true
            }

            override fun setValue(item: HttpHeader, value: String) {
                item.key = value
            }
        }

        private val VALUE_COLUMN = object : ColumnInfo<HttpHeader, String>(message("settings.type.table.column.2")) {

            override fun valueOf(item: HttpHeader): String {
                return item.value
            }

            override fun isCellEditable(item: HttpHeader): Boolean {
                return true
            }

            override fun setValue(item: HttpHeader, value: String) {
                item.value = value
            }
        }
    }

    override fun createListModel(): ListTableModel<String> {
        return ListTableModel<String>(KEY_COLUMN, VALUE_COLUMN)
    }

    override fun createElement(): HttpHeader {
        return HttpHeader("", "")
    }

    override fun isEmpty(element: HttpHeader): Boolean {
        return element.key.isEmpty() && element.value.isEmpty()
    }

    override fun isUpDownSupported(): Boolean {
        return true
    }

    override fun cloneElement(variable: HttpHeader): HttpHeader {
        return variable.copy()
    }

    override fun canDeleteElement(selection: HttpHeader): Boolean {
        return true
    }

    public override fun getElements(): List<HttpHeader> {
        return super.getElements()
    }

    override fun setValues(envVariables: List<HttpHeader>) {
        super.setValues(envVariables)
        tableView.listTableModel.fireTableDataChanged()
    }

    fun reset() {
        setValues(state.httpHeaders)
    }

    fun apply() {
        state.httpHeaders.clear()
        elements.forEach {
            state.httpHeaders.add(it.copy())
        }
    }

    fun isModified(): Boolean {
        return state.httpHeaders != elements
    }
}
