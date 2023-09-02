package com.fobgochod.endpoints.domain.node

import com.intellij.icons.AllIcons
import com.intellij.psi.PsiMethod
import javax.swing.Icon

class MethodEntity(val psiMethod: PsiMethod, icon: Icon = AllIcons.Nodes.Method) : BaseEntity(psiMethod.name, icon)
