package dk.simonpeter.weatherforecastapp.database

import androidx.room.*


@Dao
interface CoordinatesDao {
    @Query("SELECT * FROM Coordinates")
    fun getCoordinates(): List<Coordinates>

    @Query("SELECT COUNT(*) from Coordinates")
    fun getCount(): Int

    @Insert
    fun insertAll(vararg coordinates: Coordinates)

    @Update
    fun updateAll(vararg coordinates: Coordinates)

    @Delete
    fun deleteAll(coordinates: Coordinates)

}