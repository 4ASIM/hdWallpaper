package com.example.hdwallpaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hdwallpaper.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val authRepository: AuthRepository = AuthRepository(FirebaseAuth.getInstance())

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun login(email: String, password: String) {
        if (validateInput(email, password)) {
            authRepository.signInUser(email, password) { result ->
                _loginResult.value = result
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        when {
            email.isEmpty() -> {
                _loginResult.value = "Email is required"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _loginResult.value = "Enter a valid email"
                return false
            }
            password.isEmpty() -> {
                _loginResult.value = "Password is required"
                return false
            }
            else -> return true
        }
    }
}
