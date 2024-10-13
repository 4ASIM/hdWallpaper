package com.example.hdwallpaper
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdwallpaper.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString().trim()
            val password = binding.signupPassword.text.toString().trim()
            val confirmPassword = binding.signupConfirm.text.toString().trim()

            if (validateInput(email, password, confirmPassword)) {
                signUpUser(email, password)
            }
        }

        binding.loginRedirectText.setOnClickListener {

        }
    }


    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {


        if (email.isEmpty()) {
            binding.signupEmail.error = "Email is required"
            binding.signupEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.signupEmail.error = "Enter a valid email"
            binding.signupEmail.requestFocus()
            return false
        }


        if (password.isEmpty()) {
            binding.signupPassword.error = "Password is required"
            binding.signupPassword.requestFocus()
            return false
        }

        if (password.length < 6) {
            binding.signupPassword.error = "Password must be at least 6 characters"
            binding.signupPassword.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.signupConfirm.error = "Please confirm your password"
            binding.signupConfirm.requestFocus()
            return false
        }

        if (password != confirmPassword) {
            binding.signupConfirm.error = "Confirm Password must be same"
            binding.signupConfirm.requestFocus()
            return false
        }

        return true
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginScreen::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign-up fails, show error message
                    Toast.makeText(this, "Sign-up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
