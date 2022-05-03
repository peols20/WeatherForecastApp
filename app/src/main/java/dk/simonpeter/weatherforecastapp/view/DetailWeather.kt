package dk.simonpeter.weatherforecastapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.viewmodel.DayListViewModel
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.Daily
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import dk.simonpeter.weatherforecastapp.tools.Constants
import dk.simonpeter.weatherforecastapp.tools.Formatting
import kotlin.math.ceil
import kotlin.math.roundToInt

class DetailWeather : Fragment() {

    private val dayListViewModel: DayListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_weather_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setWeatherData(daily: Daily) {
        val date = view?.findViewById(R.id.detail_date) as TextView
        date.text = Formatting.unixDateTimeFormatter(daily.dt, "EEEE, MMMM d")

        val iconPic = view?.findViewById(R.id.detail_condition_icon) as ImageView
        val icon: String = daily.weather[0].icon
        Glide.with(this)
            .load(Constants.openweatherIconUrl + icon + Constants.openweathermapIconExtension)
            .override(200, 200)
            .into(iconPic)

        val temperature = view?.findViewById(R.id.detail_temperature) as TextView
        temperature.text = getString(R.string.temperature, daily.temp.day.roundToInt().toString())

        val feelsLikeTemperature = view?.findViewById(R.id.detail_feels_like_temperature) as TextView
        feelsLikeTemperature.text = getString(R.string.feels_like, daily.feels_like.day.roundToInt().toString())

        val wind = view?.findViewById(R.id.detail_wind) as TextView
        wind.text = getString(R.string.wind,
            daily.wind_speed.roundToInt().toString(),
            daily.wind_gust.roundToInt().toString(),
            Formatting.degeesToDirection(daily.wind_deg.toDouble()))

        val precipitation = view?.findViewById(R.id.detail_precipitation) as TextView
        precipitation.text = getString(R.string.precipitation,
            ceil(daily.rain).toInt().toString(),
            (daily.pop*100).toInt().toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dayListViewModel.selectedDayWeatherData.observe(viewLifecycleOwner, Observer {
            setWeatherData(it)
        })

    }


}