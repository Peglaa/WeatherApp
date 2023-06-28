package hr.dice.damir_stipancic.weatherapp.ui.daily_details_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.DailyDetails
import hr.dice.damir_stipancic.weatherapp.data.preferences.UserPreferences
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.Daily
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.DailyWeather
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository
import hr.dice.damir_stipancic.weatherapp.repository.WeatherRepository
import hr.dice.damir_stipancic.weatherapp.util.getHourMinute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyDetailsWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val _locationCity: MutableStateFlow<String?> = MutableStateFlow(null)
    val locationCity = _locationCity.asStateFlow()

    private val _weatherResponseState =
        MutableStateFlow<Resource<DailyWeather>>(Resource.Loading())
    val weatherResponseState = _weatherResponseState.asStateFlow()

    private val _dataForUi = MutableStateFlow<List<DailyDetails>>(emptyList())
    val dataForUi = _dataForUi.asStateFlow()

    val preferencesFlow = settingsRepository.getPreferences()

    init {
        getDailyWeather()
    }

    private fun getDailyWeather() {
        preferencesFlow.combine(locationRepository.getActiveLocation()) { preferences, location ->
            _locationCity.value = location?.city
            weatherRepository.getDailyWeather(
                latitude = location?.latitude.toString(),
                longitude = location?.longitude.toString(),
                units = preferences.units.value,
                language = preferences.language.value
            ).let {
                when (it) {
                    is Resource.Success -> {
                        _weatherResponseState.value = it
                    }
                    is Resource.Error -> {
                        _weatherResponseState.value = it
                    }
                    else -> {
                        _weatherResponseState.value = it
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun prepareDataForUi(
        day: Daily,
        preferences: UserPreferences
    ) {

        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val tempIdentifier = preferences.units.tempIdentifier
                val speedIdentifier = preferences.units.speedIdentifier
                _dataForUi.value = listOf(
                    DailyDetails.DailyDetailsCard(
                        date = day.date,
                        temp = "${day.temp.day.toInt()}$tempIdentifier",
                        weatherDescription = day.weather[0].description,
                        iconUrl = day.weather[0].icon
                    ),
                    DailyDetails.DailyDetailsHeader(R.string.temperature),

                    DailyDetails.DailyDetailsContent(
                        R.string.max_daily, "${day.temp.max.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.min_daily, "${day.temp.min.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.night_temp, "${day.temp.night.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.evening_temp, "${day.temp.eve.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.morning_temp, "${day.temp.morn.toInt()}$tempIdentifier"
                    ),

                    DailyDetails.DailyDetailsHeader(R.string.feels_like_temp),

                    DailyDetails.DailyDetailsContent(
                        R.string.day_temp, "${day.feelsLike.day.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.night_temp, "${day.feelsLike.night.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.evening_temp, "${day.feelsLike.eve.toInt()}$tempIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(
                        R.string.morning_temp, "${day.temp.morn.toInt()}$tempIdentifier"
                    ),

                    DailyDetails.DailyDetailsHeader(R.string.sun),

                    DailyDetails.DailyDetailsContent(R.string.sunrise, getHourMinute(day.sunrise)),
                    DailyDetails.DailyDetailsContent(R.string.sundown, getHourMinute(day.sunset)),

                    DailyDetails.DailyDetailsHeader(R.string.wind),

                    DailyDetails.DailyDetailsContent(
                        R.string.speed, "${day.windSpeed}$speedIdentifier"
                    ),
                    DailyDetails.DailyDetailsContent(R.string.direction, "${day.windDeg}")
                )
            }
        }
    }

    fun resetWeatherSate() {
        _weatherResponseState.value = Resource.Loading()
    }
}
