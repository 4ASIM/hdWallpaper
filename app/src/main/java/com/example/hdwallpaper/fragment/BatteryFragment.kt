import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hdwallpaper.databinding.FragmentBatteryBinding

class BatteryFragment : Fragment() {
    private var _binding: FragmentBatteryBinding? = null
    private val binding get() = _binding!!
    private val batteryViewModel: BatteryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatteryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observing LiveData from ViewModel
        batteryViewModel.batteryPercentage.observe(viewLifecycleOwner, Observer { batteryPct ->
            binding.tvText1.text = "$batteryPct%"
        })

        batteryViewModel.chargingStatus.observe(viewLifecycleOwner, Observer { isCharging ->
            binding.tvText2.text = if (isCharging) "Connected" else "Disconnected"
        })

        batteryViewModel.videoUri.observe(viewLifecycleOwner, Observer { uri ->
            binding.vvBattery.setVideoURI(uri)
            binding.vvBattery.start()
            binding.vvBattery.setOnCompletionListener {
                binding.vvBattery.start()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        batteryViewModel.loadVideoUri(requireContext())
        batteryViewModel.registerBatteryReceiver(requireContext())
    }

    override fun onPause() {
        super.onPause()
        batteryViewModel.unregisterBatteryReceiver(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
