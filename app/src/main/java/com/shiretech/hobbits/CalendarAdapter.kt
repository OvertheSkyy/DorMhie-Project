package com.shiretech.hobbits

import androidx.core.content.ContextCompat
import android.content.Context
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
    private  var selectedDate: Date? = null
    private var onDateClickListener: ((Date) -> Unit)? = null
    private var onDateSelectedListener: OnDateSelectedListener? = null

    private var currentDate: Int = -1

    init {
        updateDates(currentMonth, currentYear)
    }
    interface OnDateSelectedListener {
        fun onDateSelected(selectedDate: Date)
    }
    fun updateCalendar(newMonth: Int, newYear: Int) {
        currentMonth = newMonth
        currentYear = newYear
        updateDates(newMonth, newYear)
        notifyDataSetChanged()
    }
    fun setSelectedDate(date: Date?){
        selectedDate = date
        notifyDataSetChanged()
    }
    fun setonDateClickListener(listener: (Date) -> Unit){
        onDateClickListener = listener
    }
    fun setOnDateSelectedListener(listener: OnDateSelectedListener) {
        onDateSelectedListener = listener
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

            val isCurrentMonthAndYear = (calendar.get(Calendar.MONTH) == currentMonth) && (calendar.get(Calendar.YEAR) == currentYear)
            val isCurrentMonthDay = isCurrentMonthAndYear && (calendar.get(Calendar.MONTH) == month)

            val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            dates.add("$dateString $dayOfMonth $dayOfWeek $isCurrentMonthDay")
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val currentDateInstance = Calendar.getInstance()
        val currentMonth = currentDateInstance.get(Calendar.MONTH)
        val currentYear = currentDateInstance.get(Calendar.YEAR)
        val currentDayOfMonth = currentDateInstance.get(Calendar.DAY_OF_MONTH)

        currentDate = if (currentMonth == month && currentYear == year) {
            currentDayOfMonth
        } else {
            -1 // If the displayed month is not the current month, set currentDate to an invalid value
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
            val isCurrentMonthDay = parts[3].toBoolean()

            val formattedDayOfMonth = String.format("%02d", dayOfMonth)

            viewHolder.dateTextView.text = formattedDayOfMonth
            viewHolder.dateTextView.setPadding(5, 0, 0, 0)

            val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

            if (isCurrentMonthDay) {
                // Set the color for days of the current month
                if (selectedDate != null && dayOfMonth == selectedDate!!.date && currentMonth == this.currentMonth) {
                    // Set the color and background for the selected date
                    viewHolder.dateTextView.setBackgroundResource(R.drawable.selecteddate)
                    viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.blueish))
                } else {
                    // Set the color for other days of the current month (no background)
                    viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.date))

                    if (dayOfMonth == currentDate && currentMonth == this.currentMonth) {
                        // Set the color for the current date
                        viewHolder.dateTextView.setBackgroundResource(R.drawable.currentdate)
                        viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                        // Change to your desired color
                    } else {
                        // Set the color for other days of the current month (no background)
                        viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.date))
                        viewHolder.dateTextView.setBackgroundResource(0)
                    }
                }

                // Remove the previous click listener
                viewHolder.dateTextView.setOnClickListener(null)

                viewHolder.dateTextView.setOnClickListener {
                    val dateString = dates[position]
                    val parts = dateString.split(" ")
                    val clickedDayOfMonth = parts[1].toInt()
                    val clickedMonth = currentMonth
                    val clickedYear = currentYear

                    // Create a Calendar instance and set it to the clicked date
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, clickedYear)
                    calendar.set(Calendar.MONTH, clickedMonth)
                    calendar.set(Calendar.DAY_OF_MONTH, clickedDayOfMonth)

                    // Set the selected date and notify the adapter about the change
                    selectedDate = calendar.time
                    notifyDataSetChanged()

                    // Call the click listener if set
                    onDateSelectedListener?.onDateSelected(selectedDate!!)
                }
            } else {
                // Set the color for days from the previous and next months
                viewHolder.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.faded))
                viewHolder.dateTextView.setBackgroundResource(0)
            }
            return view
        }

    private class ViewHolder(view: View) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }
}