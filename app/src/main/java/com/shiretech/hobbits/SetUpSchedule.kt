package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.AdapterView
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SetUpSchedule : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker
    private lateinit var AMPMSpinner: Spinner
    private val aMPMOptions: Array<String> by lazy {
        resources.getStringArray(R.array.am_pm_options) }
    private var selectedHour: Int = 0
    private var selectedMinute: Int = 0
    private var selectedAMPM: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_setup)

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        val changeableHobbyNameTextView = findViewById<TextView>(R.id.SetupSchedHobbyName)

        database = FirebaseDatabase.getInstance().getReference("users").child(userId)
        val hobbiesRef = database.child("categories").child("hobbies")

        hobbiesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbySnapshot = dataSnapshot.children.firstOrNull()

                // Check if the hobby exists and if it has a name field
                if (hobbySnapshot != null && hobbySnapshot.hasChild("name")) {
                    val hobbyName = hobbySnapshot.child("name").getValue(String::class.java)
                    changeableHobbyNameTextView.text = hobbyName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Progress_List", "Failed to fetch hobby data: ${databaseError.message}")
            }
        })

        hourPicker = findViewById(R.id.HourPicker)
        minutePicker = findViewById(R.id.MinutePicker)
        AMPMSpinner = findViewById(R.id.am_pm_spinner)

        hourPicker.minValue = 0
        hourPicker.maxValue = 12
        hourPicker.setFormatter { value -> String.format("%02d", value) }


        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.setFormatter { value -> String.format("%02d", value) }


        hourPicker.value = 12
        minutePicker.value = 0

        hourPicker.setOnValueChangedListener { _, _, newValue ->  }
        minutePicker.setOnValueChangedListener { _, _, newValue ->  }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, aMPMOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AMPMSpinner.adapter = spinnerAdapter


        hourPicker.setOnValueChangedListener { _, _, newValue ->
            selectedHour = newValue
        }

        minutePicker.setOnValueChangedListener { _, _, newValue ->
            selectedMinute = newValue
        }


        AMPMSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedAMPM = aMPMOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val CheckBoxMonday = findViewById<CheckBox>(R.id.CheckMonday)
        val CheckBoxTuesday = findViewById<CheckBox>(R.id.CheckTuesday)
        val CheckBoxWednesday = findViewById<CheckBox>(R.id.CheckWednesday)
        val CheckBoxThursday = findViewById<CheckBox>(R.id.CheckThursday)
        val CheckBoxFriday = findViewById<CheckBox>(R.id.CheckFriday)
        val CheckBoxSaturday = findViewById<CheckBox>(R.id.CheckSaturday)
        val CheckBoxSunday = findViewById<CheckBox>(R.id.CheckSunday)

        // Set up click listeners for the checkboxes
        CheckBoxMonday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxTuesday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxWednesday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxThursday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxFriday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxSaturday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }
        CheckBoxSunday.setOnCheckedChangeListener { _, _ -> saveSelectedDays() }

        // Load the previously selected days on activity creation
        loadSelectedDays()

        loadSavedTime()
        loadSelectedDays()


        // Assuming you have a Save button with the ID 'saveButton'
        val saveButton = findViewById<Button>(R.id.SetUpSchedSaveButton)
        saveButton.setOnClickListener {
            saveSelectedTime()
            saveSelectedDays()

            val intent = Intent(this, Home::class.java)

            val sharedPreferences = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)
            val selectedDays = sharedPreferences.getStringSet("selectedDays", emptySet())?.toTypedArray()
            intent.putExtra("selectedDays", selectedDays)

            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Set the default time to 12:00 AM every time the user opens the setup screen
        setDefaultTime()
    }

    private fun saveSelectedTime() {
        val sharedPreferences = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("hour", selectedHour)
        editor.putInt("minute", selectedMinute)
        editor.putString("am_pm", selectedAMPM)
        editor.apply()

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, Home::class.java)

        intent.putExtra("selectedHour", selectedHour)
        intent.putExtra("selectedMinute", selectedMinute)
        intent.putExtra("selectedAMPM", selectedAMPM)
        startActivity(intent)

    }

    private fun loadSavedTime() {
        val sharedPreferences = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)
        selectedHour = sharedPreferences.getInt("hour", 12)
        selectedMinute = sharedPreferences.getInt("minute", 0)
        selectedAMPM = sharedPreferences.getString("am_pm", "AM")

        // If there is no saved time, set a default time (e.g., 12:00 AM)
        if (selectedHour == -1 || selectedMinute == -1 || selectedAMPM.isNullOrEmpty()) {
            selectedHour = 12
            selectedMinute = 0
            selectedAMPM = "AM"
        }

        // Update the UI to reflect the loaded time
        hourPicker.value = selectedHour
        minutePicker.value = selectedMinute
        val amPmPosition = aMPMOptions.indexOf(selectedAMPM)
        AMPMSpinner.setSelection(amPmPosition)
    }

    private fun setDefaultTime() {
        // Set the default time to 12:00
        selectedHour = 12
        selectedMinute = 0
        selectedAMPM = "AM"

        // Update the UI to reflect the default time
        hourPicker.value = selectedHour
        minutePicker.value = selectedMinute
        val amPmPosition = aMPMOptions.indexOf(selectedAMPM)
        AMPMSpinner.setSelection(amPmPosition)
    }
    private fun loadSelectedDays(){
        val sharedPreferences = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)
        val SelectedDays = sharedPreferences.getStringSet("selectedDays", emptySet())

        val CheckBoxMonday = findViewById<CheckBox>(R.id.CheckMonday)
        val CheckBoxTuesday = findViewById<CheckBox>(R.id.CheckTuesday)
        val CheckBoxWednesday = findViewById<CheckBox>(R.id.CheckWednesday)
        val CheckBoxThursday = findViewById<CheckBox>(R.id.CheckThursday)
        val CheckBoxFriday = findViewById<CheckBox>(R.id.CheckFriday)
        val CheckBoxSaturday = findViewById<CheckBox>(R.id.CheckSaturday)
        val CheckBoxSunday = findViewById<CheckBox>(R.id.CheckSunday)

        //Updates Checkbox states
        CheckBoxMonday.isChecked = SelectedDays?.contains("Mon") == true
        CheckBoxTuesday.isChecked = SelectedDays?.contains("Tues") == true
        CheckBoxWednesday.isChecked = SelectedDays?.contains("Wed") == true
        CheckBoxThursday.isChecked = SelectedDays?.contains("Thur") == true
        CheckBoxFriday.isChecked = SelectedDays?.contains("Fri") == true
        CheckBoxSaturday.isChecked = SelectedDays?.contains("Sat") == true
        CheckBoxSunday.isChecked = SelectedDays?.contains("Sun") == true
    }
    private fun saveSelectedDays(){
        val sharedPreferences = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val selectedDays = mutableSetOf<String>()
        val CheckBoxMonday = findViewById<CheckBox>(R.id.CheckMonday)
        val CheckBoxTuesday = findViewById<CheckBox>(R.id.CheckTuesday)
        val CheckBoxWednesday = findViewById<CheckBox>(R.id.CheckWednesday)
        val CheckBoxThursday = findViewById<CheckBox>(R.id.CheckThursday)
        val CheckBoxFriday = findViewById<CheckBox>(R.id.CheckFriday)
        val CheckBoxSaturday = findViewById<CheckBox>(R.id.CheckSaturday)
        val CheckBoxSunday = findViewById<CheckBox>(R.id.CheckSunday)

        if (CheckBoxMonday.isChecked) selectedDays.add("Mon")
        if (CheckBoxTuesday.isChecked) selectedDays.add("Tues")
        if (CheckBoxWednesday.isChecked) selectedDays.add("Wed")
        if (CheckBoxThursday.isChecked) selectedDays.add("Thur")
        if (CheckBoxFriday.isChecked) selectedDays.add("Fri")
        if (CheckBoxSaturday.isChecked) selectedDays.add("Sat")
        if (CheckBoxSunday.isChecked) selectedDays.add("Sun")

        // Limit the selected days to 3
        if (selectedDays.size > 3) {
            // Display a message to the user
            Toast.makeText(this, "You can only select a maximum of 3 days.", Toast.LENGTH_SHORT).show()

            // Uncheck the last checked checkbox
            CheckBoxMonday.isChecked = false
            CheckBoxTuesday.isChecked = false
            CheckBoxWednesday.isChecked = false
            CheckBoxThursday.isChecked = false
            CheckBoxFriday.isChecked = false
            CheckBoxSaturday.isChecked = false
            CheckBoxSunday.isChecked = false

            // Remove the unchecked day from the selectedDays set
            selectedDays.remove("Mon")
            selectedDays.remove("Tues")
            selectedDays.remove("Wed")
            selectedDays.remove("Thur")
            selectedDays.remove("Fri")
            selectedDays.remove("Sat")
            selectedDays.remove("Sun")
        }

        editor.putStringSet("selectedDays", selectedDays)
        editor.apply()
    }
}