package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_CURRENT_WEATHER_BASE = "base"
private const val SER_CURRENT_WEATHER_CLOUDS = "clouds"
private const val SER_CURRENT_WEATHER_COD = "cod"
private const val SER_CURRENT_WEATHER_COORD = "coord"
private const val SER_CURRENT_WEATHER_DT = "dt"
private const val SER_CURRENT_WEATHER_ID = "id"
private const val SER_CURRENT_WEATHER_MAIN = "main"
private const val SER_CURRENT_WEATHER_NAME = "name"
private const val SER_CURRENT_WEATHER_RAIN = "rain"
private const val SER_CURRENT_WEATHER_SNOW = "snow"
private const val SER_CURRENT_WEATHER_SYS = "sys"
private const val SER_CURRENT_WEATHER_TIMEZONE = "timezone"
private const val SER_CURRENT_WEATHER_VISIBILITY = "visibility"
private const val SER_CURRENT_WEATHER_WEATHER = "weather"
private const val SER_CURRENT_WEATHER_WIND = "wind"

@Serializable
data class CurrentWeather(
    @SerialName(SER_CURRENT_WEATHER_BASE)
    val base: String,

    @SerialName(SER_CURRENT_WEATHER_CLOUDS)
    val clouds: Clouds,

    @SerialName(SER_CURRENT_WEATHER_COD)
    val cod: Int,

    @SerialName(SER_CURRENT_WEATHER_COORD)
    val coord: Coord,

    @SerialName(SER_CURRENT_WEATHER_DT)
    val dt: Int,

    @SerialName(SER_CURRENT_WEATHER_ID)
    val id: Int,

    @SerialName(SER_CURRENT_WEATHER_MAIN)
    val main: Main,

    @SerialName(SER_CURRENT_WEATHER_NAME)
    val name: String,

    @SerialName(SER_CURRENT_WEATHER_RAIN)
    val rain: Rain? = null,

    @SerialName(SER_CURRENT_WEATHER_SNOW)
    val snow: Snow? = null,

    @SerialName(SER_CURRENT_WEATHER_SYS)
    val sys: Sys,

    @SerialName(SER_CURRENT_WEATHER_TIMEZONE)
    val timezone: Int,

    @SerialName(SER_CURRENT_WEATHER_VISIBILITY)
    val visibility: Int,

    @SerialName(SER_CURRENT_WEATHER_WEATHER)
    val weather: List<Weather>,

    @SerialName(SER_CURRENT_WEATHER_WIND)
    val wind: Wind
)
