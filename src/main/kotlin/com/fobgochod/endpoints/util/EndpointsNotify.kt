package com.fobgochod.endpoints.util

import com.fobgochod.mock.serializer.Constants
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import java.awt.Color
import java.time.LocalDateTime
import javax.swing.JTextPane
import javax.swing.text.BadLocationException
import javax.swing.text.Document
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants

object EndpointsNotify {

    fun info(content: String, project: Project? = null) {
        notify(content, project, NotificationType.INFORMATION)
    }

    fun warning(content: String, project: Project? = null) {
        notify(content, project, NotificationType.WARNING)
    }

    fun error(content: String, project: Project? = null) {
        notify(content, project, NotificationType.ERROR)
    }

    private fun notify(content: String, project: Project?, type: NotificationType) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("Endpoints Notification")
                .createNotification(content, type)
                .notify(project)
    }

    fun debug(console: JTextPane, message: String) {
        render(console, message, JBColor.GRAY)
    }

    fun info(console: JTextPane, message: String) {
        render(console, message, JBColor.foreground())
    }

    fun warn(console: JTextPane, message: String) {
        render(console, message, JBColor.MAGENTA)
    }

    fun error(console: JTextPane, message: String) {
        render(console, message, JBColor.RED)
    }

    private fun render(console: JTextPane, content: String, color: Color) {
        val document: Document = console.document
        try {
            val message = String.format("%s - %s\n", LocalDateTime.now().format(Constants.DATETIME_FORMATTER), content)

            val style = SimpleAttributeSet()
            StyleConstants.setForeground(style, color)
            document.insertString(0, message, style)
        } catch (ignored: BadLocationException) {
        }
    }
}
