package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_WIND_DEG = "deg"
private const val SER_WIND_SPEED = "speed"
private const val SER_WIND_GUST = "gust"

@Serializable
data class Wind(
    @SerialName(SER_WIND_DEG)
    val deg: Int,

    @SerialName(SER_WIND_SPEED)
    val speed: Double,

    @SerialName(SER_WIND_GUST)
    val gust: Double? = null
)
