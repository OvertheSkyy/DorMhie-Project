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

    private val imageViewsMap = hashMapOf<Int, Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_list)

        val imageViews = arrayOf(
            findViewById<ImageView>(R.id.xprogress1_1),
            findViewById<ImageView>(R.id.xprogress1_2),
            findViewById<ImageView>(R.id.xprogress1_3),
            findViewById<ImageView>(R.id.xprogress2_1),
            findViewById<ImageView>(R.id.xprogress2_2),
            findViewById<ImageView>(R.id.xprogress2_3),
            findViewById<ImageView>(R.id.xprogress3_1),
            findViewById<ImageView>(R.id.xprogress3_2),
            findViewById<ImageView>(R.id.xprogress3_3)
        )

        for (imageView in imageViews) {
            imageViewsMap[imageView.id] = false // Initialize all images as unchecked
            imageView.setOnClickListener {
                val isChecked = imageViewsMap[imageView.id] ?: false
                imageViewsMap[imageView.id] = !isChecked
                imageView.setImageResource(
                    if (isChecked) R.drawable.unchecked_progress
                    else R.drawable.progress_checked
                )
            }
        }

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid ?: ""

        val changeableHobbyNameTextView = findViewById<TextView>(R.id.ChangeableHobbyName)
        val hobbitNameTextView1 = findViewById<TextView>(R.id.FirstHobbitsProgress)
        val hobbitNameTextView2 = findViewById<TextView>(R.id.SecondHobbitsProgress)
        val hobbitNameTextView3 = findViewById<TextView>(R.id.ThirdHobbitsProgress)
        val hobbitBitTextViews = listOf(
            listOf(
                findViewById<TextView>(R.id.hobbitsprogress1_1),
                findViewById<TextView>(R.id.hobbitsprogress1_2),
                findViewById<TextView>(R.id.hobbitsprogress1_3)
            ),
            listOf(
                findViewById<TextView>(R.id.hobbitsprogress2_1),
                findViewById<TextView>(R.id.hobbitsprogress2_2),
                findViewById<TextView>(R.id.hobbitsprogress2_3)
            ),
            listOf(
                findViewById<TextView>(R.id.hobbitsprogress3_1),
                findViewById<TextView>(R.id.hobbitsprogress3_2),
                findViewById<TextView>(R.id.hobbitsprogress3_3)
            )
        )

        database = FirebaseDatabase.getInstance().getReference("users").child(userId)
        val hobbiesRef = database.child("categories").child("hobbies")

// Fetch the hobby data from the database
        hobbiesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbySnapshot = dataSnapshot.children.firstOrNull { snapshot ->
                    snapshot.child("hobbits").child("hobbit0").child("name").getValue(String::class.java) != null
                }

                if (hobbySnapshot != null) {
                    changeableHobbyNameTextView.text = hobbySnapshot.child("name").getValue(String::class.java)

                    // Fetch hobbit names
                    val hobbitNames = (0 until 3).map { hobbitIndex ->
                        hobbySnapshot.child("hobbits").child("hobbit$hobbitIndex").child("name").getValue(String::class.java)
                    }

                    // Set hobbit names to text views
                    hobbitNameTextView1.text = hobbitNames.getOrNull(0)
                    hobbitNameTextView2.text = hobbitNames.getOrNull(1)
                    hobbitNameTextView3.text = hobbitNames.getOrNull(2)

                    // Fetch and set hobbit bits to text views
                    for (hobbitIndex in 0 until 3) {
                        val bitsSnapshot = hobbySnapshot.child("hobbits").child("hobbit$hobbitIndex").child("bits")
                        for (bitIndex in 0 until 3) {
                            val bitText = bitsSnapshot.child("bit$bitIndex").getValue(String::class.java)
                            hobbitBitTextViews[hobbitIndex][bitIndex].text = bitText
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Progress_List", "Failed to fetch hobby data: ${databaseError.message}")
            }
        })
    }
}