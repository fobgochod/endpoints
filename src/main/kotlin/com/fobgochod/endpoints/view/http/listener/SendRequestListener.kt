package com.fobgochod.endpoints.view.http.listener

import com.fobgochod.endpoints.view.http.EndpointsTestPane
import com.intellij.openapi.progress.ProgressManager
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class SendRequestListener(private val testPane: EndpointsTestPane) : ActionListener {

    override fun actionPerformed(e: ActionEvent) {
        ProgressManager.getInstance().run(SendRequestTask(testPane))
    }
}