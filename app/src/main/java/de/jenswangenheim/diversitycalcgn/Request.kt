package de.jenswangenheim.diversitycalcgn

import android.util.Log
import java.net.URL

class Request(private val url: String) {

    fun run() {
        val dataAsCsv = URL(url).readText()
        Log.d(Request::class.java.simpleName, dataAsCsv.take(500))
    }
}