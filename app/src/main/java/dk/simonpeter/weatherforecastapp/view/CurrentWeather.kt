package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.viewmodel.DayListViewModel
import dk.weatherforecastapp.openweathermap.onecall.Daily
import android.view.View
import androidx.fragment.app.activityViewModels

class CurrentWeather : Fragment() {
/*
    private val adaptor = RecyclerAdapter()
*/

    companion object {
        fun newInstance() = CurrentWeather()
    }

    private val dayListViewModel: DayListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("hest", "inflate")
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    fun setWeatherData(daily: Daily) {
        val condition = view?.findViewById(R.id.detail_condition) as TextView
        condition.text = daily.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(DayListViewModel::class.java)



        dayListViewModel.selectedDayWeatherData.observe(viewLifecycleOwner, Observer {
            setWeatherData(it)
        })

    }


}