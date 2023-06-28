package hr.dice.damir_stipancic.weatherapp.di

import hr.dice.damir_stipancic.weatherapp.data.exception.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val errorModule = module {
    single { ErrorHandler(androidContext()) }
}
