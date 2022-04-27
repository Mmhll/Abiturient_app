package com.mhl.myapplication.classes

class Authorization {


    fun auth(email: String, password: String): Boolean {
        if (checkEmail(email) && checkPassword(password)) {
            return true
        }
        return false
    }

    fun checkEmail(email: String): Boolean {
        var regex = Regex("[A-z0-9\\-\\_]{5,18}+\\@+[a-z]{2,10}+\\.+[a-z]{2,5}")
        if (regex.matches(email)) {
            return true
        }
        return false
    }

    fun checkPassword(password: String): Boolean {
        var regex = Regex("^\\S{6,24}$*")
        if (regex.matches(password)) {
            return true
        }
        return false
    }
}