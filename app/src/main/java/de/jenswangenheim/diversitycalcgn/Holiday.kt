package de.jenswangenheim.diversitycalcgn

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Holiday(@SerializedName("Feiertage") val name: String,
                   @SerializedName("Beschreibung")val description: String,
                   @SerializedName("Art")val type: String,
                   @SerializedName("von")val from: Date,
                   @SerializedName("bis")val to: Date) : Comparable<Holiday> {

    override fun compareTo(other: Holiday) = when {
        this.from < other.from -> -1
        this.from > other.from -> 1
        else -> 0
    }

    companion object {
        val DATE_FORMAT_PATTERN_LONG: String = "dd.MM.yyyy"
        val DATE_FORMAT_PATTERN_SHORT: String = "dd. MMM"
        val SINGLE_DAY_MARKER_DATE: String = "01.01.1970"

        fun dateAsString(pattern: String, date: Date): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
        }

        fun stringAsDate(pattern: String, date: String): Date {
            return SimpleDateFormat(pattern, Locale.getDefault()).parse(date)
        }
    }
}
