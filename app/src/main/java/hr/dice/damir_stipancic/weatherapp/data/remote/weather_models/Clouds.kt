package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_CLOUDS_ALL = "all"

@Serializable
data class Clouds(
    @SerialName(SER_CLOUDS_ALL)
    val all: Int
)
