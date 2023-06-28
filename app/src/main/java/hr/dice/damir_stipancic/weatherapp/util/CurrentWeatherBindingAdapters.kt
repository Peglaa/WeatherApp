package hr.dice.damir_stipancic.weatherapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.preferences.Units
import hr.dice.damir_stipancic.weatherapp.data.preferences.UserPreferences

@BindingAdapter("currentTemp", "currentTempIdentifier")
fun TextView.bindingCurrentTempText(temperature: Double, tempIdentifier: String?) {
    this.text = this.resources.getString(R.string.temp_tv, temperature.toInt(), tempIdentifier)
}

@BindingAdapter("currentHumidity")
fun TextView.bindingCurrentHumidityText(humidity: Int) {
    this.text = this.resources.getString(R.string.humidity_tv, humidity)
}

@BindingAdapter("currentPressure")
fun TextView.bindingCurrentPressureText(pressure: Int) {
    this.text = this.resources.getString(R.string.pressure_tv, pressure)
}

@BindingAdapter("currentWind", "currentSpeedIdentifier")
fun TextView.bindingWindText(wind: Double, preferences: UserPreferences?) {
    this.text = when (val units = preferences?.units) {
        Units.IMPERIAL -> this.resources.getString(
            R.string.wind_tv,
            wind.toInt(),
            units.speedIdentifier
        )
        else -> this.resources.getString(
            R.string.wind_tv,
            (wind * 3.6).toInt(),
            units?.speedIdentifier
        )
    }
}

@BindingAdapter("weather")
fun TextView.bindingWeatherText(weather: String?) {
    this.text = weather?.replaceFirstChar {
        it.uppercase()
    }
}

@BindingAdapter("weatherImage")
fun ImageView.bindingWeatherImage(iconId: String?) {
    if (iconId != null) {
        val imageUrl = "http://openweathermap.org/img/wn/$iconId@2x.png"
        println(imageUrl)
        this.load(imageUrl)
    }
}
