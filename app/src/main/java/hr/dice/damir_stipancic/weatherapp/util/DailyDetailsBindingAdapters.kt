package hr.dice.damir_stipancic.weatherapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.DailyDetails

@BindingAdapter("detailsImage")
fun ImageView.bindingDetailsImage(iconId: String?) {
    if (iconId != null) {
        val imageUrl = "http://openweathermap.org/img/wn/$iconId@2x.png"
        this.load(imageUrl)
    }
}

@BindingAdapter("detailsWeatherDescription")
fun TextView.bindDetailsWeatherDescription(desc: String) {
    this.text = desc.replaceFirstChar { it.uppercase() }
}

@BindingAdapter("detailsSubtitle")
fun TextView.bindDetailsSubtitle(dailyDetails: DailyDetails.DailyDetailsContent) {
    this.text = this.resources.getString(dailyDetails.subtitleId)
}

@BindingAdapter("detailsContent")
fun TextView.bindDetailsContent(dailyDetails: DailyDetails.DailyDetailsContent) {
    if (dailyDetails.subtitleId == R.string.direction)
        this.text = degToDirection(this.context, dailyDetails.value.toInt())
    else
        this.text = dailyDetails.value
}
