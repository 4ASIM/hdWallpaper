import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hdwallpaper.R

class BatteryViewModel(application: Application) : AndroidViewModel(application) {

    private val _batteryPercentage = MutableLiveData<Int>()
    val batteryPercentage: LiveData<Int> get() = _batteryPercentage

    private val _chargingStatus = MutableLiveData<Boolean>()
    val chargingStatus: LiveData<Boolean> get() = _chargingStatus

    private val _videoUri = MutableLiveData<Uri>()
    val videoUri: LiveData<Uri> get() = _videoUri

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL

            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct: Int = (level / scale.toFloat() * 100).toInt()

            _batteryPercentage.value = batteryPct
            _chargingStatus.value = isCharging
        }
    }

    fun registerBatteryReceiver(context: Context) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)
    }

    fun unregisterBatteryReceiver(context: Context) {
        context.unregisterReceiver(batteryReceiver)
    }

    fun loadVideoUri(context: Context) {
        val videoPath = "android.resource://" + context.packageName + "/" + R.raw.appbattery
        _videoUri.value = Uri.parse(videoPath)
    }
}
