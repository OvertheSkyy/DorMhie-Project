package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EditHobby : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_hobby)

        val hobbyName = intent.getStringExtra("hobbyName")
        val hobbits = intent.getStringArrayListExtra("hobbits")

        val hobbyNameTextView = findViewById<TextView>(R.id.ChangeableHobbyName)
        val hobbit1TextView = findViewById<TextView>(R.id.FirstHobbits)
        val hobbit2TextView = findViewById<TextView>(R.id.SecondHobbits)
        val hobbit3TextView = findViewById<TextView>(R.id.ThirdHobbits)

        hobbyNameTextView.text = hobbyName

        if (hobbits != null) {
            if (hobbits.size >= 1) {
                hobbit1TextView.text = "1. " + hobbits[0]
            }
            if (hobbits.size >= 2) {
                hobbit2TextView.text = "2. " + hobbits[1]
            }
            if (hobbits.size >= 3) {
                hobbit3TextView.text = "3. " + hobbits[2]
            }
        }
    }
}