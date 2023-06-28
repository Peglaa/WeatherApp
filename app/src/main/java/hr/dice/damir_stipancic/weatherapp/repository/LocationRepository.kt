package hr.dice.damir_stipancic.weatherapp.repository

import hr.dice.damir_stipancic.weatherapp.data.database.WeatherDao
import hr.dice.damir_stipancic.weatherapp.data.location.LocationEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * This class is responsible for fetching location information from the local database
 *
 * @param [databaseDao] A [WeatherDao] object responsible for making database calls
 */
class LocationRepository(
    private val databaseDao: WeatherDao
) {

    /**
     * Adds a [location] to the database
     *
     * @param [location] A [LocationEntry] object to be inserted
     */
    suspend fun insertLocation(location: LocationEntry) {
        return withContext(Dispatchers.IO) {
            databaseDao.insertLocation(location)
        }
    }

    /**
     * Updates the the current active location to include the name of the city
     *
     * @param [location] A [LocationEntry] object to be updated
     */
    suspend fun updateLocationCity(location: LocationEntry) {
        return withContext(Dispatchers.IO) {
            databaseDao.updateLocationCity(location)
        }
    }

    /**
     * Retrieves the current active location from the database
     * @return a [LocationEntry] or null if the app is run for the first time
     */
    fun getActiveLocation(): Flow<LocationEntry?> {
        return databaseDao.getActiveLocation()
    }
}
