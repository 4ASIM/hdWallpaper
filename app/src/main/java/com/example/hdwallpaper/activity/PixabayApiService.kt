import com.example.hdwallpaper.dataclasses.pixbayclass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {
    @GET("/api/")
    fun getImages(
        @Query("key") apiKey: String,
        @Query("image_type") imageType: String = "photo",
        @Query("per_page") perPage: Int = 50
    ): Call<pixbayclass>
}
