package de.jenswangenheim.diversitycalcgn

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class Request(private val url: String) {

    private val dateFormat = SimpleDateFormat(Holiday.DATE_FORMAT, Locale.getDefault())

    fun run(): MutableList<Holiday> {
        val holidayType = object : TypeToken<MutableList<Holiday>>() {}.type
        val holidaysJSON = URL(url).readText()
        return GsonBuilder().setDateFormat(Holiday.DATE_FORMAT).registerTypeAdapter(Date::class.java, HolidayDateSerializer()).create().fromJson(holidaysJSON, holidayType)
    }

    inner class HolidayDateSerializer : JsonDeserializer<Date> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
            if (json!!.asString.isEmpty()) {
                return dateFormat.parse(Holiday.SINGLE_DAY_MARKER_DATE)
            }
            return dateFormat.parse(json!!.asString)
        }
    }
}