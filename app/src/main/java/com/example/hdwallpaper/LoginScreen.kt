package com.example.hdwallpaper

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hdwallpaper.databinding.ActivityLoginScreenBinding
import com.example.hdwallpaper.viewmodel.LoginViewModel

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observing changes from the ViewModel
        loginViewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                "Success" -> {
                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashBoard::class.java))
                    finish()
                }
                else -> {
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.signinButton.setOnClickListener {
            val email = binding.signinEmail.text.toString().trim()
            val password = binding.signinPassword.text.toString().trim()

            // Delegate login operation to the ViewModel
            loginViewModel.login(email, password)
        }

        binding.tvLoginRedirectText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
    }
}
