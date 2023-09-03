package com.fobgochod.endpoints.view.http.editor

import com.fobgochod.endpoints.action.http.HttpTestActionGroup
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsNotify
import com.intellij.ide.ui.customization.CustomizationUtil
import com.intellij.json.JsonFileType
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.ui.EditorTextField
import com.intellij.util.LocalTimeCounter
import com.intellij.util.ui.JBUI
import javax.swing.border.Border

class HttpTextField(project: Project, fileType: FileType = JsonFileType.INSTANCE) : EditorTextField(project, fileType) {

    init {
        isOneLineMode = false

        CustomizationUtil.installPopupHandler(this, HttpTestActionGroup::class.java.name, ActionPlaces.POPUP)
    }

    private fun setupCustomTextFieldEditor(editor: EditorEx) {
        val settings = editor.settings
        settings.additionalLinesCount = 0
        settings.additionalColumnsCount = 1
        settings.isRightMarginShown = false
        settings.setRightMargin(-1)
        settings.isFoldingOutlineShown = true
        settings.isLineNumbersShown = true
        settings.isLineMarkerAreaShown = true
        settings.isIndentGuidesShown = false
        settings.isVirtualSpace = false
        settings.isWheelFontChangeEnabled = false
        settings.isAdditionalPageAtBottom = false
        editor.setHorizontalScrollbarVisible(true)
        editor.setVerticalScrollbarVisible(true)
        settings.lineCursorWidth = 1
    }

    fun setText(text: String, fileType: FileType) {
        super.setFileType(fileType)
        val document = createDocument(text, fileType)
        setDocument(document)
        val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document!!)
        if (psiFile != null) {
            try {
                WriteCommandAction.runWriteCommandAction(project) {
                    CodeStyleManager.getInstance(project).reformat(psiFile)
                }
            } catch (e: Exception) {
                EndpointsNotify.error(e.message.toString(), project)
            }
        }
    }

    override fun setFileType(fileType: FileType) {
        setNewDocumentAndFileType(fileType, createDocument(getText(), fileType))
    }

    override fun createDocument(): Document? {
        return createDocument("", fileType)
    }

    private fun initOneLineModePre(editor: EditorEx) {
        editor.isOneLineMode = false
        editor.colorsScheme = editor.createBoundColorSchemeDelegate(null)
        editor.settings.isCaretRowShown = false
    }

    override fun createEditor(): EditorEx {
        val editor = super.createEditor()
        initOneLineModePre(editor)
        setupCustomTextFieldEditor(editor)
        return editor
    }

    override fun repaint(tm: Long, x: Int, y: Int, width: Int, height: Int) {
        super.repaint(tm, x, y, width, height)
        if (editor is EditorEx) {
            initOneLineModePre((editor as EditorEx))
        }
    }

    override fun setBorder(border: Border?) {
        super.setBorder(JBUI.Borders.empty())
    }

    private fun createDocument(text: String, fileType: FileType): Document? {
        val factory = PsiFileFactory.getInstance(project)
        val stamp = LocalTimeCounter.currentTime()
        val psiFile = factory.createFileFromText(message("plugin.name"), fileType, text, stamp, true, false)
        return PsiDocumentManager.getInstance(project).getDocument(psiFile)
    }
}
