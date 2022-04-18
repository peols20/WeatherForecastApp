package dk.simonpeter.weatherforecastapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.simonpeter.weatherforecastapp.R
import dk.weatherforecastapp.viewmodel.DailyWeatherViewModel

class MainActivity : AppCompatActivity() {
    //private var titlesList = mutableListOf<String>()
    //private var descList = mutableListOf<String>()
    //private var imagesList = mutableListOf<Int>()
    private lateinit var rv_recyclerView: RecyclerView
    private val dailyWeatherViewModel: DailyWeatherViewModel by viewModels()
    private val adaptor = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rv_recyclerView = findViewById<RecyclerView>(R.id.rv_recyclerView)

        //postToList()
        Log.i("hest", "Ko")

        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = adaptor
        //rv_recyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList)

        dailyWeatherViewModel.weatherData.observe(this , { data ->
            adaptor.setWeatherData(data)
        })
    }
/*
    private fun addToList(title: String, description: String, image: Int){
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..10) {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher)
        }
    }

 */
}