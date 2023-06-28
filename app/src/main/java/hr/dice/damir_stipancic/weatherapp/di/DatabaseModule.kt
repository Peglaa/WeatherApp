package hr.dice.damir_stipancic.weatherapp.di

import androidx.room.Room
import hr.dice.damir_stipancic.weatherapp.data.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather_data_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<WeatherDatabase>().getWeatherDao()
    }
}
