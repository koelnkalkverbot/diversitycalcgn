package de.jenswangenheim.diversitycalcgn

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTime
import java.lang.reflect.Type
import java.net.URL

class Request(private val url: String) {

    fun run(): MutableList<Holiday> {
        val holidayType = object : TypeToken<MutableList<Holiday>>() {}.type
        val holidaysJSON = URL(url).readText()
        return GsonBuilder().registerTypeAdapter(DateTime::class.java, HolidayDateSerializer()).create().fromJson(holidaysJSON, holidayType)
    }

    inner class HolidayDateSerializer : JsonDeserializer<DateTime> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DateTime? {
            if (json!!.asString.isEmpty()) {
                return null
            }
            return Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, json.asString)
        }
    }
}