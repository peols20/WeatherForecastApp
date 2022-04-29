package dk.simonpeter.weatherforecastapp.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

object Formatting {

    var zone: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    fun unixDateTimeFormatter(time: Int, /*zone: String,*/ format: String = "EEE, MMMM d"): String {
        val zoneId = ZoneId.of(zone)
        val instant = Instant.ofEpochSecond(time.toLong())
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }

    fun degeesToDirection(degrees: Double): String {
        val directions = arrayOf<String>("N", "NE", "E", "SE", "S", "SV", "V", "NV", "N")
        var dIndex: Double = degrees % 360
        var index = (dIndex / 45).roundToInt() + 1
        return directions.get(index)
    }

}