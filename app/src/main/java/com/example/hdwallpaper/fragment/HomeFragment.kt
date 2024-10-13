package com.example.hdwallpaper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.R
import com.example.hdwallpaper.adapter.staggeradapter
import com.example.hdwallpaper.databinding.FragmentHomeBinding
import com.example.hdwallpaper.dataclasses.dataclass

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<dataclass>  // Use dataclass here
    private lateinit var adapter: staggeradapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.rvImages
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Initialize the list with data class instances
        dataList = arrayListOf(
            dataclass(R.drawable.tree1, "Sample Image 1"),  // Update with your actual image names
            dataclass(R.drawable.tree2, "Sample Image 2"),
            dataclass(R.drawable.tree4, "Sample Image 3"),
            dataclass(R.drawable.tree5, "Sample Image 3"),
            dataclass(R.drawable.tree6, "Sample Image 3"),
            dataclass(R.drawable.tree1, "Sample Image 3"),
            dataclass(R.drawable.tree2, "Sample Image 3"),
            dataclass(R.drawable.tree4, "Sample Image 3"),
            dataclass(R.drawable.tree5, "Sample Image 3"),
            dataclass(R.drawable.tree1, "Sample Image 3"),



            dataclass(R.drawable.wallpaperapplication, "Sample Image 4")
        )

        // Set up the adapter with the list of dataclass instances
        adapter = staggeradapter(dataList, requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
