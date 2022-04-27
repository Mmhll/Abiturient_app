package com.mhl.myapplication.classes

data class Professions(
    val about : String? = "",
    val image : String? = "",
    val name : String? = "",
    val years : ArrayList<Years>? = null
)
data class Years(
    val year : String? = "",
    val url : String? = ""
)
