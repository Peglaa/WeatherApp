package hr.dice.damir_stipancic.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.dice.damir_stipancic.weatherapp.data.location.LocationEntry

@Database(entities = [LocationEntry::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao
}
