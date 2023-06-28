package hr.dice.damir_stipancic.weatherapp.data.preferences

/**
 * Class representing all available app languages as enums that contain [String] values for each
 *
 * @param [value] a [String] object representing the shorthand name of the language(e.g. "en", "de").
 */
enum class Language(val value: String) {
    ENGLISH("en"),
    GERMAN("de"),
    CROATIAN("hr")
}
