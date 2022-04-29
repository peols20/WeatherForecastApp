package dk.simonpeter.weatherforecastapp.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object Formatting {

    @RequiresApi(Build.VERSION_CODES.O)
    fun unixDateTimeFormatter(time: Int, zone: String, format: String = "EEE, MMMM d"): String {
        val zoneId = ZoneId.of(zone)
        val instant = Instant.ofEpochSecond(time.toLong())
        val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
        return instant.atZone(zoneId).format(formatter)
    }

}