package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_WEATHER_DESCRIPTION = "description"
private const val SER_WEATHER_ICON = "icon"
private const val SER_WEATHER_ID = "id"
private const val SER_WEATHER_MAIN = "main"

@Serializable
data class Weather(
    @SerialName(SER_WEATHER_DESCRIPTION)
    val description: String,

    @SerialName(SER_WEATHER_ICON)
    val icon: String,

    @SerialName(SER_WEATHER_ID)
    val id: Int,

    @SerialName(SER_WEATHER_MAIN)
    val main: String
)
