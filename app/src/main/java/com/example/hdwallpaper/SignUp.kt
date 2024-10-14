package com.example.hdwallpaper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hdwallpaper.databinding.ActivitySignUpBinding
import com.example.hdwallpaper.viewmodel.SignUpViewModel

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observing changes from the ViewModel
        signUpViewModel.signUpResult.observe(this, Observer { result ->
            when (result) {
                "Success" -> {
                    Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginScreen::class.java))
                    finish()
                }
                else -> {
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString().trim()
            val password = binding.signupPassword.text.toString().trim()
            val confirmPassword = binding.signupConfirm.text.toString().trim()

            // Delegate sign-up operation to the ViewModel
            signUpViewModel.signUp(email, password, confirmPassword)
        }

        binding.loginRedirectText.setOnClickListener {
            // Redirect to login if necessary
            startActivity(Intent(this, LoginScreen::class.java))
        }
    }
}
