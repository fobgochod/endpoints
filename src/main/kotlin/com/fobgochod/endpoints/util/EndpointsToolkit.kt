package com.fobgochod.endpoints.util

import com.intellij.openapi.diagnostic.logger
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

object EndpointsToolkit {

    private val logger = logger<EndpointsToolkit>()

    /**
     * 把文本设置到剪贴板（复制）
     */
    fun copy(text: String) {
        // 获取系统剪贴板
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        // 封装文本内容
        val trans = StringSelection(text)
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null)
    }

    /**
     * 从剪贴板中获取文本（粘贴）
     */
    fun paste(): String {
        // 获取系统剪贴板
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        // 获取剪贴板中的内容
        val trans = clipboard.getContents(null)
        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    return trans.getTransferData(DataFlavor.stringFlavor) as String
                } catch (e: Exception) {
                    logger.error("黏贴文件", e)
                }
            }
        }
        return ""
    }
}
