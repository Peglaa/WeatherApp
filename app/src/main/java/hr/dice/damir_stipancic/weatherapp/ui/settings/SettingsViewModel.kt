package hr.dice.damir_stipancic.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.damir_stipancic.weatherapp.data.preferences.Language
import hr.dice.damir_stipancic.weatherapp.data.preferences.NumberOfDays
import hr.dice.damir_stipancic.weatherapp.data.preferences.Theme
import hr.dice.damir_stipancic.weatherapp.data.preferences.Units
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val userPreferencesFlow = settingsRepository.getPreferences()

    fun updateUnits(units: Units) {
        viewModelScope.launch {
            settingsRepository.updateUnits(units)
        }
    }

    fun updateLanguage(language: Language) {
        viewModelScope.launch {
            settingsRepository.updateLanguage(language)
        }
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            settingsRepository.updateTheme(theme)
        }
    }

    fun updateDays(days: NumberOfDays) {
        viewModelScope.launch {
            settingsRepository.updateDays(days)
        }
    }
}
