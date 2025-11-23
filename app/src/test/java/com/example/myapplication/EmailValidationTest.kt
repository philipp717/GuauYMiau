package com.example.myapplication

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidationTest {

    // Simple regex pattern for email validation simulation
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    @Test
    fun emailValidation_CorrectEmail_ReturnsTrue() {
        assertTrue(isValidEmail("usuario@duoc.cl"))
    }

    @Test
    fun emailValidation_IncorrectEmail_ReturnsFalse() {
        assertFalse(isValidEmail("usuario-sin-arroba"))
    }

    @Test
    fun emailValidation_EmptyEmail_ReturnsFalse() {
        assertFalse(isValidEmail(""))
    }
}