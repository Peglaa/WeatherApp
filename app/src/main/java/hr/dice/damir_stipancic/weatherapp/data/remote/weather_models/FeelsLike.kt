package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_FEELS_LIKE_DAY = "day"
private const val SER_FEELS_LIKE_EVE = "eve"
private const val SER_FEELS_LIKE_MORN = "morn"
private const val SER_FEELS_LIKE_NIGHT = "night"

@Serializable
data class FeelsLike(
    @SerialName(SER_FEELS_LIKE_DAY)
    val day: Double,

    @SerialName(SER_FEELS_LIKE_EVE)
    val eve: Double,

    @SerialName(SER_FEELS_LIKE_MORN)
    val morn: Double,

    @SerialName(SER_FEELS_LIKE_NIGHT)
    val night: Double
)
