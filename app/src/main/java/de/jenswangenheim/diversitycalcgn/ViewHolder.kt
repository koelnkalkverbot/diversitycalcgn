package de.jenswangenheim.diversitycalcgn

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameView = view.find<TextView>(R.id.tv_name)
    val fromView = view.find<TextView>(R.id.tv_date)

    fun bindHoliday(holiday: Holiday) {
        with (holiday) {
            nameView.text = name
            fromView.text = Holiday.dateAsString(Holiday.DATE_FORMAT_PATTERN_SHORT, from)
        }
    }
}