package hr.dice.damir_stipancic.weatherapp.di

import hr.dice.damir_stipancic.weatherapp.data.preferences.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single {
        DataStoreManager(androidContext())
    }
}
