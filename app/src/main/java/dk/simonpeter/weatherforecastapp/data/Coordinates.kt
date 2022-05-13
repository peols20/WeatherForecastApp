package dk.simonpeter.weatherforecastapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Coordinates")
data class Coordinates (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var uid: Int,

    @ColumnInfo(name = "city")
    var city: String,

    @ColumnInfo(name = "lat")
    var lat: String,

    @ColumnInfo(name = "lon")
    var lon: String
)