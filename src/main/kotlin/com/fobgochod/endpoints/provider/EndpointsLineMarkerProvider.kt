package com.fobgochod.endpoints.provider

import com.fobgochod.endpoints.util.EndpointsBundle.message
import com.fobgochod.endpoints.util.EndpointsIcons.LOGO_12X12
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import javax.swing.Icon

class EndpointsLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun getIcon(): Icon {
        return LOGO_12X12
    }

    override fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in RelatedItemLineMarkerInfo<*>>) {
        if (element is PsiMethod) {
            val builder = NavigationGutterIconBuilder.create(LOGO_12X12)
                    .setAlignment(GutterIconRenderer.Alignment.CENTER)
                    .setTargets(element)
                    .setTooltipTitle(message("action.editor.navigate.view.text"))
            val nameIdentifier = element.nameIdentifier
            if (nameIdentifier != null) {
                result.add(builder.createLineMarkerInfo(nameIdentifier))
            }
        }
    }
}
