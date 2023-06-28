package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_SYS_COUNTRY = "country"
private const val SER_SYS_ID = "id"
private const val SER_SYS_SUNRISE = "sunrise"
private const val SER_SYS_SUNSET = "sunset"
private const val SER_SYS_TYPE = "type"
private const val SER_SYS_MESSAGE = "message"

@Serializable
data class Sys(
    @SerialName(SER_SYS_COUNTRY)
    val country: String,

    @SerialName(SER_SYS_ID)
    val id: Int? = null,

    @SerialName(SER_SYS_SUNRISE)
    val sunrise: Int,

    @SerialName(SER_SYS_SUNSET)
    val sunset: Int,

    @SerialName(SER_SYS_TYPE)
    val type: Int? = null,

    @SerialName(SER_SYS_MESSAGE)
    val message: String? = null
)
