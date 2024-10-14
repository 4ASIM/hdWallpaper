package com.example.hdwallpaper.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private val auth: FirebaseAuth) {

    fun signUpUser(email: String, password: String, callback: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback("Success")
                } else {
                    callback("Sign-up failed: ${task.exception?.message}")
                }
            }
    }

    fun signInUser(email: String, password: String, callback: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback("Success")
                } else {
                    callback("Sign-in failed: ${task.exception?.message}")
                }
            }
    }
}
