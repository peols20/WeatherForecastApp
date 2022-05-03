package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.viewmodel.DayListViewModel


class DayList : Fragment() {

    private val icl: ItemClickListener = object : ItemClickListener {
        override fun onItemClick(position: Int) {
            dayListViewModel.updateSelectedDayWeatherData(position)

            val currentWeatherFragment = DetailWeather()
            val activity = view!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentWeatherFragment)
                .addToBackStack(null).commit()
        }
    }

    private lateinit var daysRecyclerView: RecyclerView
    private var adaptor: RecyclerAdapter = RecyclerAdapter(icl)
    private val dayListViewModel: DayListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.day_list_fragment, container, false)

        daysRecyclerView = view.findViewById<RecyclerView>(R.id.days_recyclerView)
        daysRecyclerView.layoutManager = LinearLayoutManager(context)
        //adaptor = RecyclerAdapter(context, icl)
        daysRecyclerView.adapter = adaptor

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dayListViewModel.weatherData.observe(viewLifecycleOwner) {
            adaptor.setWeatherData(it)
        }
    }

}