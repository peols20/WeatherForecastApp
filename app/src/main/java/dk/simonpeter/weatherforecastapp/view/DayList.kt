package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.viewmodel.DayListViewModel

class DayList : Fragment() {

    companion object {
        fun newInstance() = DayList()
    }

    val icl: ItemClickListener = object : ItemClickListener {
        override fun onItemClick(position: Int) {
            // your code here when the user clicks the row
            Log.i("hest", "qwe" + position)

            dayListViewModel.updateSelectedDayWeatherData(position)
            Log.i("hest", "qwe2" + position)

            val currentWeatherFragment = CurrentWeather()
            val activity = view!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, currentWeatherFragment)
                .addToBackStack(null).commit()

        }
    }

    private lateinit var days_recyclerView: RecyclerView
    private val adaptor = RecyclerAdapter(icl)
    private val dayListViewModel: DayListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.day_list_fragment, container, false)
        days_recyclerView = view.findViewById<RecyclerView>(R.id.days_recyclerView)
        days_recyclerView.layoutManager = LinearLayoutManager(context)
        days_recyclerView.adapter = adaptor

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(DayListViewModel::class.java)



        dayListViewModel.weatherData.observe(viewLifecycleOwner, Observer {
            adaptor.setWeatherData(it)
        })

    }

}