package com.mhl.myapplication.classes

import com.mhl.myapplication.classes.Authorization
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthorizationTest {

    @Test
    fun correctEmail() {
        assertTrue(Authorization().checkEmail("assan_-@gmail.com"))
    }

    @Test
    fun correct() {
        assertTrue(Authorization().checkPassword("12345asdvvcvcvvxvvxcvcx"))
    }

    @Test
    fun passwordWhitespace() {
        assertFalse(Authorization().checkPassword("12345asdv vcvcvvxvvxcvcx"))
    }

    @Test
    fun passwordLess() {
        assertFalse(Authorization().checkPassword("12345"))
    }

    @Test
    fun passwordMore() {
        assertFalse(Authorization().checkPassword("1111111111111111111111111111111111"))
    }
}