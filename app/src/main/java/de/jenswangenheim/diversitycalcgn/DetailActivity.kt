package de.jenswangenheim.diversitycalcgn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.jenswangenheim.diversitycalcgn.Holiday.Companion.DATE_FORMAT_PATTERN_LONG
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val HOLIDAY = "DetailActivity:holiday"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindHoliday(intent.getSerializableExtra(HOLIDAY) as Holiday)

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
