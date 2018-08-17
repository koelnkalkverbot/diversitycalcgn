package de.jenswangenheim.diversitycalcgn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    companion object {
        val CAL_DATA_DOWNLOAD_URL = "https://offenedaten-koeln.de/sites/default/files/Feiertage_Angepasst.csv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            Request(CAL_DATA_DOWNLOAD_URL).run()
        }
    }
}
