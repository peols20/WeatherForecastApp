package dk.simonpeter.weatherforecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.simonpeter.weatherforecastapp.openweathermap.OpenWeatherMapServiceBuilder
import dk.simonpeter.weatherforecastapp.openweathermap.geo.GeoResponse
import dk.simonpeter.weatherforecastapp.tools.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchCityViewModel : ViewModel() {

    private val geoService = OpenWeatherMapServiceBuilder().getGeoService()

    private val _searchData = MutableLiveData<GeoResponse>()
    val searchData: MutableLiveData<GeoResponse>
        get() = _searchData

    fun searchCity(searchText: String) {
        // Launch coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO){

            val geoService = geoService.geocoding(searchText, Constants.openweathermapAppId)
            val resp = geoService.execute().body()!!

            _searchData.postValue(resp)
        }
    }
}