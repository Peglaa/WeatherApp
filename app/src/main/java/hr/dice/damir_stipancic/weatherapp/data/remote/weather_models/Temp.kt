package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_TEMP_DAY = "day"
private const val SER_TEMP_EVE = "eve"
private const val SER_TEMP_MAX = "max"
private const val SER_TEMP_MIN = "min"
private const val SER_TEMP_MORN = "morn"
private const val SER_TEMP_NIGHT = "night"

@Serializable
data class Temp(
    @SerialName(SER_TEMP_DAY)
    val day: Double,

    @SerialName(SER_TEMP_EVE)
    val eve: Double,

    @SerialName(SER_TEMP_MAX)
    val max: Double,

    @SerialName(SER_TEMP_MIN)
    val min: Double,

    @SerialName(SER_TEMP_MORN)
    val morn: Double,

    @SerialName(SER_TEMP_NIGHT)
    val night: Double
)
