package de.jenswangenheim.diversitycalcgn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.util.*



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
            items.add(Holiday("Testtag", "Lorem ipsum", "Typ", Date(System.currentTimeMillis()), Date(System.currentTimeMillis())))
            items.sortBy { it.from }
            uiThread {
                rvDates.adapter = HolidayListAdapter(items)
                val index = findClosestDateInHolidays(items)
                rvDates.scrollToPosition(index)
            }
        }
    }

    private fun findClosestDateInHolidays(holidays: List<Holiday>): Int {
        // TODO fix implementation
        return -(holidays.binarySearch(Holiday("Testtag", "Lorem ipsum", "Typ", Date(System.currentTimeMillis()), Date(System.currentTimeMillis()))))
    }
}
