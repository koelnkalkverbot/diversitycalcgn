package de.jenswangenheim.diversitycalcgn

import com.google.gson.annotations.SerializedName
import java.util.*

data class Holiday(@SerializedName("Feiertage") val name: String,
                   @SerializedName("Beschreibung")val description: String,
                   @SerializedName("Art")val type: String,
                   @SerializedName("von")val from: Date,
                   @SerializedName("bis")val to: Date)