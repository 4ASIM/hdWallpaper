// WallpaperViewModel.kt
import android.app.Application
import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException

class WallpaperViewModel(application: Application) : AndroidViewModel(application) {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun setHomeScreenWallpaper(imagePath: String) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(getApplication())
            val bitmap = BitmapFactory.decodeFile(imagePath)
            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            _toastMessage.value = "Home screen wallpaper set successfully!"
        } catch (e: IOException) {
            e.printStackTrace()
            _toastMessage.value = "Failed to set home screen wallpaper"
        }
    }

    fun setWallpaper(imagePath: String) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(getApplication())
            val bitmap = BitmapFactory.decodeFile(imagePath)
            wallpaperManager.setBitmap(bitmap)
            _toastMessage.value = "Wallpaper set successfully!"
        } catch (e: IOException) {
            e.printStackTrace()
            _toastMessage.value = "Failed to set wallpaper"
        }
    }

    fun setLockScreenWallpaper(imagePath: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(getApplication())
                val bitmap = BitmapFactory.decodeFile(imagePath)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                _toastMessage.value = "Lock screen wallpaper set successfully!"
            } catch (e: IOException) {
                e.printStackTrace()
                _toastMessage.value = "Failed to set lock screen wallpaper"
            }
        } else {
            _toastMessage.value = "Lock screen wallpaper can only be set on Android N (API 24) and above."
        }
    }
}
