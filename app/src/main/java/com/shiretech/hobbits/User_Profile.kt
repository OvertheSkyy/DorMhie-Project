package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class User_Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

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
        val UnselectedCalendarImageClick = findViewById<ImageView>(R.id.ClickUnselectedCalendar)
        UnselectedCalendarImageClick.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }

        val username = findViewById<TextView>(R.id.UserName)

        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val uid = currentUser.uid
            val usersRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.child("name").getValue(String::class.java)

                    if (user != null) {
                        val welcomeMessage = "$user"
                        username.text = welcomeMessage
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            })
        }
    }
}