package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class EditHobby : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_hobby)

        val ButtonSetSchedule = findViewById<Button>(R.id.ButtonSetSched)
        ButtonSetSchedule.setOnClickListener{
            val intent = Intent(this, SetUpSchedule::class.java)
            startActivity(intent)
        }

        val hobbyName = intent.getStringExtra("hobbyName")
        val hobbits = intent.getStringArrayListExtra("hobbits")


        val hobbyNameTextView = findViewById<TextView>(R.id.ChangeableHobbyName)
        val hobbit1TextView = findViewById<TextView>(R.id.FirstHobbits)
        val hobbit2TextView = findViewById<TextView>(R.id.SecondHobbits)
        val hobbit3TextView = findViewById<TextView>(R.id.ThirdHobbits)

        hobbyNameTextView.text = hobbyName

        database = FirebaseDatabase.getInstance().reference.child("categories")

        if (hobbits != null) {
            if (hobbits.size >= 1) {
                hobbit1TextView.text = "1. " + hobbits[0]
                fetchBitsForHobbit(hobbyName.toString(), hobbits[0], getHobbitTextViews(1))
            }
            if (hobbits.size >= 2) {
                hobbit2TextView.text = "2. " + hobbits[1]
                fetchBitsForHobbit(hobbyName.toString(), hobbits[1], getHobbitTextViews(2))
            }
            if (hobbits.size >= 3) {
                hobbit3TextView.text = "3. " + hobbits[2]
                fetchBitsForHobbit(hobbyName.toString(), hobbits[2], getHobbitTextViews(3))
            }
        }
    }

    private fun getHobbitTextViews(hobbitIndex: Int): Array<TextView> {
        return when (hobbitIndex) {
            1 -> arrayOf(
                findViewById(R.id.hobbits1_1),
                findViewById(R.id.hobbits1_2),
                findViewById(R.id.hobbits1_3)
            )
            2 -> arrayOf(
                findViewById(R.id.hobbits2_1),
                findViewById(R.id.hobbits2_2),
                findViewById(R.id.hobbits2_3)
            )
            3 -> arrayOf(
                findViewById(R.id.hobbits3_1),
                findViewById(R.id.hobbits3_2),
                findViewById(R.id.hobbits3_3)
            )
            else -> emptyArray()
        }
    }

    private fun fetchBitsForHobbit(hobbyName: String, hobbitName: String, hobbitTextViews: Array<TextView>) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val categories = dataSnapshot.children
                for (categorySnapshot in categories) {
                    val hobbies = categorySnapshot.child("hobbies").children
                    for (hobbySnapshot in hobbies) {
                        val name = hobbySnapshot.child("name").value as String
                        if (name == hobbyName) {
                            val hobbits = hobbySnapshot.child("hobbits").children
                            for (hobbitSnapshot in hobbits) {
                                val hobbit = hobbitSnapshot.child("name").value as String
                                if (hobbit == hobbitName) {
                                    val bits = hobbitSnapshot.child("bits").value as List<String>?
                                    if (bits != null && bits.size >= hobbitTextViews.size) {
                                        for (i in 0 until hobbitTextViews.size) {
                                            val bitTextView = hobbitTextViews[i]
                                            bitTextView.text = "${i + 1}. ${bits[i]}"
                                        }
                                    } else {
                                        for (i in 0 until hobbitTextViews.size) {
                                            val bitTextView = hobbitTextViews[i]
                                            bitTextView.text = "No bits available for $hobbitName"
                                        }
                                    }
                                    break
                                }
                            }
                            break
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("EditHobby", "Failed to fetch bits: ${databaseError.message}")
            }
        })
    }
}
