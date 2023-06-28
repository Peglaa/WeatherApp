package hr.dice.damir_stipancic.weatherapp.data.exception

import android.content.Context
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource

/**
 * A class responsible for handling various HTTP error codes and returning a corresponding string message
 *
 * @param [context] required to provide access to string templates
 * @return [String] error message for specific HTTP error code
 */
class ErrorHandler(
    private val context: Context
) {

    fun <T> displayErrorMessage(result: Resource.Error<T>): String {
        return when (result.code) {
            401 -> context.resources.getString(R.string.unauthorized)
            403 -> context.resources.getString(R.string.invalid_permission)
            404 -> context.resources.getString(R.string.server_url_error)
            else -> context.resources.getString(R.string.unknown_error) + "${result.message}"
        }
    }
}
