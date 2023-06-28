package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val SER_COORD_LAT = "lat"
private const val SER_COORD_LON = "lon"

@Serializable
data class Coord(
    @SerialName(SER_COORD_LAT)
    val lat: Double,

    @SerialName(SER_COORD_LON)
    val lon: Double
)
