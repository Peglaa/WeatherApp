package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_DAILY_WEATHER_DAILY_LIST = "daily"
private const val SER_DAILY_WEATHER_LAT = "lat"
private const val SER_DAILY_WEATHER_LON = "lon"
private const val SER_DAILY_WEATHER_TIMEZONE = "timezone"
private const val SER_DAILY_WEATHER_TIMEZONE_OFFSET = "timezone_offset"

@Serializable
data class DailyWeather(
    @SerialName(SER_DAILY_WEATHER_DAILY_LIST)
    val daily: List<Daily>,

    @SerialName(SER_DAILY_WEATHER_LAT)
    val lat: Double,

    @SerialName(SER_DAILY_WEATHER_LON)
    val lon: Double,

    @SerialName(SER_DAILY_WEATHER_TIMEZONE)
    val timezone: String,

    @SerialName(SER_DAILY_WEATHER_TIMEZONE_OFFSET)
    val timezoneOffset: Int
)
