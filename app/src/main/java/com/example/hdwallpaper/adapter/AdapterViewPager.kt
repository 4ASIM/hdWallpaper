package com.example.hdwallpaper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterViewPager // Constructor to initialize the adapter with a list of fragments
    (fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {
    // Returns the number of fragments
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // Returns the fragment for a given position
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
