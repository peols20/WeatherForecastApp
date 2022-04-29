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
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.tools.Formatting
import dk.weatherforecastapp.openweathermap.onecall.OneCallResponse
import java.util.*
import kotlin.math.roundToInt


class RecyclerAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerAdapter.Viewholder>() {

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
        vh.itemView.setOnClickListener(View.OnClickListener { // get the position of this Vh
            val position: Int = vh.absoluteAdapterPosition
            itemClickListener.onItemClick(position)
        })
        return vh
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        try {
            val date = Formatting.unixDateTimeFormatter(weatherData.daily[position].dt, weatherData.timezone, "EEEE, MMMM d")
            holder.itemTitle.text = date
            holder.itemDetail.text = weatherData.daily[position].temp.day.roundToInt().toString() + "\u00B0"
            val icon: String = weatherData.daily[position].weather[0].icon
            val iconUrl = "https://openweathermap.org/img/w/$icon.png"
            Glide.with(context)
                //.load("https://openweathermap.org/img/wn/10d.png")
                .load(iconUrl)
                .override(200, 200)
                .into(holder.itemPicture)

        } catch (e: Exception)  {
            Log.e("", "OnBindViewHolder fejler: " + e.toString())
        }

    }

    override fun getItemCount(): Int {
        try {
            return weatherData.daily.size
        } catch (e: Exception) {
            return 0
        }
    }


}

interface ItemClickListener {
    fun onItemClick(position: Int)
}
