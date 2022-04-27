package com.mhl.myapplication.classes

import com.google.firebase.database.*

class DatabaseFirebase {

    fun instanceProfs(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Professions")
    }

}