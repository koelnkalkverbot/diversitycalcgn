package de.jenswangenheim.diversitycalcgn

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.holiday_list_item.view.*

class HolidayListAdapter(private val items: List<Holiday>, private val itemClick:
        ViewHolder.OnHolidayItemClickedListener) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holiday_list_item,
                parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewCompat.setTransitionName(holder.itemView.tvName, items[position].name)

        holder.bindHoliday(items[position])
        holder.itemView.setOnClickListener { itemClick.onHolidayItemClicked(position,
                items[position], holder.itemView.tvName) }
    }

    override fun getItemCount(): Int = items.size
}