package hr.dice.damir_stipancic.weatherapp.ui.no_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.data.location.LocationTracker
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoLocationViewModel(
    private val locationRepository: LocationRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _navigateToNoLocation = MutableStateFlow(false)
    val navigateToNoLocation = _navigateToNoLocation.asStateFlow()

    private val _navigateToCurrentWeather = MutableStateFlow(false)
    val navigateToCurrentWeather = _navigateToCurrentWeather.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation().let {
                if (it != null) {
                    locationRepository.insertLocation(it)
                    _navigateToCurrentWeather.value = true
                } else
                    _navigateToNoLocation.value = true
            }
        }
    }
}
