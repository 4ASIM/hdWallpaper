package com.example.hdwallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hdwallpaper.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    private val authRepository: AuthRepository = AuthRepository(FirebaseAuth.getInstance())

    private val _signUpResult = MutableLiveData<String>()
    val signUpResult: LiveData<String> = _signUpResult

    fun signUp(email: String, password: String, confirmPassword: String) {
        if (validateInput(email, password, confirmPassword)) {
            authRepository.signUpUser(email, password) { result ->
                _signUpResult.value = result
            }
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        when {
            email.isEmpty() -> {
                _signUpResult.value = "Email is required"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _signUpResult.value = "Enter a valid email"
                return false
            }
            password.isEmpty() -> {
                _signUpResult.value = "Password is required"
                return false
            }
            password.length < 6 -> {
                _signUpResult.value = "Password must be at least 6 characters"
                return false
            }
            confirmPassword.isEmpty() -> {
                _signUpResult.value = "Please confirm your password"
                return false
            }
            password != confirmPassword -> {
                _signUpResult.value = "Confirm Password must be the same"
                return false
            }
            else -> return true
        }
    }
}
