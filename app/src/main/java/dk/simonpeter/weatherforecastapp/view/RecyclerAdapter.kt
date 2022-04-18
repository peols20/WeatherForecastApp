package dk.simonpeter.weatherforecastapp.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dk.simonpeter.weatherforecastapp.R
import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import java.lang.Exception
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)


        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = absoluteAdapterPosition //adapterPosition
                //Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private lateinit var weatherData: OneCallResponse
    fun setWeatherData(data: OneCallResponse) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return Viewholder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        try {
            val date = dateTime(weatherData.daily[position].dt, weatherData.timezone)
            holder.itemTitle.text = date
            holder.itemDetail.text = weatherData.daily[position].temp.day.toString()
        } catch (e: Exception)  {}

        //holder.itemTitle.text = weatherData.daily[position].dt.toString()
        //holder.itemDetail.text = weatherData.daily[position].temp.day.toString()
        //holder.itemPicture.setImageResource(images[position]) ToDo Hvordan finder vi det korrekte billede?

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val activity = p0!!.context as AppCompatActivity
                val demoFragment = CurrentWeather()
                activity.supportFragmentManager.beginTransaction().replace(R.id.rec, demoFragment)
                    .addToBackStack(null).commit()

            }
        })

    }

    override fun getItemCount(): Int {
        try {
            return weatherData.daily.size
        } catch (e: Exception) {
            return 1
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateTime(time: Int, zone: String, format: String = "EEE, MMMM d K:mm a"): String {
        // parse the time zone
        val zoneId = ZoneId.of(zone)
        // create a moment in time from the given timestamp (in seconds!)
        val instant = Instant.ofEpochSecond(time.toLong())
        // define a formatter using the given pattern and a Locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.GERMAN)
        // then make the moment in time consider the zone and return the formatted String
        return instant.atZone(zoneId).format(formatter)
    }

}
/*
class RecyclerAdapter (private var titles: List<String>, private var details: List<String>, private var images: List<Int>) :
    RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {


    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)


        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                //Toast.makeText(itemView.context, "You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemPicture.setImageResource(images[position])

        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                val activity=p0!!.context as AppCompatActivity
                val demoFragment= CurrentWeather()
                activity.supportFragmentManager.beginTransaction().replace(R.id.rec, demoFragment).addToBackStack(null).commit()

            }
        })

    }

    override fun getItemCount(): Int {
        return titles.size
    }
}
*/