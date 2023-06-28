package hr.dice.damir_stipancic.weatherapp.di

import hr.dice.damir_stipancic.weatherapp.repository.LocationRepository
import hr.dice.damir_stipancic.weatherapp.repository.SettingsRepository
import hr.dice.damir_stipancic.weatherapp.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        WeatherRepository(get())
    }

    single {
        LocationRepository(get())
    }

    single {
        SettingsRepository(get())
    }
}
