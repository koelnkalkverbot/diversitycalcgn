package de.jenswangenheim.diversitycalcgn

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Holiday(@SerializedName("Feiertage") val name: String,
                   @SerializedName("Beschreibung")val description: String,
                   @SerializedName("Art")val type: String,
                   @SerializedName("von")val from: Date,
                   @SerializedName("bis")val to: Date) {
    companion object {
        val DATE_FORMAT: String = "dd.MM.yyyy"
        val SINGLE_DAY_MARKER_DATE: String = "01.01.1970"
    }
}
