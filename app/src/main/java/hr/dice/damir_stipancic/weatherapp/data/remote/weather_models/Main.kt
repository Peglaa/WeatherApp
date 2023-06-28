package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_MAIN_FEELS_LIKE = "feels_like"
private const val SER_MAIN_HUMIDITY = "humidity"
private const val SER_MAIN_PRESSURE = "pressure"
private const val SER_MAIN_TEMP = "temp"
private const val SER_MAIN_TEMP_MAX = "temp_max"
private const val SER_MAIN_TEMP_MIN = "temp_min"
private const val SER_MAIN_SEA_LEVEL = "sea_level"
private const val SER_MAIN_GROUND_LEVEL = "grnd_level"

@Serializable
data class Main(
    @SerialName(SER_MAIN_FEELS_LIKE)
    val feelsLike: Double,

    @SerialName(SER_MAIN_HUMIDITY)
    val humidity: Int,

    @SerialName(SER_MAIN_PRESSURE)
    val pressure: Int,

    @SerialName(SER_MAIN_TEMP)
    val temp: Double,

    @SerialName(SER_MAIN_TEMP_MAX)
    val tempMax: Double,

    @SerialName(SER_MAIN_TEMP_MIN)
    val tempMin: Double,

    @SerialName(SER_MAIN_SEA_LEVEL)
    val sea_level: Int? = null,

    @SerialName(SER_MAIN_GROUND_LEVEL)
    val ground_level: Int? = null
)
