package dk.weatherforecastapp.openweathermap

import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OneCallService {
    @GET("onecall")
    fun onecall(
        @Query("lat") lat: String, @Query("lon") lon: String,
        @Query("appid") appid: String, @Query("units") units: String = "metric"
    ):
            Call<OneCallResponse>
}
