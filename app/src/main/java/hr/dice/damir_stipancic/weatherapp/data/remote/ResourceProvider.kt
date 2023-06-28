package hr.dice.damir_stipancic.weatherapp.data.remote

import hr.dice.damir_stipancic.weatherapp.data.exception.NoInternetConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Function responsible for performing the network API call and handling possible response states
 * @param [api] Suspend function block for calling the wanted API call function
 * @return [Resource] representing the state of the network API response
 */
suspend fun <T> callApi(api: suspend () -> Response<T>): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            api().let { response ->
                if (response.isSuccessful)
                    Resource.Success(data = response.body())
                else
                    Resource.Error(code = response.code())
            }
        } catch (e: HttpException) {
            Resource.Error(code = e.code())
        } catch (e: IOException) {
            Resource.Error(throwable = NoInternetConnectionException())
        } catch (e: Exception) {
            println(e.localizedMessage)
            Resource.Error(message = e.localizedMessage)
        }
    }
}
