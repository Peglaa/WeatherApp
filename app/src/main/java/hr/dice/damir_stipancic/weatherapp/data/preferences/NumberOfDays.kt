package hr.dice.damir_stipancic.weatherapp.data.preferences

/**
 * Class representing the number of available days to display for daily weather(3 to 7) as enums
 * each containing the exact [Int] value
 *
 * @param [value] an [Int] value representing the actual number of days
 */
enum class NumberOfDays(val value: Int) {
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7)
}
