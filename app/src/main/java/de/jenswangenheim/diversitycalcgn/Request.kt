package de.jenswangenheim.diversitycalcgn

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

class Request(private val url: String) {

    fun run(): MutableList<Holiday> {
        val holidayType = object : TypeToken<MutableList<Holiday>>() {}.type
        val holidaysJSON = URL(url).readText()
        return Gson().fromJson(holidaysJSON, holidayType)
    }
}