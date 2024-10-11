package com.example.hdwallpaper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.VideoView

class Battery : AppCompatActivity() {
    private lateinit var tvText1: TextView
    private lateinit var tvText2: TextView

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

            // Calculate battery percentage
            val batteryPct: Int = (level / scale.toFloat() * 100).toInt()

            // Update the UI based on charging state
            if (isCharging) {
                tvText1.text = "$batteryPct%"
                tvText2.text = "Connected"
            } else {
                tvText1.text = "$batteryPct% "
                tvText2.text = "Disconnected"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)

        // Initialize VideoView and TextViews
        val videoView: VideoView = findViewById(R.id.vv_battery)
        tvText1 = findViewById(R.id.tv_text1)
        tvText2 = findViewById(R.id.tv_text2)

        // Set up video playback
        val videoPath = "android.resource://" + packageName + "/" + R.raw.appbattery
        val uri: Uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Start video playback
        videoView.start()

        // Restart video after it finishes
        videoView.setOnCompletionListener {
            videoView.start()
        }

        // Register the battery status receiver
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver to avoid memory leaks
        unregisterReceiver(batteryReceiver)
    }
}
