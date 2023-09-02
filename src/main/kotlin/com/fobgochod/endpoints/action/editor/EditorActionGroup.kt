package com.fobgochod.endpoints.action.editor

import com.fobgochod.endpoints.action.EndpointsActionGroup
import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsIcons

class EditorActionGroup : EndpointsActionGroup() {

    init {
        templatePresentation.icon = EndpointsIcons.LOGO
        templatePresentation.text = message("action.editor.group.text")
    }
}
