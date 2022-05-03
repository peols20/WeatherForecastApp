package dk.simonpeter.weatherforecastapp.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.Daily
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.OneCallResponse
import dk.simonpeter.weatherforecastapp.tools.Formatting
import dk.simonpeter.weatherforecastapp.openweathermap.OpenWeatherMapServiceBuilder
import dk.simonpeter.weatherforecastapp.tools.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class DayListViewModel : ViewModel() {
    @Volatile
    private var running = true
    private val WeatherService = OpenWeatherMapServiceBuilder().getOneCallService()

    private val _weatherData = MutableLiveData<OneCallResponse>()
    val weatherData: LiveData<OneCallResponse>
        get() = _weatherData

    private val _selectedDayWeatherData = MutableLiveData<Daily>()
    val selectedDayWeatherData: LiveData<Daily>
        get() = _selectedDayWeatherData

    init {
        updateWeather()
    }

    fun updateWeather() {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO){
            while(running) {
                val lat = "55.4269" //ToDo hent i DB
                val lon = "10.4714" //ToDo hent i DB
                val oneCall = WeatherService.onecall(lat, lon, Constants.openweathermapAppId)
                val resp = oneCall.execute().body()

                if (resp != null) {
                    Formatting.zone = resp.timezone;

                    _weatherData.postValue(resp!!)

                    updateSelectedDayWeatherData(0)

                    running = false;
                } else {
                    // Suspend the coroutine for a seconds and try again
                    delay(1000)
                }
            }
        }
    }

    fun updateSelectedDayWeatherData(position: Int) {
        try {
            _selectedDayWeatherData.postValue(weatherData.value!!.daily[position])
        } catch (e: Exception) {}

    }

    override fun onCleared() {
        running = false
        super.onCleared()
    }

}