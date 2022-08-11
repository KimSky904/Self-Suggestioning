package com.eng.selfsuggestion.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {
    val auth = Firebase.auth

    fun checkUser(): FirebaseUser? {
        val currunt = auth.currentUser
        return currunt
    }

}