package com.example.hdwallpaper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.adapter.VideoAdapter
import com.example.hdwallpaper.dataclasses.VideoHit
import com.example.hdwallpaper.databinding.FragmentVideoBinding
import com.example.hdwallpaper.modelview.VideoViewModel
import com.tashila.pleasewait.PleaseWaitDialog

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoList: ArrayList<VideoHit>
    private lateinit var adapter: VideoAdapter
    private lateinit var viewModel: VideoViewModel
    private lateinit var progressDialog: PleaseWaitDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val view = binding.root


        progressDialog = PleaseWaitDialog(context = requireContext()).apply {
            setTitle("Please wait")
            setMessage("Loading videos...")
            setCancelable(false)
        }

        recyclerView = binding.rvVideos
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        videoList = ArrayList()
        adapter = VideoAdapter(videoList, requireContext())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)

        progressDialog.show()

        viewModel.videoList.observe(viewLifecycleOwner, Observer { videos ->
            videoList.clear()
            videoList.addAll(videos)
            adapter.notifyDataSetChanged()
            progressDialog.dismiss()
        })

        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        })

        viewModel.fetchVideos()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        progressDialog.dismiss()
    }
}
