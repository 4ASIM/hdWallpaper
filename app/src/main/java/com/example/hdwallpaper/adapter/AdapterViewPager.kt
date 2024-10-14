package com.example.hdwallpaper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterViewPager
    (fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // Returns the fragment for a given position
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
