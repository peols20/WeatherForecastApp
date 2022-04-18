package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dk.simonpeter.weatherforecastapp.R
import dk.weatherforecastapp.viewmodel.DailyWeatherViewModel

class CurrentWeather : Fragment() {

    //private val adaptor = RecyclerAdapter()
/*
    companion object {
        fun newInstance() = CurrentWeather()
    }
*/
    //private val dailyWeatherViewModel: DailyWeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("hest", "Ged")

        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
/*
        dailyWeatherViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            adaptor.setWeatherData(it)
        })

        dailyWeatherViewModel.updateWeather()*/
    }

}