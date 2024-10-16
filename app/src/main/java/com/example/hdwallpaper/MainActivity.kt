package com.example.hdwallpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hdwallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  var binding: ActivityMainBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //added
        Handler().postDelayed({

            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}