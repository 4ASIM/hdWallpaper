package com.example.hdwallpaper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.adapter.downloadadapter
import com.example.hdwallpaper.databinding.FragmentDownloadBinding
import com.example.hdwallpaper.roomdb.AppDatabase
import com.example.hdwallpaper.roomdb.ImageDao
import com.example.hdwallpaper.roomdb.ImageEntity

import com.google.firebase.auth.FirebaseAuth

class DownloadFragment : Fragment() {

    private var _binding: FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: downloadadapter
    private lateinit var imageDao: ImageDao
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        recyclerView = binding.rvImages
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        val db = AppDatabase.getDatabase(requireContext())
        imageDao = db.imageDao()

        val currentUser = auth.currentUser
        currentUser?.let {
            loadUserImages(it.email!!)
        }

        return view
    }

    private fun loadUserImages(email: String) {

        imageDao.getImagesByEmail(email).observe(viewLifecycleOwner, { images ->
            if (images != null) {
                adapter = downloadadapter(images, requireContext())
                recyclerView.adapter = adapter
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
