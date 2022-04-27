package com.mhl.myapplication.classes

import org.junit.Assert.*
import org.junit.Test

class RegistrationTest {

    @Test
    fun validateDate() {
        assertTrue(Registration().validateDate("30.09.2020"))
    }

    @Test
    fun validateRepeatPasswordTrue() {
        assertTrue(Registration().validateRepeatPassword("123456", "123456"))
    }

    @Test
    fun validateRepeatPasswordFalse() {
        assertFalse(Registration().validateRepeatPassword("123456", "12345"))
    }

}