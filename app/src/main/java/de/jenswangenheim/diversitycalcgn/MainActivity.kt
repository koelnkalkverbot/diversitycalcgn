package de.jenswangenheim.diversitycalcgn

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.joda.time.DateTime
import android.support.v4.view.ViewCompat
import android.support.v4.app.ActivityOptionsCompat




class MainActivity : AppCompatActivity(), ViewHolder.OnHolidayItemClickedListener {

    companion object {
        const val CAL_DATA_DOWNLOAD_URL = "http://jenswangenheim.de/diversity_calendar.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestDataAndBindViews()
    }

    private fun requestDataAndBindViews() {
        rvDates.layoutManager = LinearLayoutManager(this)

        doAsync {
            val items = Request(CAL_DATA_DOWNLOAD_URL).run()
            items.sortBy { it.from }
            uiThread {
                val datesList = items.map { it.from }
                rvDates.adapter = HolidayListAdapter(items, this@MainActivity)
                rvDates.scrollToPosition(indexOfClosestDate(datesList) - 1)
            }
        }
    }

    override fun onHolidayItemClicked(position: Int, holiday: Holiday, textView: TextView) {
        openDetailActivity(holiday, textView)
    }

    private fun indexOfClosestDate(dates: List<DateTime>): Int {
        val date = java.util.TreeSet<DateTime>(dates).lower(DateTime.now())
        return dates.indexOf(date)
    }

    private fun openDetailActivity(holiday: Holiday, textView: TextView) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.HOLIDAY, holiday)
        intent.putExtra(DetailActivity.TRANSITION_NAME, ViewCompat.getTransitionName(textView))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    textView,
                    ViewCompat.getTransitionName(textView))

            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }
}
