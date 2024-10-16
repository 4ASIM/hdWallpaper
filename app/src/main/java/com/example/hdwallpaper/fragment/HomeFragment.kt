package com.example.hdwallpaper.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.adapter.staggeradapter
import com.example.hdwallpaper.dataclasses.dataclass
import com.example.hdwallpaper.databinding.FragmentHomeBinding
import com.example.hdwallpaper.modelview.HomeViewModel
import com.tashila.pleasewait.PleaseWaitDialog
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<dataclass>
    private lateinit var adapter: staggeradapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var progressDialog: PleaseWaitDialog

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        progressDialog = PleaseWaitDialog(context = requireContext()).apply {
            setTitle("Please wait")
            setMessage("Loading...")
            setCancelable(false)
        }

        recyclerView = binding.rvImages
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        dataList = ArrayList()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Show the progress dialog when fetching images
        progressDialog.show()

        viewModel.dataList.observe(viewLifecycleOwner, Observer { images ->
            dataList.clear()
            dataList.addAll(images)
            adapter.notifyDataSetChanged()
            progressDialog.dismiss()
        })

        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        })

        adapter = staggeradapter(dataList, requireContext())
        recyclerView.adapter = adapter

        checkStoragePermission()

        viewModel.fetchImages()

        return view
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        progressDialog.dismiss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with operation
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Permission denied to write to storage",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
