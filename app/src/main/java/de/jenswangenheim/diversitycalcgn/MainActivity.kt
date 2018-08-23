package de.jenswangenheim.diversitycalcgn

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.view.Gravity
import android.view.Window
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
                val datesList = items.map { it.from }
                rvDates.adapter = HolidayListAdapter(items) {
                    openDetailActivity(it)
                }
                rvDates.scrollToPosition(indexOfClosestDate(datesList) - 1)
            }
        }
    }

    private fun indexOfClosestDate(dates: List<DateTime>): Int {
        val date = java.util.TreeSet<DateTime>(dates).lower(DateTime.now())
        return dates.indexOf(date)
    }

    private fun openDetailActivity(holiday: Holiday) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.HOLIDAY, holiday)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {

        }
    }
}
