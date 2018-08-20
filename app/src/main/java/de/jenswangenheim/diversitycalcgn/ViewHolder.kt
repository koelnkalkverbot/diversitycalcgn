package de.jenswangenheim.diversitycalcgn

import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameView = view.find<TextView>(R.id.tv_name)
    private val fromView = view.find<TextView>(R.id.tv_date)
    private val cardView = view.find<CardView>(R.id.card)

    fun bindHoliday(holiday: Holiday) {
        with (holiday) {
            nameView.text = name
            fromView.text = Holiday.dateAsString(Holiday.DATE_FORMAT_PATTERN_SHORT, from)
            if (DateUtils.isToday(from.toDate().time)) {
                cardView.setCardBackgroundColor(Color.RED)
            } else {
                cardView.setCardBackgroundColor(Color.WHITE)
            }
        }
    }
}