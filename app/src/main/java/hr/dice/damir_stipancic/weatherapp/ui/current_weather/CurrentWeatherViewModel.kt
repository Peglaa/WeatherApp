package hr.dice.damir_stipancic.weatherapp.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.data.remote.weather_models.CurrentWeather
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository
import hr.dice.damir_stipancic.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn

class CurrentWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val _weatherResponseState =
        MutableStateFlow<Resource<CurrentWeather>>(Resource.Loading())
    val weatherResponseState = _weatherResponseState.asStateFlow()

    val preferencesFlow = settingsRepository.getPreferences()

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        preferencesFlow.combine(locationRepository.getActiveLocation()) { preferences, location ->
            weatherRepository.getCurrentWeather(
                latitude = location?.latitude.toString(),
                longitude = location?.longitude.toString(),
                units = preferences.units.value,
                language = preferences.language.value
            ).let {
                when (it) {
                    is Resource.Success -> {
                        if (it.data != null) {
                            if (location?.hasCity() == false) {
                                val updatedLocation = location.copy(
                                    city = it.data.name
                                )
                                locationRepository.updateLocationCity(updatedLocation)
                            }
                            _weatherResponseState.value = it
                        }
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

    fun resetWeatherState() {
        _weatherResponseState.value = Resource.Loading()
    }
}
