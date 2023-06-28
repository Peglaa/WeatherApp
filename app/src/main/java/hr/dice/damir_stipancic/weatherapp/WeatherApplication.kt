package hr.dice.damir_stipancic.weatherapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import hr.dice.damir_stipancic.weatherapp.di.databaseModule
import hr.dice.damir_stipancic.weatherapp.di.errorModule
import hr.dice.damir_stipancic.weatherapp.di.locationModule
import hr.dice.damir_stipancic.weatherapp.di.networkModule
import hr.dice.damir_stipancic.weatherapp.di.preferencesModule
import hr.dice.damir_stipancic.weatherapp.di.repositoryModule
import hr.dice.damir_stipancic.weatherapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                databaseModule,
                locationModule,
                repositoryModule,
                viewModelModule,
                networkModule,
                errorModule,
                preferencesModule
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .logger(DebugLogger())
            .build()
    }
}
