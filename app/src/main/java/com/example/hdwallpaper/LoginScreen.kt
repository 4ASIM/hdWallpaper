package com.example.hdwallpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hdwallpaper.databinding.ActivityLoginScreenBinding
import com.example.hdwallpaper.databinding.ActivityMainBinding

class LoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLoginRedirectText.setOnClickListener{
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

    }
}