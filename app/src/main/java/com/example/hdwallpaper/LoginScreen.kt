package com.example.hdwallpaper

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hdwallpaper.databinding.ActivityLoginScreenBinding
import com.example.hdwallpaper.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (FirebaseAuth.getInstance().currentUser != null) {

            startActivity(Intent(this, DashBoard::class.java))
            finish()
            return
        }


        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
        binding.passwordToggle.setOnClickListener {
            togglePasswordVisibility()
        }


        binding.signinButton.setOnClickListener {
            val email = binding.signinEmail.text.toString().trim()
            val password = binding.signinPassword.text.toString().trim()


            loginViewModel.login(email, password)
        }

        binding.tvLoginRedirectText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
    }
    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            binding.signinPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.hide)
        } else {
            // Show password
            binding.signinPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.view)
        }

        binding.signinPassword.setSelection(binding.signinPassword.text?.length ?: 0)
        isPasswordVisible = !isPasswordVisible
    }
}
