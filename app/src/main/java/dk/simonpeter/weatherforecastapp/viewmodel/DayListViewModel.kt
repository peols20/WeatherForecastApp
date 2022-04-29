package dk.simonpeter.weatherforecastapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.simonpeter.weatherforecastapp.tools.Formatting
import dk.weatherforecastapp.openweathermap.OneCallServiceBuilder
import dk.weatherforecastapp.openweathermap.onecall.Daily
import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class DayListViewModel : ViewModel() {
    @Volatile
    private var running = true
    private val WeatherService = OneCallServiceBuilder().getOneCallService()

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
                try {
                    val oneCall = WeatherService.onecall("55.4269", "10.4714","e3914f805149a337ef279793a4a34093")
                    val resp = oneCall.execute().body()

                    Formatting.zone = resp!!.timezone;

                    _weatherData.postValue(resp!!) /// ToDo Der bør laves errorhandling i stedet for at bruge !!
                    // Suspend the coroutine for 60 seconds
                    delay(60000)
                } catch (e: Exception) {
                    // ToDo Error handling / recovery / graceful termination
                }
            }
        }
    }

    fun updateSelectedDayWeatherData(position: Int) {
        _selectedDayWeatherData.postValue(weatherData.value!!.daily[position])
    }

    override fun onCleared() {
        running = false
        super.onCleared()
    }

}