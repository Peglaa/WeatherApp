package hr.dice.damir_stipancic.weatherapp.data

import androidx.annotation.StringRes
import java.time.LocalDate

sealed class DailyDetails {
    class DailyDetailsHeader(@StringRes val titleId: Int) : DailyDetails()
    class DailyDetailsContent(@StringRes val subtitleId: Int, val value: String) : DailyDetails()
    class DailyDetailsCard(
        val date: LocalDate,
        val temp: String,
        val weatherDescription: String,
        val iconUrl: String
    ) : DailyDetails()
}
