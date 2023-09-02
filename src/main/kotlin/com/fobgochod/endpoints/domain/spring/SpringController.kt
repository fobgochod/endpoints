package com.fobgochod.endpoints.domain.spring

enum class SpringController(val qualifiedName: String) {

    Controller("org.springframework.stereotype.Controller"),
    RestController("org.springframework.web.bind.annotation.RestController");
}
