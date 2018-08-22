package de.jenswangenheim.diversitycalcgn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.joda.time.DateTime


class MainActivity : AppCompatActivity() {

    companion object {
        const val CAL_DATA_DOWNLOAD_URL = "http://jenswangenheim.de/diversity_calendar.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvDates.layoutManager = LinearLayoutManager(this)

        doAsync {
            val items = Request(CAL_DATA_DOWNLOAD_URL).run()
            items.sortBy { it.from }
            uiThread {
                rvDates.adapter = HolidayListAdapter(items)
                val datesList = items.map { it.from }
                rvDates.scrollToPosition(indexOfClosestDate(datesList) - 1)
            }
        }
    }

    private fun indexOfClosestDate(dates: List<DateTime>): Int {
        val date = java.util.TreeSet<DateTime>(dates).lower(DateTime.now())
        return dates.indexOf(date)
    }
}
