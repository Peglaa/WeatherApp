package hr.dice.damir_stipancic.weatherapp.data.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TN_LOCATION = "location"
const val COL_LOCATION_ID = "id"
const val COL_LONGITUDE = "longitude"
const val COL_LATITUDE = "latitude"
const val COL_CITY = "city"
const val COL_IS_ACTIVE = "is_active"

@Entity(tableName = TN_LOCATION)
data class LocationEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_LOCATION_ID)
    val id: Int = 0,

    @ColumnInfo(name = COL_LONGITUDE)
    val longitude: Double,

    @ColumnInfo(name = COL_LATITUDE)
    val latitude: Double,

    @ColumnInfo(name = COL_CITY)
    val city: String = "",

    @ColumnInfo(name = COL_IS_ACTIVE)
    val isActive: Boolean = false
) {
    fun hasCity(): Boolean {
        return city != ""
    }
}
