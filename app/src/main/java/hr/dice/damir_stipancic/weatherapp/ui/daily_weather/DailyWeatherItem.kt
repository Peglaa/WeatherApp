package hr.dice.damir_stipancic.weatherapp.ui.daily_weather

import java.time.LocalDate

data class DailyWeatherItem(
    val date: LocalDate,
    val formattedDate: String,
    val dayTemp: String,
    val minTemp: String,
    val maxTemp: String,
    val humidity: String,
    val pressure: String,
    val windSpeed: String
)
