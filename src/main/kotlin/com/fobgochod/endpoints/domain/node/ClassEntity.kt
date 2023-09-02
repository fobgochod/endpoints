package com.fobgochod.endpoints.domain.node

import com.intellij.icons.AllIcons
import com.intellij.psi.PsiClass
import javax.swing.Icon

class ClassEntity(val psiClass: PsiClass, icon: Icon = AllIcons.Nodes.Class) :
    BaseEntity(psiClass.name.toString(), icon)
