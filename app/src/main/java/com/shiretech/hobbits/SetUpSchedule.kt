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


class SetUpSchedule : AppCompatActivity() {

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
        loadSavedTime()


        // Assuming you have a Save button with the ID 'saveButton'
        val saveButton = findViewById<Button>(R.id.SetUpSchedSaveButton)
        saveButton.setOnClickListener {
            saveSelectedTime()
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

}