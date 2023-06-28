package hr.dice.damir_stipancic.weatherapp.di

import com.google.android.gms.location.LocationServices
import hr.dice.damir_stipancic.weatherapp.data.location.LocationTracker
import hr.dice.damir_stipancic.weatherapp.data.location.LocationTrackerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationModule = module {

    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<LocationTracker> {
        LocationTrackerImpl(get(), get())
    }
}
