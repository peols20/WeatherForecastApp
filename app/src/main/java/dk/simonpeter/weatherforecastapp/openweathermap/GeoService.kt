package dk.simonpeter.weatherforecastapp.openweathermap

import dk.simonpeter.weatherforecastapp.openweathermap.geo.GeoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {
    @GET("direct")
    fun geocoding(
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("limit") limit: String = "5"
    ):
            Call<GeoResponse>
}