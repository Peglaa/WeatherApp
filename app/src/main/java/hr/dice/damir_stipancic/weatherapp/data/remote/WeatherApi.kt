package hr.dice.damir_stipancic.weatherapp.data.remote

import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.CurrentWeather
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.DailyWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

const val API_KEY = "bbdcac06f5112c4479f7c19dc2d106e0"

/**
 * A retrofit2 interface for endpoints
 */
interface WeatherApi {

    /**
     * Retrofit2 GET method that retrieves current weather information from network API
     *
     * @param [latitude] GPS location latitude
     * @param [longitude] GPS location longitude
     * @param [units] system of unit measurements
     * @param [language] preferred API response language
     * @param [apiKey] API key for call authorization
     * @return [Response] object of type [CurrentWeather]
     */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") apiKey: String = API_KEY
    ): Response<CurrentWeather>

    /**
     * Retrofit2 GET method that retrieves daily weather information from network API
     *
     * @param [latitude] GPS location latitude
     * @param [longitude] GPS location longitude
     * @param [units] system of unit measurements
     * @param [language] preferred API response language
     * @param [exclude] string of comma separated values representing responses to be ignored
     * @param [apiKey] API key for call authorization
     * @return [Response] object of type [DailyWeather]
     */
    @GET
    suspend fun getDailyWeather(
        @Url url: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("exclude") exclude: String = "minutely,hourly,current,alerts",
        @Query("appid") apiKey: String = API_KEY
    ): Response<DailyWeather>
}
