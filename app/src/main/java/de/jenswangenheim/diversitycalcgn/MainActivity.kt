package de.jenswangenheim.diversitycalcgn

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupTransitionAnimation()
        }
        setContentView(R.layout.activity_main)
        requestDataAndBinViews()
    }

    private fun requestDataAndBinViews() {
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
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.HOLIDAY, holiday)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupTransitionAnimation() {
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            exitTransition = Slide(Gravity.START)
        }
    }
}
