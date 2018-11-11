package de.jenswangenheim.diversitycalcgn

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import de.jenswangenheim.diversitycalcgn.model.Holiday
import kotlinx.android.synthetic.main.holiday_list_item.view.*

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindHoliday(holiday: Holiday) {
        with (holiday) {
            itemView.tvName.text = name
            itemView.tvNameType.text = type
            itemView.tvDateFrom.text = Holiday.dateAsString(Holiday.DATE_FORMAT_PATTERN_SHORT, from)
            itemView.cardView.setCardBackgroundColor(Color.WHITE)
            itemView.divider.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
            if (DateUtils.isToday(from.toDate().time)) {
                itemView.cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
                itemView.divider.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            }
        }
    }

    interface OnHolidayItemClickedListener {
        fun onHolidayItemClicked(position: Int, holiday: Holiday, textView: TextView)
    }
}