package hr.dice.damir_stipancic.weatherapp.util

import android.content.Context
import android.icu.text.DateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import hr.dice.damir_stipancic.weatherapp.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A function responsible for determining if there is an active internet connection on the device
 *
 * @param [context] required to perform necessary system service checks
 *
 * @return [Boolean] If there is an internet connection or not
 */
fun hasInternetConnection(context: Context): Boolean {
    val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

        else -> false
    }
}

/**
 * A function that converts degrees to a general compass bearing(8 points)
 *
 * @param [deg] degrees represented as an Integer value in the range from 0 to 360
 * @return [String], [IllegalStateException] Returns a string representation of the name of the bearing
 * or throws an exception if the value is out of bounds
 */
fun degToDirection(context: Context, deg: Int): String {
    return when (deg) {
        in 0..22 -> context.getString(R.string.north)
        in 23..67 -> context.getString(R.string.northeast)
        in 68..112 -> context.getString(R.string.east)
        in 113..157 -> context.getString(R.string.southeast)
        in 158..202 -> context.getString(R.string.south)
        in 203..247 -> context.getString(R.string.southwest)
        in 248..292 -> context.getString(R.string.west)
        in 293..337 -> context.getString(R.string.northwest)
        in 338..360 -> context.getString(R.string.north)
        else -> throw IllegalStateException("Degrees out of bounds!")
    }
}

/**
 * A function that extracts hours and minutes from a [LocalDateTime] object and returns it as a
 * string based on provided pattern
 *
 * @param [dateTime] a [LocalDateTime] object
 * @param [pattern] string pattern representing the extracted time,
 * if not supplied a default value of "HH:mm" will be used
 *
 * @return [String] representation of hours and minutes based on provided pattern
 */
fun getHourMinute(dateTime: LocalDateTime, pattern: String = "HH:mm"): String {
    return DateTimeFormatter.ofPattern(pattern).format(dateTime)
}

/**
 * A function that returns the name of the relevant day of the week contained in a [LocalDate] object
 *
 * @param [localDate] a [LocalDate] object from which you want to extract the day
 * @param [context] to provide resource access
 *
 * @return [String] A string containing the name of the day of the week, "Today" or "Tomorrow"
 * depending on the date
 */
fun getDayOfWeek(localDate: LocalDate, context: Context): String {
    return when (localDate) {
        LocalDate.now() -> {
            context.resources.getString(R.string.today)
        }
        LocalDate.now().plusDays(1) -> {
            context.resources.getString(R.string.tomorrow)
        }
        else -> {
            localDate.format(DateTimeFormatter.ofPattern(DateFormat.WEEKDAY)).replaceFirstChar {
                it.uppercase()
            }
        }
    }
}
