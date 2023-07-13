package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.Calendar

class CalendarAdapter(private val context: Context, private val dates: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return dates.size
    }

    override fun getItem(position: Int): Any {
        return dates[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = View.inflate(context, R.layout.calendar_items, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val date = dates[position]
        viewHolder.dateTextView.text = date

        val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        if (date.toInt() == currentDate){
            viewHolder.dateTextView.setBackgroundResource(R.drawable.selecteddate)
        }
        else{
            viewHolder.dateTextView.setBackgroundResource(0)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }

}