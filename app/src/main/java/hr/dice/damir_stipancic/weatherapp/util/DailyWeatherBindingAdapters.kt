package hr.dice.damir_stipancic.weatherapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDate

@BindingAdapter("day")
fun TextView.bindingDay(datetime: LocalDate) {
    this.text = getDayOfWeek(datetime, this.context)
}
