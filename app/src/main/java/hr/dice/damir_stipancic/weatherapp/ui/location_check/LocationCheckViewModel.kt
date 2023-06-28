package hr.dice.damir_stipancic.weatherapp.ui.location_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LocationCheckViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _hasSavedLocation = MutableStateFlow<Boolean?>(null)
    val hasSavedLocation = _hasSavedLocation.asStateFlow()

    init {
        checkSavedLocation()
    }

    private fun checkSavedLocation() {
        viewModelScope.launch {
            when (locationRepository.getActiveLocation().first()) {
                null -> _hasSavedLocation.value = false
                else -> _hasSavedLocation.value = true
            }
        }
    }
}
