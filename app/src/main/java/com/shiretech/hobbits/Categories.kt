package com.shiretech.hobbits

import com.google.firebase.database.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setMargins

class Categories : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_subpage)

        database = FirebaseDatabase.getInstance().reference

        fetchCategories()

        val cookingCategoryDropdown = findViewById<ImageView>(R.id.CookingCategorydropdown)
        val cookingHobbiesContainer = findViewById<RelativeLayout>(R.id.CookinghobbiesContainer)
        val layoutParams = cookingHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        cookingCategoryDropdown.setOnClickListener {
            if (cookingHobbiesContainer.visibility == View.GONE){
                cookingHobbiesContainer.visibility = View.VISIBLE
                layoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                cookingHobbiesContainer.visibility = View.GONE
                layoutParams.setMargins(0,0,0,0)
            }
        }


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

    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
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
                    Log.d("Categories", "Fetched category list: $categoryList")
                    renderCategories(categoryList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Categories", "Database error: $databaseError")
                // Handle the error here if needed
            }
        })
    }

    private fun fetchHobbiesForCategory(category: String, index: Int, onComplete: (List<String>, Int) -> Unit) {
        val categoryReference = database.child("categories").child(index.toString()).child("hobbies")
        categoryReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbies = dataSnapshot.getValue(object : GenericTypeIndicator<List<String>>() {})
                Log.d("Categories", "Fetched hobbies for category $category (index: $index): $hobbies")
                onComplete(hobbies ?: emptyList(), index)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Categories", "Database error: $databaseError")
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
            val firstCategory = categoryList[0]
            firstCategoryTextView.text = firstCategory

            fetchHobbiesForCategory(firstCategory, 0) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 2) {
            val secondCategory = categoryList[1]
            secondCategoryTextView.text = secondCategory

            fetchHobbiesForCategory(secondCategory, 1) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 3) {
            val thirdCategory = categoryList[2]
            thirdCategoryTextView.text = thirdCategory

            fetchHobbiesForCategory(thirdCategory, 2) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 4) {
            val fourthCategory = categoryList[3]
            fourthCategoryTextView.text = fourthCategory

            fetchHobbiesForCategory(fourthCategory, 3) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 5) {
            val fifthCategory = categoryList[4]
            fifthCategoryTextView.text = fifthCategory

            fetchHobbiesForCategory(fifthCategory, 4) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 6) {
            val sixthCategory = categoryList[5]
            sixthCategoryTextView.text = sixthCategory

            fetchHobbiesForCategory(sixthCategory, 5) { hobbies, index ->
                val hobbyTextViews = getHobbyTextViewsForCategory(index)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
    }


    private fun getHobbyTextViewsForCategory(index: Int): Array<TextView> {
        // Return the appropriate array of TextViews based on the category index
        return when (index) {
            0 -> arrayOf(
                findViewById(R.id.FirstCategoryFirstHobby),
                findViewById(R.id.FirstCategorySecondHobby),
                findViewById(R.id.FirstCategoryThirdHobby),
                findViewById(R.id.FirstCategoryFourthHobby)
            )
            1 -> arrayOf(
                //Add textView id for Second Category
            )
            2 -> arrayOf(
                //Add textView id for Third Category
            )
            3 -> arrayOf(
                //Add textView id for Fourth Category
            )
            4 -> arrayOf(
                //Add textView id for Fifth Category
            )
            5 -> arrayOf(
                //Add textView id for Sixth Category
            )
            else -> emptyArray()
        }
    }

    private fun renderHobbies(hobbies: List<String>?, vararg hobbyTextViews: TextView) {
        if (hobbies != null) {
            for (i in hobbyTextViews.indices) {
                if (i < hobbies.size) {
                    hobbyTextViews[i].text = hobbies[i]
                    hobbyTextViews[i].visibility = View.VISIBLE
                } else {
                    hobbyTextViews[i].text = ""
                    hobbyTextViews[i].visibility = View.GONE
                }
            }
        } else {
            // Handle the case when hobbies are null (e.g., display a default message or hide the TextViews)
            for (hobbyTextView in hobbyTextViews) {
                hobbyTextView.text = "No hobbies found"
                hobbyTextView.visibility = View.GONE
            }
        }
    }
}
