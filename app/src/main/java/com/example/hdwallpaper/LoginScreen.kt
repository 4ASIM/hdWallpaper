package com.example.hdwallpaper
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdwallpaper.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth


class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signinButton.setOnClickListener {
            val email = binding.signinEmail.text.toString().trim()
            val password = binding.signinPassword.text.toString().trim()

            if (validateInput(email, password)) {
                signInUser(email, password)
            }
        }

        binding.tvLoginRedirectText.setOnClickListener {

            startActivity(Intent(this, SignUp::class.java))
            finish()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.signinEmail.error = "Email is required"
            binding.signinEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.signinEmail.error = "Enter a valid email"
            binding.signinEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            binding.signinPassword.error = "Password is required"
            binding.signinPassword.requestFocus()
            return false
        }

        return true
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                     startActivity(Intent(this, DashBoard::class.java))
                } else {
                    Toast.makeText(this, "Sign-in failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
