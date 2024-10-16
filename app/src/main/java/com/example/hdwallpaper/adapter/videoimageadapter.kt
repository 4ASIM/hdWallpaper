package com.example.hdwallpaper.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hdwallpaper.fragment.HomeFragment
import com.example.hdwallpaper.fragment.VideoFragment


class videoimageadapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            HomeFragment()
        } else VideoFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}