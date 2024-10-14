package com.example.hdwallpaper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.R
import com.example.hdwallpaper.adapter.downloadadapter
import com.example.hdwallpaper.databinding.FragmentDownloadBinding
import com.example.hdwallpaper.databinding.FragmentHomeBinding
import com.example.hdwallpaper.dataclasses.dataclass
import com.example.hdwallpaper.dataclasses.downloaddataclass

class DownloadFragment : Fragment() {

    private var _binding: FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<downloaddataclass>
    private lateinit var adapter: downloadadapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.rvImages
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Initialize the list with data class instances
        dataList = arrayListOf(
            downloaddataclass(R.drawable.tree1, "Sample Image 1"),
            downloaddataclass(R.drawable.tree2, "Sample Image 2"),
            downloaddataclass(R.drawable.tree4, "Sample Image 3"),
            downloaddataclass(R.drawable.tree5, "Sample Image 3"),
            downloaddataclass(R.drawable.tree6, "Sample Image 3"),
            downloaddataclass(R.drawable.tree1, "Sample Image 3"),
            downloaddataclass(R.drawable.tree2, "Sample Image 3"),
            downloaddataclass(R.drawable.tree4, "Sample Image 3"),
            downloaddataclass(R.drawable.tree5, "Sample Image 3"),
            downloaddataclass(R.drawable.tree1, "Sample Image 3"),
            downloaddataclass(R.drawable.wallpaperapplication, "Sample Image 4")
        )

        adapter = downloadadapter(dataList, requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
