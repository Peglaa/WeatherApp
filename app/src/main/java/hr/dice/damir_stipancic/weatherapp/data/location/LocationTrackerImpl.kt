package hr.dice.damir_stipancic.weatherapp.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationTrackerImpl(
    private val locationClient: FusedLocationProviderClient,
    private val application: Context
) : LocationTracker {
    override suspend fun getCurrentLocation(): LocationEntry? =
        suspendCancellableCoroutine { cont ->
            val hasAccessCoarseLocation = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val locationManager =
                application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!hasAccessCoarseLocation || !isGpsEnabled) {
                cont.resume(null)
                return@suspendCancellableCoroutine
            }

            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).apply {
                addOnSuccessListener {
                    if (it != null)
                        cont.resume(
                            LocationEntry(
                                longitude = it.longitude,
                                latitude = it.latitude,
                                isActive = true
                            )
                        )
                    else
                        cont.resume(null)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
}
