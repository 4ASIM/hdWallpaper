package com.example.hdwallpaper

import BatteryFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hdwallpaper.adapter.AdapterViewPager

import com.example.hdwallpaper.fragment.DownloadFragment
import com.example.hdwallpaper.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashBoard : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        // Initialize ViewPager2
        viewPager = findViewById(R.id.vp_dashbaord)

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigation)

        // Setup fragments list
        val fragments: List<Fragment> = listOf(
            HomeFragment(),
            DownloadFragment(),
            BatteryFragment()
        )

        // Set up adapter with ViewPager2
        val adapter = AdapterViewPager(this, fragments)
        viewPager.adapter = adapter

        // Handle ViewPager swipe to update BottomNavigationView
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.Home
                    1 -> bottomNavigationView.selectedItemId = R.id.Download
                    2 -> bottomNavigationView.selectedItemId = R.id.Battery
                }
            }
        })

        // Handle BottomNavigationView clicks to update ViewPager
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> viewPager.currentItem = 0
                R.id.Download -> viewPager.currentItem = 1
                R.id.Battery -> viewPager.currentItem = 2
            }
            true
        }
    }
}
