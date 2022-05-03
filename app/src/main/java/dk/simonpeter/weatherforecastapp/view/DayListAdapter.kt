package dk.simonpeter.weatherforecastapp.view

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.tools.Constants
import dk.simonpeter.weatherforecastapp.tools.Formatting
import dk.simonnpeter.weatherforecastapp.openweathermap.onecall.OneCallResponse
import kotlin.math.roundToInt


class RecyclerAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)
    }

    private lateinit var context: Context

    private lateinit var weatherData: OneCallResponse
    fun setWeatherData(data: OneCallResponse) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val vh = Viewholder(LayoutInflater.from(context).inflate(R.layout.day_list_item_layout, parent, false))
        vh.itemView.setOnClickListener { // get the position of this Vh
            val position: Int = vh.absoluteAdapterPosition
            itemClickListener.onItemClick(position)
        }
        return vh
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        try {
            val date = Formatting.unixDateTimeFormatter(weatherData.daily[position].dt, "EEEE, MMMM d")
            holder.itemTitle.text = date
            holder.itemDetail.text = context.getString(R.string.temperature,
                weatherData.daily[position].temp.day.roundToInt().toString())
            val icon: String = weatherData.daily[position].weather[0].icon
            Glide.with(context)
                .load(Constants.openweatherIconUrl + icon + Constants.openweathermapIconExtension)
                .override(200, 200)
                .into(holder.itemPicture)

        } catch (e: Exception)  {
            Log.e("", "OnBindViewHolder fejler: " + e.toString())
        }
    }

    override fun getItemCount(): Int {
        return try {
            weatherData.daily.size
        } catch (e: Exception) {
            0
        }
    }


}

interface ItemClickListener {
    fun onItemClick(position: Int)
}
