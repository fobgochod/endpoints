package com.fobgochod.endpoints.domain.node

import javax.swing.Icon

abstract class BaseEntity(var name: String, val icon: Icon) {

    override fun toString(): String {
        return name
    }
}
