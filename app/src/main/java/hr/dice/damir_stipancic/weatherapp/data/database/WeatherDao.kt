package hr.dice.damir_stipancic.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hr.dice.damir_stipancic.weatherapp.data.location.COL_IS_ACTIVE
import hr.dice.damir_stipancic.weatherapp.data.location.LocationEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: LocationEntry): Long

    @Query("SELECT * FROM location WHERE $COL_IS_ACTIVE = 1")
    fun getActiveLocation(): Flow<LocationEntry?>

    @Update
    suspend fun updateLocationCity(location: LocationEntry)
}
