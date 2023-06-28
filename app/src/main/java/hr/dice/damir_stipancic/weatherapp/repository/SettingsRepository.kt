package hr.dice.damir_stipancic.weatherapp.repository

import hr.dice.damir_stipancic.weatherapp.data.preferences.DataStoreManager
import hr.dice.damir_stipancic.weatherapp.data.preferences.Language
import hr.dice.damir_stipancic.weatherapp.data.preferences.NumberOfDays
import hr.dice.damir_stipancic.weatherapp.data.preferences.Theme
import hr.dice.damir_stipancic.weatherapp.data.preferences.Units
import hr.dice.damir_stipancic.weatherapp.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * This class is used to fetch user preferences.
 *
 * @param [dataStoreManager] A [DataStoreManager] object used to preform dataStore calls.
 */
class SettingsRepository(
    private val dataStoreManager: DataStoreManager
) {

    /**
     * Retrieves all user preferences from preferences DataStore.
     *
     * @return [Flow] of type [UserPreferences]
     */
    fun getPreferences(): Flow<UserPreferences> {
        return dataStoreManager.settingsFlow
    }

    /**
     * A suspend function used to update the type of SI units for measurement.
     *
     * @param [units] an enum of class [Units]
     */
    suspend fun updateUnits(units: Units) {
        dataStoreManager.updateUnits(units)
    }

    /**
     * A suspend function used to update the app language.
     *
     * @param [language] an enum of class [Language]
     */
    suspend fun updateLanguage(language: Language) {
        dataStoreManager.updateLanguage(language)
    }

    /**
     * A suspend function used to update the app theme.
     *
     * @param [theme] an enum of class [Theme]
     */
    suspend fun updateTheme(theme: Theme) {
        dataStoreManager.updateTheme(theme)
    }

    /**
     * A suspend function used to update the number of displayed day for daily weather
     *
     * @param [days] an enum of class [NumberOfDays]
     */
    suspend fun updateDays(days: NumberOfDays) {
        dataStoreManager.updateDays(days)
    }
}
