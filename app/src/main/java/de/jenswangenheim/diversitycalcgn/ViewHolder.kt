package de.jenswangenheim.diversitycalcgn

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import kotlinx.android.synthetic.main.holiday_list_item.view.*

class ViewHolder(view: View, private val itemClick: (Holiday) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bindHoliday(holiday: Holiday) {
        with (holiday) {
            itemView.tvName.text = name
            itemView.tvDateFrom.text = Holiday.dateAsString(Holiday.DATE_FORMAT_PATTERN_SHORT, from)
            if (DateUtils.isToday(from.toDate().time)) {
                itemView.cardView.setCardBackgroundColor(Color.RED)
            } else {
                itemView.cardView.setCardBackgroundColor(Color.WHITE)
            }
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}