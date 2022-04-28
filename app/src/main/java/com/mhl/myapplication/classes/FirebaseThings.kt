package com.mhl.myapplication.classes

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseThings {

    fun instanceProfs(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Professions")
    }
    fun instanceUsers(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Users")
    }
    fun instanceStorage() : StorageReference {
        return FirebaseStorage.getInstance().getReference("Docs")
    }
}