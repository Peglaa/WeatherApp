package hr.dice.damir_stipancic.weatherapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.preferences.Language
import hr.dice.damir_stipancic.weatherapp.data.preferences.NumberOfDays
import hr.dice.damir_stipancic.weatherapp.data.preferences.Theme
import hr.dice.damir_stipancic.weatherapp.data.preferences.Units
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeButtonGroup.addOnButtonCheckedListener { _, checkedButton, isChecked ->
            if (isChecked)
                when (checkedButton) {
                    R.id.button_theme_default -> viewModel.updateTheme(Theme.DEFAULT)
                    R.id.button_theme_light -> viewModel.updateTheme(Theme.LIGHT)
                    R.id.button_theme_dark -> viewModel.updateTheme(Theme.DARK)
                }
        }

        binding.languageButtonGroup.addOnButtonCheckedListener { _, checkedButton, isChecked ->
            if (isChecked) {
                when (checkedButton) {
                    R.id.button_language_croatian -> viewModel.updateLanguage(Language.CROATIAN)
                    R.id.button_language_english -> viewModel.updateLanguage(Language.ENGLISH)
                    R.id.button_language_german -> viewModel.updateLanguage(Language.GERMAN)
                }
            }
        }

        binding.unitsButtonGroup.addOnButtonCheckedListener { _, checkedButton, isChecked ->
            if (isChecked)
                when (checkedButton) {
                    R.id.button_units_standard -> viewModel.updateUnits(Units.STANDARD)
                    R.id.button_units_imperial -> viewModel.updateUnits(Units.IMPERIAL)
                    R.id.button_units_metric -> viewModel.updateUnits(Units.METRIC)
                }
        }

        binding.daysButtonGroup.addOnButtonCheckedListener { _, checkedButton, isChecked ->
            if (isChecked)
                when (checkedButton) {
                    R.id.button_days_3 -> viewModel.updateDays(NumberOfDays.THREE)
                    R.id.button_days_4 -> viewModel.updateDays(NumberOfDays.FOUR)
                    R.id.button_days_5 -> viewModel.updateDays(NumberOfDays.FIVE)
                    R.id.button_days_6 -> viewModel.updateDays(NumberOfDays.SIX)
                    R.id.button_days_7 -> viewModel.updateDays(NumberOfDays.SEVEN)
                }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    val preferences = viewModel.userPreferencesFlow.first()
                    binding.themeButtonGroup.check(
                        when (preferences.theme) {
                            Theme.DEFAULT -> R.id.button_theme_default
                            Theme.LIGHT -> R.id.button_theme_light
                            Theme.DARK -> R.id.button_theme_dark
                        }
                    )

                    binding.languageButtonGroup.check(
                        when (preferences.language) {
                            Language.CROATIAN -> R.id.button_language_croatian
                            Language.ENGLISH -> R.id.button_language_english
                            Language.GERMAN -> R.id.button_language_german
                        }
                    )

                    binding.unitsButtonGroup.check(
                        when (preferences.units) {
                            Units.STANDARD -> R.id.button_units_standard
                            Units.METRIC -> R.id.button_units_metric
                            Units.IMPERIAL -> R.id.button_units_imperial
                        }
                    )

                    binding.daysButtonGroup.check(
                        when (preferences.numOfDays) {
                            NumberOfDays.THREE -> R.id.button_days_3
                            NumberOfDays.FOUR -> R.id.button_days_4
                            NumberOfDays.FIVE -> R.id.button_days_5
                            NumberOfDays.SIX -> R.id.button_days_6
                            NumberOfDays.SEVEN -> R.id.button_days_7
                        }
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
