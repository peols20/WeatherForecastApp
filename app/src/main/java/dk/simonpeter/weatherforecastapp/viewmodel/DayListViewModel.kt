package dk.simonpeter.weatherforecastapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.Daily
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.OneCallResponse
import dk.simonpeter.weatherforecastapp.data.AppDatabase
import dk.simonpeter.weatherforecastapp.data.Coordinates
import dk.simonpeter.weatherforecastapp.tools.Formatting
import dk.simonpeter.weatherforecastapp.openweathermap.OpenWeatherMapServiceBuilder
import dk.simonpeter.weatherforecastapp.tools.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class DayListViewModel(application: Application) : AndroidViewModel(application) {
    @Volatile
    private var running = true
    private val WeatherService = OpenWeatherMapServiceBuilder().getOneCallService()
    private var coordinatesDao = AppDatabase.getInstance(application).CoordinatesDao()
    private var appl = application

    private var coordinateData: Coordinates = Coordinates(0,
                                                            Constants.initCityName,
                                                            Constants.initLatitude,
                                                            Constants.initLongitude)

    private val _citynameData = MutableLiveData<String>()
    val citynameData: LiveData<String>
        get() = _citynameData

    private val _weatherData = MutableLiveData<OneCallResponse>()
    val weatherData: LiveData<OneCallResponse>
        get() = _weatherData

    private val _selectedDayWeatherData = MutableLiveData<Daily>()
    val selectedDayWeatherData: LiveData<Daily>
        get() = _selectedDayWeatherData

    init {

        //fetchCoordinates()
        updateWeather(true)
    }

    fun writeCoordinates(lat: Double,lon: Double,cityName: String){
        _citynameData.postValue(cityName)
        coordinateData = Coordinates(0, cityName, lat.toString(), lon.toString())
        viewModelScope.launch(Dispatchers.IO) {
            if(coordinatesDao.getCount() == 0) {
                Log.i("hest", "insert ")
                val write = coordinatesDao.insertAll(coordinateData)
            }
            else {
                Log.i("hest", "update ")
                val write = coordinatesDao.updateAll(coordinateData)
            }
        }
        updateWeather(false)
    }

    fun updateWeather(fetchInDb: Boolean) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO){
            while(running) {
                if(fetchInDb) {
                    if (coordinatesDao.getCount() == 0) {
                    } else {
                        coordinateData = coordinatesDao.getCoordinates()[0]
                    }
                }
                _citynameData.postValue(coordinateData.city)
                val oneCall = WeatherService.onecall(coordinateData.lat, coordinateData.lon, Constants.openweathermapAppId)
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
        running = true;
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