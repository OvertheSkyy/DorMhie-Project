package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.*

class Home : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var gridView: GridView
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var monthYearTextView: TextView
    private lateinit var displayTimeView: TextView
    private lateinit var dateAssignedTextView: TextView



    private var currentMonth: Int = 0
    private var currentYear: Int = 0

    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val textViewWelcome = findViewById<TextView>(R.id.TextViewWelcome)
        val textViewTime = findViewById<TextView>(R.id.TimeAssignedForHobby0)

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            val usersRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.child("name").getValue(String::class.java)

                    if (user != null) {
                        val welcomeMessage = "Hello, $user"
                        textViewWelcome.text = welcomeMessage
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            })
        }

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        val hobbyNameTextView = findViewById<TextView>(R.id.HobbyName0)

        database = FirebaseDatabase.getInstance().getReference("users").child(userId)
        val hobbiesRef = database.child("categories").child("hobbies").child("savedhobby0")

        // Fetch the hobby name from the database
        hobbiesRef.child("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbyName = dataSnapshot.getValue(String::class.java)
                if (hobbyName != null) {
                    hobbyNameTextView.text = hobbyName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Home", "Failed to fetch hobby name: ${databaseError.message}")
            }
        })

        val ButtonViewProgressButtonViewProgressHobby0 = findViewById<Button>(R.id.ButtonViewProgressButtonViewProgressHobby0)
        ButtonViewProgressButtonViewProgressHobby0.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("users").child(userId)
            val hobbiesRef = database.child("categories").child("hobbies").child("savedhobby0")

            hobbiesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Retrieve the hobby name from the database
                    val hobbyName = dataSnapshot.child("name").getValue(String::class.java)
                    if (hobbyName != null) {
                        // Retrieve the hobbits and bits data from the database
                        val hobbits = ArrayList<String>()
                        val hobbitBitsMap = HashMap<String, ArrayList<String>>()

                        for (hobbitSnapshot in dataSnapshot.child("hobbits").children) {
                            val hobbitName = hobbitSnapshot.child("name").getValue(String::class.java)
                            if (hobbitName != null) {
                                hobbits.add(hobbitName)

                                val bits = ArrayList<String>()
                                for (bitSnapshot in hobbitSnapshot.child("bits").children) {
                                    val bitText = bitSnapshot.getValue(String::class.java)
                                    if (bitText != null) {
                                        bits.add(bitText)
                                    }
                                }
                                hobbitBitsMap[hobbitName] = bits
                            }
                        }

                        // Start EditHobby activity with the intent and pass the hobbits and bits data
                        val intent = Intent(this@Home, EditHobby::class.java).apply {
                            putExtra("hobbyName", hobbyName)
                            putExtra("hobbits", hobbits)
                            putExtra("hobbitBitsMap", hobbitBitsMap)
                        }
                        startActivity(intent)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Progress_List", "Failed to fetch hobby data: ${databaseError.message}")
                }
            })
        }


        val UnselectedCategoriesImageClick = findViewById<ImageView>(R.id.ClickUnselectedCategories)
        UnselectedCategoriesImageClick.setOnClickListener {
            val intent = Intent(this, Categories::class.java)
            startActivity(intent)
        }
        val UnselectedUserImageClick = findViewById<ImageView>(R.id.ClickUnselectedUser)
        UnselectedUserImageClick.setOnClickListener {
            val intent = Intent(this, User_Profile::class.java)
            startActivity(intent)
        }

        gridView = findViewById(R.id.calendarGridView)
        monthYearTextView = findViewById(R.id.MonthandYear)

        val initialCalendar = Calendar.getInstance()
        currentMonth = initialCalendar.get(Calendar.MONTH)
        currentYear = initialCalendar.get(Calendar.YEAR)

        val currentDate = Calendar.getInstance().time

        calendarAdapter = CalendarAdapter(this, currentMonth, currentYear)
        gridView.adapter = calendarAdapter

        updateMonthAndYearTextView()


        val dateNext: ImageView = findViewById(R.id.DateNext)
        val dateBack: ImageView = findViewById(R.id.DateBack)

        dateNext.setOnClickListener {
            val nextCalendar = getNextMonthCalendar()
            currentMonth = nextCalendar.get(Calendar.MONTH)
            currentYear = nextCalendar.get(Calendar.YEAR)
            calendarAdapter.updateCalendar(currentMonth, currentYear)
            updateMonthAndYearTextView()
        }
        dateBack.setOnClickListener {
            val previousCalendar = getPreviousMonthCalendar()
            currentMonth = previousCalendar.get(Calendar.MONTH)
            currentYear = previousCalendar.get(Calendar.YEAR)
            calendarAdapter.updateCalendar(currentMonth, currentYear)
            updateMonthAndYearTextView()
        }

        // Get the current month and year
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        // Create the adapter with the current month and year
        calendarAdapter = CalendarAdapter(this, currentMonth, currentYear)
        gridView.adapter = calendarAdapter


        //SCHEDULE


        displayTimeView = findViewById(R.id.TimeAssignedForHobby0)


        val sharedPreferences = getSharedPreferences("SchedulePrefs", MODE_PRIVATE)
        val savedHour = sharedPreferences.getInt("hour", 12)
        val savedMinute = sharedPreferences.getInt("minute", 0)
        val savedAMPM = sharedPreferences.getString("am_pm", "AM")

        val displayText = " ${String.format("%02d", savedHour)}:${String.format("%02d", savedMinute)} $savedAMPM"
        displayTimeView.text = displayText


        dateAssignedTextView = findViewById(R.id.DateAssignedForHobby0)

        // Retrieve the selected days from the intent
        val selectedDays = intent.getStringArrayExtra("selectedDays")

        // Update the TextView with the selected days
        if (selectedDays != null && selectedDays.isNotEmpty()) {
            val daysString = selectedDays.joinToString(" ")
            dateAssignedTextView.text = daysString
        }

    }

    private fun updateMonthAndYearTextView() {
        val monthName = DateFormatSymbols().months[currentMonth]
        val monthYearText = "$monthName, $currentYear"
        monthYearTextView.text = monthYearText
    }

    private fun getNextMonthCalendar(): Calendar {
        val nextCalendar = Calendar.getInstance()
        nextCalendar.set(Calendar.MONTH, currentMonth)
        nextCalendar.set(Calendar.YEAR, currentYear)
        nextCalendar.add(Calendar.MONTH, 1)
        return nextCalendar
    }

    private fun getPreviousMonthCalendar(): Calendar {
        val previousCalendar = Calendar.getInstance()
        previousCalendar.set(Calendar.MONTH, currentMonth)
        previousCalendar.set(Calendar.YEAR, currentYear)
        previousCalendar.add(Calendar.MONTH, -1)
        return previousCalendar
    }
}

