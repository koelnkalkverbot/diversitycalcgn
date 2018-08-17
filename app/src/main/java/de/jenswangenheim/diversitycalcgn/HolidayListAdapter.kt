package de.jenswangenheim.diversitycalcgn

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

class HolidayListAdapter(val items: List<Holiday>) :
        RecyclerView.Adapter<HolidayListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].name
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}