package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.*
import java.util.Locale

class CalendarAdapter(private val context: Context, private var currentMonth: Int, private var currentYear: Int) : BaseAdapter() {

    private val dates: MutableList<String> = mutableListOf()

    init {
        updateDates(currentMonth, currentYear)
    }

    fun updateCalendar(newMonth: Int, newYear: Int) {
        currentMonth = newMonth
        currentYear = newYear
        updateDates(newMonth, newYear)
        notifyDataSetChanged()
    }

    private fun updateDates(month: Int, year: Int) {
        dates.clear()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        val daysFromPreviousMonth = (calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY + 7) % 7
        calendar.add(Calendar.DAY_OF_MONTH, -daysFromPreviousMonth)

        val totalDays = 6 * 7 // 6 rows and 7 columns
        for (i in 0 until totalDays) {
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val isCurrentMonthAndYear = (calendar.get(Calendar.MONTH) == month) && (calendar.get(Calendar.YEAR) == year)
            val isCurrentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

            val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            dates.add("$dateString $dayOfMonth $dayOfWeek ${isCurrentMonthAndYear && isCurrentDayOfMonth}")
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

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

        val dateString = dates[position]
        val parts = dateString.split(" ")
        val dayOfMonth = parts[1].toInt()
        val dayOfWeek = parts[2].toInt()
        val isCurrentMonthAndYear = parts[3].toBoolean()

        viewHolder.dateTextView.text = dayOfMonth.toString()
        viewHolder.dateTextView.setPadding(5, 0, 0, 0)

        // Get the current date
        val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

        if (!isCurrentMonthAndYear) {
            // Set the color for days from the previous and next months
            viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.date))
        } else {
            // Set the color for days of the current month
            viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.faded))

            if (dayOfMonth == currentDate && currentMonth == this.currentMonth) {
                // Set the color and background for the current day of the current month
                viewHolder.dateTextView.setBackgroundResource(R.drawable.selecteddate)
                viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                viewHolder.dateTextView.setBackgroundResource(0)
            }
        }

        return view
    }

    private class ViewHolder(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }
}