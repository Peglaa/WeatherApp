package hr.dice.damir_stipancic.weatherapp.data.location

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationEntry?
}
