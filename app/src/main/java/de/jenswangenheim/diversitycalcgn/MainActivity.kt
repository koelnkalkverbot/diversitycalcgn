package de.jenswangenheim.diversitycalcgn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    companion object {
        val CAL_DATA_DOWNLOAD_URL = "http://jenswangenheim.de/diversity_calendar.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvDates: RecyclerView = find(R.id.rv_dates)
        rvDates.layoutManager = LinearLayoutManager(this)

        doAsync {
            val items = Request(CAL_DATA_DOWNLOAD_URL).run()
            runOnUiThread {
                rvDates.adapter = HolidayListAdapter(items)
            }
        }
    }
}
