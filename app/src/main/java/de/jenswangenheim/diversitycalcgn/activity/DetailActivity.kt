package de.jenswangenheim.diversitycalcgn.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.jenswangenheim.diversitycalcgn.model.Holiday.Companion.DATE_FORMAT_PATTERN_LONG
import kotlinx.android.synthetic.main.activity_detail.*
import android.os.Build
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import de.jenswangenheim.diversitycalcgn.R
import de.jenswangenheim.diversitycalcgn.model.Holiday
import android.content.Intent
import android.provider.CalendarContract.Events
import android.provider.CalendarContract


class DetailActivity : AppCompatActivity() {

    companion object {
        const val HOLIDAY = "DetailActivity:holiday"
        const val TRANSITION_NAME = "DetailActivity:transitionName"
        const val HOUR_IN_MILLIS = 3600000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(R.layout.activity_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionName = intent.getSerializableExtra(TRANSITION_NAME) as String
            tvNameDetail.transitionName = transitionName
        }

        bindHoliday(intent.getSerializableExtra(HOLIDAY) as Holiday)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_calendar -> {
                addToCalendar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToCalendar() {
        val holiday = intent.getSerializableExtra(HOLIDAY) as Holiday

        val intent = Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, holiday.from.millis + HOUR_IN_MILLIS)
                .putExtra(Events.TITLE, holiday.name)
                .putExtra(Events.DESCRIPTION, holiday.description)
                .putExtra(Events.ALL_DAY, true)

        if (holiday.to != null) {
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, holiday.to.millis)
        }
        startActivity(intent)
    }

    private fun bindHoliday(holiday: Holiday) {
        tvNameDetail.text = holiday.name
        tvNameType.text = holiday.type
        tvFromTo.text = formatDateString(holiday)
        tvDescription.text = holiday.description
    }

    private fun formatDateString(holiday: Holiday): String {
        holiday.to?.let { return getString(R.string.from_to_date_format).format(
                Holiday.dateAsString(DATE_FORMAT_PATTERN_LONG, holiday.from),
                Holiday.dateAsString(DATE_FORMAT_PATTERN_LONG, holiday.to)
        ) }
        return Holiday.dateAsString(DATE_FORMAT_PATTERN_LONG, holiday.from)
    }
}
