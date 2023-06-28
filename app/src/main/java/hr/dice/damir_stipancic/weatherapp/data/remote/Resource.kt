package hr.dice.damir_stipancic.weatherapp.data.remote

/**
 * A sealed class representing different states of an API network response.
 *
 * Use [Success] to signify a successful API response that returns some data
 *
 * Use [Error] to signify a failed API response with a corresponding message and/or throwable Exception
 *
 * Use [Loading] to signify that you are awaiting an API response
 */
sealed class Resource<T> {
    /**
     * Data class representing a successful API response
     *
     * @param [data] generic type representing the response data
     */
    data class Success<T>(val data: T?) : Resource<T>()

    /**
     * Data class representing a failed API response
     *
     * @param [throwable] an exception you wish to throw
     * @param [code] error HTTP code
     */
    data class Error<T>(
        val throwable: Throwable? = null,
        val code: Int? = null,
        val message: String? = null
    ) : Resource<T>()

    /**
     * Class representing an in progress API network call
     */
    class Loading<T> : Resource<T>()
}
