package de.jenswangenheim.diversitycalcgn

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


data class Holiday(@SerializedName("Feiertage") val name: String,
                   @SerializedName("Beschreibung")val description: String,
                   @SerializedName("Art")val type: String,
                   @SerializedName("von")val from: DateTime,
                   @SerializedName("bis")val to: DateTime) : Comparable<Holiday> {

    override fun compareTo(other: Holiday) = when {
        this.from.isBefore(other.from) -> -1
        this.from.isAfter(other.from) -> 1
        else -> 0
    }

    companion object {
        val DATE_FORMAT_PATTERN_LONG = DateTimeFormat.forPattern("dd.MM.yyyy") as DateTimeFormatter
        val DATE_FORMAT_PATTERN_SHORT = DateTimeFormat.forPattern("dd. MMM") as DateTimeFormatter
        const val SINGLE_DAY_MARKER_DATE: String = "01.01.1970"

        fun dateAsString(pattern: DateTimeFormatter, date: DateTime): String {
            return date.toString(pattern)
        }

        fun stringAsDate(pattern: DateTimeFormatter, date: String): DateTime {
            return DateTime.parse(date, pattern)
        }
    }
}
