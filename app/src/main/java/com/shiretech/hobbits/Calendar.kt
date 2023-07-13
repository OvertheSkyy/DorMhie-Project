package com.shiretech.hobbits

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Calendar
import java.text.DateFormatSymbols

class Calendar : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var calendarAdapter: CalendarAdapter
    private val dates: MutableList<String> = mutableListOf()
    private lateinit var monthyearTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_page)

        val UnselectedHomeImageClick = findViewById<ImageView>(R.id.ClickHome)
        UnselectedHomeImageClick.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val UnselectedCategoriesImageClick = findViewById<ImageView>(R.id.ClickUnselectedCategories)
        UnselectedCategoriesImageClick.setOnClickListener {
            val intent = Intent(this, Categories::class.java)
            startActivity(intent)
        }
        val UnselectedProgressImageClick = findViewById<ImageView>(R.id.CLickUnselectedProgress)
        UnselectedProgressImageClick.setOnClickListener {
            val intent = Intent(this, Progress::class.java)
            startActivity(intent)
        }
        val UnselectedUserImageClick = findViewById<ImageView>(R.id.ClickUnselectedUser)
        UnselectedUserImageClick.setOnClickListener {
            val intent = Intent(this, User_Profile::class.java)
            startActivity(intent)
        }

        gridView = findViewById(R.id.calendarGridView)
        monthyearTextView = findViewById(R.id.MonthandYear)

        calendarAdapter = CalendarAdapter(this, dates)
        gridView.adapter = calendarAdapter

        populateDates()
        setCurrentMonthandYear()
    }
    private fun populateDates() {
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (dayOfMonth in 1..daysInMonth) {
            dates.add(dayOfMonth.toString())
        }

        calendarAdapter.notifyDataSetChanged()
    }
    private fun setCurrentMonthandYear(){
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val monthName = DateFormatSymbols().months[month]
        val monthYearText = "$monthName, $year"
        monthyearTextView.text = monthYearText
    }
}
