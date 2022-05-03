package dk.simonpeter.weatherforecastapp.openweathermap

import dk.simonpeter.weatherforecastapp.openweathermap.GeoService
import dk.simonpeter.weatherforecastapp.tools.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherMapServiceBuilder {
    private var weatherDataRetrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.openweathermapDataUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var OneCallService: OneCallService = weatherDataRetrofit.create(dk.simonpeter.weatherforecastapp.openweathermap.OneCallService::class.java)

    private var weatherGeoRetrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.openweathermapGeoUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var GeoService: GeoService = weatherGeoRetrofit.create(dk.simonpeter.weatherforecastapp.openweathermap.GeoService::class.java)

    fun getOneCallService(): OneCallService {
        return OneCallService
    }

    fun getGeoService(): GeoService {
        return GeoService
    }
}