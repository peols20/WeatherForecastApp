package dk.simonpeter.weatherforecastapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private const val DATABASE = "Coordinates"

@Database(
    entities = [Coordinates::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CoordinatesDao(): CoordinatesDao

    //code below courtesy of https://github.com/googlesamples/android-sunflower; it     is open
    //source just like this application.
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}


