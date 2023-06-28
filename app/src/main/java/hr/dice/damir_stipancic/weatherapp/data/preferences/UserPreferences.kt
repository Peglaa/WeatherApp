package hr.dice.damir_stipancic.weatherapp.data.preferences

/**
 * A class used to represent all of the user preferences for the app
 *
 * @param [units] a [Units] object representing the SI system
 * @param [language] a [Language] object representing app language
 * @param [theme] a [Theme] object representing app theme
 * @param [numOfDays] a [NumberOfDays] object representing the number of days to be shown for daily
 * weather
 */
data class UserPreferences(
    val units: Units,
    val language: Language,
    val theme: Theme,
    val numOfDays: NumberOfDays
)
