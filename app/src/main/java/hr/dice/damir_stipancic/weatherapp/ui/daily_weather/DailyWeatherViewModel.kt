package hr.dice.damir_stipancic.weatherapp.ui.daily_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.data.preferences.Units
import hr.dice.damir_stipancic.weatherapp.data.preferences.UserPreferences
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.Daily
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.DailyWeather
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository
import hr.dice.damir_stipancic.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val _locationCity: MutableStateFlow<String?> = MutableStateFlow(null)
    val locationCity = _locationCity.asStateFlow()

    private val _weatherResponseState =
        MutableStateFlow<Resource<DailyWeather>>(Resource.Loading())
    val weatherResponseState = _weatherResponseState.asStateFlow()

    private val _dataForUi = MutableStateFlow<List<DailyWeatherItem>>(emptyList())
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
                        val data = it.copy(
                            data = it.data?.copy(
                                daily = it.data.daily.take(preferences.numOfDays.value)
                            )
                        )
                        _weatherResponseState.value = data
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
        days: List<Daily>?,
        preferences: UserPreferences
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                if (days != null) {
                    _dataForUi.value = days.map {
                        DailyWeatherItem(
                            date = it.date,
                            formattedDate = it.formattedDate,
                            dayTemp = "${it.temp.day.toInt()}${preferences.units.tempIdentifier}",
                            minTemp = "${it.temp.min.toInt()}${preferences.units.tempIdentifier}",
                            maxTemp = "${it.temp.max.toInt()}${preferences.units.tempIdentifier}",
                            humidity = "${it.humidity}%",
                            pressure = "${it.pressure}hPa",
                            windSpeed = when (preferences.units) {
                                Units.IMPERIAL ->
                                    "${it.windSpeed.toInt()}${preferences.units.speedIdentifier}"
                                else ->
                                    "${(it.windSpeed * 3.6).toInt()}${preferences.units.speedIdentifier}"
                            }
                        )
                    }
                }
            }
        }
    }

    fun resetWeatherStatus() {
        _weatherResponseState.value = Resource.Loading()
    }
}
