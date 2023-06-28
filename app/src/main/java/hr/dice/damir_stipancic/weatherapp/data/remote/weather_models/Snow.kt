package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_SNOW_1H = "1h"
private const val SER_SNOW_3H = "3h"

@Serializable
data class Snow(
    @SerialName(SER_SNOW_1H)
    val oneHour: Double,

    @SerialName(SER_SNOW_3H)
    val threeHours: Double? = null
)
