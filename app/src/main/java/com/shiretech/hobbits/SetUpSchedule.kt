package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Spinner

class SetUpSchedule : AppCompatActivity() {

    private lateinit var hourPicker: NumberPicker
    private lateinit var minutePicker: NumberPicker
    private lateinit var AMPMSpinner: Spinner

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

        val AMPMOptions = resources.getStringArray(R.array.am_pm_options)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, AMPMOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AMPMSpinner.adapter = spinnerAdapter

        val SetUpSchedSaveButton = findViewById<Button>(R.id.SetUpSchedSaveButton)
        SetUpSchedSaveButton.setOnClickListener {

            val intent = Intent(this, Progress_List::class.java)
            startActivity(intent)
        }
    }

}