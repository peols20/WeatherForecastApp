package dk.weatherforecastapp.openweathermap

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OneCallServiceBuilder {
    private val url = "https://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit = Retrofit.Builder().baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var OneCallService: OneCallService = retrofit.create(dk.weatherforecastapp.openweathermap.OneCallService::class.java)

    fun getOneCallService(): OneCallService {
        Log.i("hest", "Service build")
        return OneCallService
    }
}