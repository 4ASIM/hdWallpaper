package com.example.hdwallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hdwallpaper.adapter.AdapterViewPager
import com.example.hdwallpaper.fragment.BatteryFragment

import com.example.hdwallpaper.fragment.DownloadFragment
import com.example.hdwallpaper.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashBoard : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)


        // dashbaord toolbar
//        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        // Disable the default title
//        supportActionBar?.setDisplayShowTitleEnabled(false)


        viewPager = findViewById(R.id.vp_dashbaord)


        bottomNavigationView = findViewById(R.id.bottomNavigation)

        val fragments: List<Fragment> = listOf(
            tablayout(),
            DownloadFragment(),
            BatteryFragment()
        )


        val adapter = AdapterViewPager(this, fragments)
        viewPager.adapter = adapter


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
