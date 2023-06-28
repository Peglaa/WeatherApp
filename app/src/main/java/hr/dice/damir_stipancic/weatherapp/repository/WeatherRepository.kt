package hr.dice.damir_stipancic.weatherapp.repository

import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.data.remote.WeatherApi
import hr.dice.damir_stipancic.weatherapp.data.remote.callApi
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.CurrentWeather
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.DailyWeather

private const val DAILY_WEATHER_BASE_URL = "https://api.openweathermap.org/data/3.0/onecall?"

/**
 * This class represents the applications main repository.
 * It is the middle man between the apps UI and the data source(network API) and it is used to fetch
 * current and daily weather data information.
 *
 * @param [weatherApi] network api used to perform network calls
 */
class WeatherRepository(
    private val weatherApi: WeatherApi
) {

    /**
     * Retrieves current weather data from the network API
     *
     * @param [latitude] GPS location latitude
     * @param [longitude] GPS location longitude
     * @return a [Resource] object of type [CurrentWeather] that contains the network API response data or error data
     */
    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        units: String,
        language: String
    ): Resource<CurrentWeather> {
        return callApi {
            weatherApi.getCurrentWeather(
                latitude = latitude,
                longitude = longitude,
                units = units,
                language = language
            )
        }
    }

    /**
     * Retrieves daily weather data from the network API
     *
     * @param [latitude] GPS location latitude
     * @param [longitude] GPS location longitude
     * @return a [Resource] object of type [DailyWeather] that contains the network API response data or error data
     */
    suspend fun getDailyWeather(
        latitude: String,
        longitude: String,
        units: String,
        language: String
    ): Resource<DailyWeather> {
        return callApi {
            weatherApi.getDailyWeather(
                url = DAILY_WEATHER_BASE_URL,
                latitude = latitude,
                longitude = longitude,
                units = units,
                language = language
            )
        }
    }
}
