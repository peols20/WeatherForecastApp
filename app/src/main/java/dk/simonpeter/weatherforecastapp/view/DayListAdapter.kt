package dk.simonpeter.weatherforecastapp.view

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import dk.simonpeter.weatherforecastapp.R
import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class RecyclerAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)
    }

    private lateinit var weatherData: OneCallResponse
    fun setWeatherData(data: OneCallResponse) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val vh = Viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
        vh.itemView.setOnClickListener(View.OnClickListener { // get the position of this Vh
            val position: Int = vh.absoluteAdapterPosition
            itemClickListener.onItemClick(position)
        })
        return vh
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        try {
            val date = dateTime(weatherData.daily[position].dt, weatherData.timezone)
            holder.itemTitle.text = date
            holder.itemDetail.text = weatherData.daily[position].temp.day.toString()
        } catch (e: Exception)  {}
    }

    override fun getItemCount(): Int {
        try {
            return weatherData.daily.size
        } catch (e: Exception) {
            return 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateTime(time: Int, zone: String, format: String = "EEE, MMMM d"): String {
        // parse the time zone
        val zoneId = ZoneId.of(zone)
        // create a moment in time from the given timestamp (in seconds!)
        val instant = Instant.ofEpochSecond(time.toLong())
        // define a formatter using the given pattern and a Locale
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        // then make the moment in time consider the zone and return the formatted String
        return instant.atZone(zoneId).format(formatter)
    }

}

interface ItemClickListener {
    fun onItemClick(position: Int)
}
