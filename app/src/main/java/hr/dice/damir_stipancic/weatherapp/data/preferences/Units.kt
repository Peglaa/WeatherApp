package hr.dice.damir_stipancic.weatherapp.data.preferences

/**
 * Class representing all available SI units of measurement as enums. Every enum contains 3 values
 * that represent the name of units, corresponding speed and temperature identifiers.
 *
 * @param [value] a [String] value representing the name of the SI system
 * @param [tempIdentifier] a [String] value representing the temperature identifier for corresponding
 * SI system
 * @param [speedIdentifier] a [String] value representing the speed identifier for corresponding SI
 * system
 */
enum class Units(
    val value: String,
    val tempIdentifier: String,
    val speedIdentifier: String
) {
    STANDARD(
        value = "standard",
        tempIdentifier = "K",
        speedIdentifier = " km/h"
    ),
    METRIC(
        value = "metric",
        tempIdentifier = "°",
        speedIdentifier = " km/h"
    ),
    IMPERIAL(
        value = "imperial",
        tempIdentifier = "°",
        speedIdentifier = " mph"
    )
}
