package com.mhl.myapplication.classes

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Registration {

    fun validateDate(date : String) : Boolean{
        val format = SimpleDateFormat("dd.MM.yyyy")
        var valid = true
        try {
            if (LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")).year > LocalDate.now().year) {
                valid = false
            }
        }
        catch (e : Exception){
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

    fun validateRepeatPassword(password1 : String, password2 : String) : Boolean{
        if (password1 == password2){
            return true
        }
        return false
    }
}