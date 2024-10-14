package com.example.hdwallpaper.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hdwallpaper.activity.RetrofitInstance
import com.example.hdwallpaper.adapter.staggeradapter
import com.example.hdwallpaper.dataclasses.dataclass
import com.example.hdwallpaper.databinding.FragmentHomeBinding
import com.example.hdwallpaper.dataclasses.pixbayclass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<dataclass>
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

        dataList = ArrayList()
        adapter = staggeradapter(dataList, requireActivity())
        recyclerView.adapter = adapter

        fetchImagesFromApi()

        return view
    }

    private fun fetchImagesFromApi() {
        val apiService = RetrofitInstance.api
        apiService.getImages("46503684-bc100fdba61e2f74c88248b50").enqueue(object : Callback<pixbayclass> {
            override fun onResponse(call: Call<pixbayclass>, response: Response<pixbayclass>) {
                if (response.isSuccessful && response.body() != null) {
                    val hits = response.body()!!.hits
                    for (hit in hits) {
                        dataList.add(dataclass(hit.webformatURL, "Sample Image"))
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireActivity(), "Failed to load images", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<pixbayclass>, t: Throwable) {
                Toast.makeText(requireActivity(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
