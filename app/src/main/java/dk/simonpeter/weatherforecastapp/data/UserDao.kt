package dk.simonpeter.weatherforecastapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

//    @Query("SELECT * FROM user_table")
//    suspend fun getCity(): User
//
//    @Query("SELECT * FROM user_table WHERE city = :city")
//    suspend fun getCoordinates(city: String): User
}