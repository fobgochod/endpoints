package com.fobgochod.endpoints.settings

import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.intellij.application.options.editor.CheckboxDescriptor
import com.intellij.application.options.editor.checkBox
import com.intellij.ide.IdeBundle
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*


/**
 * Git Settings Configurable
 *
 * See [Settings](https://plugins.jetbrains.com/docs/intellij/settings.html)
 *
 * See [Kotlin UI DSL Version 2](https://plugins.jetbrains.com/docs/intellij/kotlin-ui-dsl-version-2.html)
 *
 * See [Sample usages in IntelliJ Platform IDEs](https://plugins.jetbrains.com/docs/intellij/kotlin-ui-dsl.html#examples)
 *
 * @author fobgochod
 * @date 2023/5/24 23:43
 */
class EndpointsSettingsConfigurable : BoundSearchableConfigurable(
        message("configurable.display.name"),
        "endpoint.tool"
) {
    private val state: EndpointsSettings get() = EndpointsSettings.instance

    // @formatter:off
    private val scanLibrary get() = CheckboxDescriptor(message("settings.system.scan.library"), state::scanLibrary)
    private val expandTree get() = CheckboxDescriptor(message("settings.system.expand.tree"), state::expandTree)
    private val hideEmptyMiddlePackages get() = CheckboxDescriptor(IdeBundle.message("action.hide.empty.middle.packages"), state::flattenPackages)
    private val showClass get() = CheckboxDescriptor(message("settings.system.show.class"), state::showClass)
    private val showMethod get() = CheckboxDescriptor(message("settings.system.show.method"), state::showMethod)
    private val mockData get() = CheckboxDescriptor(message("settings.http.test.mock.data"), state::mockData)
    private val cacheParam get() = CheckboxDescriptor(message("settings.http.test.cache.param"), state::cacheParam)
    // @formatter:on

    override fun createPanel(): DialogPanel {
        return panel {
            group(message("settings.system.group")) {
                row {
                    checkBox(scanLibrary)
                }
                row {
                    checkBox(expandTree)
                }
                row {
                    checkBox(hideEmptyMiddlePackages)
                            .actionListener { _, component ->
                                if (component.isSelected) {
                                    component.text = IdeBundle.message("action.hide.empty.middle.packages")
                                } else {
                                    component.text = IdeBundle.message("action.compact.empty.middle.packages")
                                }
                            }
                }
                row {
                    checkBox(showClass)
                }
                row {
                    checkBox(showMethod)
                }
            }

            group(message("settings.json.tool.group")) {
                row {
                    checkBox(cacheParam)
                }

                row {
                    checkBox(mockData)
                }

                row(message("settings.http.test.recursion.depth")) {
                    spinner(0 until 10).bindIntValue(state::recursionDepth)
                }
            }

            group(message("settings.http.test.group")) {
                row(message("settings.http.test.http.timeout")) {
                    intTextField().bindIntText(state::httpTimeout)
                }

                row(message("settings.http.test.default.port")) {
                    intTextField(0..65535).bindIntText(state::httpPort)
                }

                row {
                    val serverTable = ServerListTable()
                    cell(serverTable.component)
                            .label(message("settings.http.test.http.servers"), LabelPosition.TOP)
                            .align(AlignX.FILL)
                            .onIsModified { serverTable.isModified() }
                            .onApply { serverTable.apply() }
                            .onReset { serverTable.reset() }
                }

                row {
                    val headerTable = HeaderListTable()
                    cell(headerTable.component)
                            .label(message("settings.http.test.http.headers"), LabelPosition.TOP)
                            .align(AlignX.FILL)
                            .onIsModified { headerTable.isModified() }
                            .onApply { headerTable.apply() }
                            .onReset { headerTable.reset() }
                }.topGap(TopGap.SMALL)
            }
        }
    }
}
