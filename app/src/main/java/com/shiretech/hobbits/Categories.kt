package com.shiretech.hobbits

import com.google.firebase.database.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Categories : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_subpage)

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference

        // Fetch data from Firebase and render it in the text views
        fetchCategories()

        // Set click listeners for other elements
        val UnselectedHomeImageClick = findViewById<ImageView>(R.id.ClickHome)
        UnselectedHomeImageClick.setOnClickListener {
            val intent = Intent(this, Home::class.java)
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
    }

    private fun fetchCategories() {
        val categoriesReference = database.child("categories")
        categoriesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val categoryList = ArrayList<String>()
                    for (categorySnapshot in dataSnapshot.children) {
                        val category = categorySnapshot.child("name").getValue(String::class.java)
                        category?.let { categoryList.add(it) }
                    }
                    renderCategories(categoryList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error here if needed
            }
        })
    }

    private fun renderCategories(categoryList: List<String>) {

        val firstCategoryTextView = findViewById<TextView>(R.id.FirstCategory)
        val secondCategoryTextView = findViewById<TextView>(R.id.SecondCategory)
        val thirdCategoryTextView = findViewById<TextView>(R.id.ThirdCategory)
        val fourthCategoryTextView = findViewById<TextView>(R.id.FourthCategory)
        val fifthCategoryTextView = findViewById<TextView>(R.id.FifthCategory)
        val sixthCategoryTextView = findViewById<TextView>(R.id.SixthCategory)

        if (categoryList.size >= 1) {
            firstCategoryTextView.text = categoryList[0]
        }
        if (categoryList.size >= 2) {
            secondCategoryTextView.text = categoryList[1]
        }
        if (categoryList.size >= 3) {
            thirdCategoryTextView.text = categoryList[2]
        }
        if (categoryList.size >= 4) {
            fourthCategoryTextView.text = categoryList[3]
        }
        if (categoryList.size >= 5) {
            fifthCategoryTextView.text = categoryList[4]
        }
        if (categoryList.size >= 6) {
            sixthCategoryTextView.text = categoryList[5]
        }
    }
}
