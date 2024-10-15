package com.example.hdwallpaper

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hdwallpaper.databinding.ActivitySignUpBinding
import com.example.hdwallpaper.viewmodel.SignUpViewModel

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordToggle.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.confirmpasswordToggle.setOnClickListener {
            toggleconfirmPasswordVisibility()
        }

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

            signUpViewModel.signUp(email, password, confirmPassword)
        }

        binding.loginRedirectText.setOnClickListener {

            startActivity(Intent(this, LoginScreen::class.java))
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            binding.signupPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.hide)
        } else {
            // Show password
            binding.signupPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordToggle.setImageResource(R.drawable.view)
        }

        binding.signupPassword.setSelection(binding.signupPassword.text?.length ?: 0)

        isPasswordVisible = !isPasswordVisible
    }

    private fun toggleconfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            // Hide confirm password
            binding.signupConfirm.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.confirmpasswordToggle.setImageResource(R.drawable.hide)
        } else {
            // Show confirm password
            binding.signupConfirm.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.confirmpasswordToggle.setImageResource(R.drawable.view)
        }

        binding.signupConfirm.setSelection(binding.signupConfirm.text?.length ?: 0)
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }

}
