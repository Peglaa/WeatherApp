package hr.dice.damir_stipancic.weatherapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * A class responsible for handling storage and updates for user app preferences(app settings).
 *
 * @param [context] a [Context] object used to access DataStore preferences
 */
class DataStoreManager(context: Context) {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")
    private val dataStore = context._dataStore

    val settingsFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            val units = Units.valueOf(
                preferences[UNITS] ?: Units.METRIC.name
            )

            val language = Language.valueOf(
                preferences[LANGUAGE] ?: Language.ENGLISH.name
            )

            val theme = Theme.valueOf(
                preferences[THEME] ?: Theme.DEFAULT.name
            )

            val days = NumberOfDays.valueOf(
                preferences[DAYS] ?: NumberOfDays.SEVEN.name
            )

            UserPreferences(units, language, theme, days)
        }.distinctUntilChanged()

    /**
     * A suspend function used to update the type of SI units for measurement.
     *
     * @param [units] an enum of class [Units]
     */
    suspend fun updateUnits(units: Units) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[UNITS] = units.name
            }
        }
    }

    /**
     * A suspend function used to update the app language.
     *
     * @param [language] an enum of class [Language]
     */
    suspend fun updateLanguage(language: Language) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[LANGUAGE] = language.name
            }
        }
    }

    /**
     * A suspend function used to update the app theme.
     *
     * @param [theme] an enum of class [Theme]
     */
    suspend fun updateTheme(theme: Theme) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[THEME] = theme.name
            }
        }
    }

    /**
     * A suspend function used to update the number of displayed day for daily weather
     *
     * @param [days] an enum of class [NumberOfDays]
     */
    suspend fun updateDays(days: NumberOfDays) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[DAYS] = days.name
            }
        }
    }

    /**
     * [Object] for storing and fetching preference key values
     */
    companion object PreferenceKeys {
        private val THEME = stringPreferencesKey("THEME")
        private val LANGUAGE = stringPreferencesKey("LANGUAGE")
        private val DAYS = stringPreferencesKey("DAYS")
        private val UNITS = stringPreferencesKey("UNITS")
    }
}
