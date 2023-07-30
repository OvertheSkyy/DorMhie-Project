package com.shiretech.hobbits

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Progress_List : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_list)

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        val hobbitNameTextViews: Array<TextView> = arrayOf(
            findViewById(R.id.FirstHobbitsProgress),
            findViewById(R.id.SecondHobbitsProgress),
            findViewById(R.id.ThirdHobbitsProgress)
        )

        val BackToHomeBtn = findViewById<ImageView>(R.id.Clickback)
        BackToHomeBtn.setOnClickListener {
            onBackPressed()
        }

        val hobbitBitTextViews = ArrayList<ArrayList<TextView>>()
        for (i in 0 until 3) {
            val bitTextViews = ArrayList<TextView>()
            for (j in 0 until 3) {
                val bitTextView = findViewById<TextView>(resources.getIdentifier("hobbitsprogress${i + 1}_${j + 1}", "id", packageName))
                bitTextViews.add(bitTextView)
            }
            hobbitBitTextViews.add(bitTextViews)
        }

        val changeableHobbyNameTextView = findViewById<TextView>(R.id.ChangeableHobbyName)

        database = FirebaseDatabase.getInstance().getReference("users").child(userId)
        val hobbiesRef = database.child("categories").child("hobbies")

        // Fetch the hobby data from the database
        hobbiesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var hobbitIndex = 0
                for (hobbySnapshot in dataSnapshot.children) {
                    val hobbitName = hobbySnapshot.child("hobbits").child("hobbit0").child("name").getValue(String::class.java)
                    if (hobbitName != null) {
                        hobbitNameTextViews[hobbitIndex].text = hobbitName

                        var bitIndex = 0
                        for (bitSnapshot in hobbySnapshot.child("hobbits").child("hobbit0").child("bits").children) {
                            val bitText = bitSnapshot.getValue(String::class.java)
                            if (bitText != null && bitIndex < 3) {
                                hobbitBitTextViews[hobbitIndex][bitIndex].text = bitText
                                bitIndex++
                            }
                        }

                        hobbitIndex++
                        if (hobbitIndex >= 3) {
                            break
                        }
                    }
                }

                // Fetch the hobby name from the "hobbies" node
                val hobbyName = dataSnapshot.child("savedhobby0").child("name").getValue(String::class.java)
                if (hobbyName != null) {
                    changeableHobbyNameTextView.text = hobbyName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Progress_List", "Failed to fetch hobby data: ${databaseError.message}")
            }
        })
    }
}