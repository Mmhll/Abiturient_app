package com.mhl.myapplication.classes

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Registration {

    fun validateDate(date: String): Boolean {
        val format = SimpleDateFormat("dd.MM.yyyy")
        var valid = true
        try {
            if (
                LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                ).year > LocalDate.now().year ||
                LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                ).year < LocalDate.parse(
                    "01.01.1900",
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                ).year

            ) {
                valid = false
            }
        } catch (e: Exception) {
            valid = false
        }
        format.isLenient = false

        try {
            format.parse(date)
        } catch (e: Exception) {
            valid = false
        }
        return valid
    }

    fun validateRepeatPassword(password1: String, password2: String): Boolean {
        if (password1 == password2) {
            return true
        }
        return false
    }

    fun validateInitials(part: String): Boolean {
        val regex = Regex("^[A-zА-я\\s]{2,30}$*")
        if (regex.matches(part)) {
            return true
        }
        return false
    }
}