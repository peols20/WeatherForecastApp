package dk.simonpeter.weatherforecastapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val uid: Int,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "coordinates")
    val coordinates: String

)