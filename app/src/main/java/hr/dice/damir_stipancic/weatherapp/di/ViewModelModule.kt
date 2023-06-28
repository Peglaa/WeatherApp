package hr.dice.damir_stipancic.weatherapp.di

import hr.dice.damir_stipancic.weatherapp.ui.MainViewModel
import hr.dice.damir_stipancic.weatherapp.ui.current_weather.CurrentWeatherViewModel
import hr.dice.damir_stipancic.weatherapp.ui.daily_details_weather.DailyDetailsWeatherViewModel
import hr.dice.damir_stipancic.weatherapp.ui.daily_weather.DailyWeatherViewModel
import hr.dice.damir_stipancic.weatherapp.ui.location_check.LocationCheckViewModel
import hr.dice.damir_stipancic.weatherapp.ui.no_location.NoLocationViewModel
import hr.dice.damir_stipancic.weatherapp.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        NoLocationViewModel(get(), get())
    }

    viewModel {
        CurrentWeatherViewModel(get(), get(), get())
    }

    viewModel {
        DailyWeatherViewModel(get(), get(), get())
    }

    viewModel {
        DailyDetailsWeatherViewModel(get(), get(), get())
    }

    viewModel {
        LocationCheckViewModel(get())
    }

    viewModel {
        SettingsViewModel(get())
    }

    viewModel {
        MainViewModel(get(), get())
    }
}
