package hr.dice.damir_stipancic.weatherapp.data.remote.weather_models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val SER_DAILY_CLOUDS = "clouds"
private const val SER_DAILY_DEW_POINT = "dew_point"
private const val SER_DAILY_DT = "dt"
private const val SER_DAILY_FEELS_LIKE = "feels_like"
private const val SER_DAILY_HUMIDITY = "humidity"
private const val SER_DAILY_MOON_PHASE = "moon_phase"
private const val SER_DAILY_MOONRISE = "moonrise"
private const val SER_DAILY_MOONSET = "moonset"
private const val SER_DAILY_POP = "pop"
private const val SER_DAILY_PRESSURE = "pressure"
private const val SER_DAILY_RAIN = "rain"
private const val SER_DAILY_SNOW = "snow"
private const val SER_DAILY_SUNRISE = "sunrise"
private const val SER_DAILY_SUNSET = "sunset"
private const val SER_DAILY_TEMP = "temp"
private const val SER_DAILY_UVI = "uvi"
private const val SER_DAILY_WEATHER = "weather"
private const val SER_DAILY_WIND_DEG = "wind_deg"
private const val SER_DAILY_WIND_GUST = "wind_gust"
private const val SER_DAILY_WIND_SPEED = "wind_speed"

@Serializable
data class Daily(
    @SerialName(SER_DAILY_CLOUDS)
    val clouds: Int,

    @SerialName(SER_DAILY_DEW_POINT)
    val dewPoint: Double,

    @SerialName(SER_DAILY_DT)
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,

    @SerialName(SER_DAILY_FEELS_LIKE)
    val feelsLike: FeelsLike,

    @SerialName(SER_DAILY_HUMIDITY)
    val humidity: Int,

    @SerialName(SER_DAILY_MOON_PHASE)
    val moonPhase: Double,

    @SerialName(SER_DAILY_MOONRISE)
    val moonrise: Int,

    @SerialName(SER_DAILY_MOONSET)
    val moonset: Int,

    @SerialName(SER_DAILY_POP)
    val pop: Double,

    @SerialName(SER_DAILY_PRESSURE)
    val pressure: Int,

    @SerialName(SER_DAILY_RAIN)
    val rain: Double? = null,

    @SerialName(SER_DAILY_SNOW)
    val snow: Double? = null,

    @SerialName(SER_DAILY_SUNRISE)
    @Serializable(with = LocalDateTimeSerializer::class)
    val sunrise: LocalDateTime,

    @SerialName(SER_DAILY_SUNSET)
    @Serializable(with = LocalDateTimeSerializer::class)
    val sunset: LocalDateTime,

    @SerialName(SER_DAILY_TEMP)
    val temp: Temp,

    @SerialName(SER_DAILY_UVI)
    val uvi: Double,

    @SerialName(SER_DAILY_WEATHER)
    val weather: List<Weather>,

    @SerialName(SER_DAILY_WIND_DEG)
    val windDeg: Int,

    @SerialName(SER_DAILY_WIND_GUST)
    val windGust: Double? = null,

    @SerialName(SER_DAILY_WIND_SPEED)
    val windSpeed: Double
) {
    val formattedDate: String
        get() {
            val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            return date.format(formatter)
        }
}

/**
 * Serializer object that is used to deserialize epoch seconds [Long] value to [LocalDate] object
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "LocalDateSerializer",
        kind = PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): LocalDate {
        val dateTime = decoder.decodeLong()

        // API returns epoch seconds so we need to convert it to epoch days
        return LocalDate.ofEpochDay(dateTime / 86400)
    }
}

/**
 * Serializer object that is used to deserialize epoch seconds [Long] value to [LocalDateTime] object
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "LocalDateTimeSerializer",
        kind = PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val dateTime = decoder.decodeLong()
        return LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC)
    }
}
