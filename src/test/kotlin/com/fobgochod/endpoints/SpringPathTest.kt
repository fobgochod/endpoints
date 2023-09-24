package com.fobgochod.endpoints

import junit.framework.TestCase

class SpringPathTest : TestCase() {

    fun testCase1() {
        val message = "userId:.+"

        val param = message.replace(":.+", "")
        assertTrue(param == "userId")
        val param2 = message.trimEnd(':', '.', '+')
        assertTrue(param2 == "userId")
    }
}
