package hr.dice.damir_stipancic.weatherapp.ui

import androidx.lifecycle.ViewModel
import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository

class MainViewModel(
    settingsRepository: SettingsRepository,
    locationRepository: LocationRepository
) : ViewModel() {

    val locationCity = locationRepository.getActiveLocation()

    val preferencesFlow = settingsRepository.getPreferences()
}
