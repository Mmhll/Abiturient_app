package com.mhl.abiturient.classes

import java.io.Serializable

data class Professions(
    val about : String? = "",
    val budget : String? = "",
    val code : String? = "",
    val image : String? = "",
    val name : String? = "",
    val years : ArrayList<Years>? = null
) : Serializable
data class Years(
    val year : String? = "",
    val url : String? = ""
)
