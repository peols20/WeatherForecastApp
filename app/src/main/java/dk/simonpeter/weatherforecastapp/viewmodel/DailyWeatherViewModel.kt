package dk.weatherforecastapp.viewmodel

import android.R
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.weatherforecastapp.openweathermap.OneCallServiceBuilder
import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyWeatherViewModel : ViewModel() {

    //private lateinit var job: Job
    //@Volatile
    //private var running = true
    private val WeatherService = OneCallServiceBuilder().getOneCallService()
    private val _weatherData = MutableLiveData<OneCallResponse>()
    val weatherData: LiveData<OneCallResponse>
        get() = _weatherData

    init {
        updateWeather()
    }

    fun updateWeather() {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO){
            //while(running) {
                val oneCall = WeatherService.onecall("55.4269", "10.4714","e3914f805149a337ef279793a4a34093")
                val resp = oneCall.execute().body()
                //val uiString = resp!!.timezone

                _weatherData.postValue(resp!!) /// ToDo Der bør laves errorhandling i stedet for at bruge !!
            Log.i("hest", "Hest")
            Log.i("hest", resp!!.timezone)
                // Suspend the coroutine for 5 seconds
            //    delay(5000)
            //}
        }
    }
/*
    override fun onCleared() {
        running = false
        super.onCleared()
        // Cancel the coroutine job when the view model is no longer used
        //job.cancel()
    }
*/
}