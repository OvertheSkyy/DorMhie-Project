package com.shiretech.hobbits

import com.google.firebase.database.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        //Cooking Category
        val cookingCategoryDropdown = findViewById<ImageView>(R.id.CookingCategorydropdown)
        val cookingHobbiesContainer = findViewById<RelativeLayout>(R.id.CookinghobbiesContainer)
        val cookinglayoutParams = cookingHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val addFirstCategoryFirstHobbyButton = findViewById<Button>(R.id.AddFirstCategoryFirstHobby)

        addFirstCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 0 // Replace with the actual index of the category
            val hobbyIndex = 0 // Replace with the actual index of the hobby within the category

            fetchHobbiesAndHobbitsForCategory(categoryIndex) { hobbiesWithHobbits ->
                if (hobbyIndex < hobbiesWithHobbits.size) {
                    val selectedHobby = hobbiesWithHobbits[hobbyIndex].first
                    val selectedHobbits = hobbiesWithHobbits[hobbyIndex].second

                    val intent = Intent(this, EditHobby::class.java)
                    intent.putExtra("hobbyName", selectedHobby)
                    intent.putStringArrayListExtra("hobbits", ArrayList(selectedHobbits))
                    startActivity(intent)
                }
            }
        }

        cookingCategoryDropdown.setOnClickListener {
            if (cookingHobbiesContainer.visibility == View.GONE){
                cookingHobbiesContainer.visibility = View.VISIBLE
                cookinglayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                cookingHobbiesContainer.visibility = View.GONE
                cookinglayoutParams.setMargins(0,0,0,0)
            }
        }

        //Creative Arts
        val creativeartsCategoryDropdown = findViewById<ImageView>(R.id.CreativeArtsDropdown)
        val creativeArtsHobbiesContainer = findViewById<RelativeLayout>(R.id.CreativeArtshobbiesContainer)
        val creativelayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        creativeartsCategoryDropdown.setOnClickListener {
            if (creativeArtsHobbiesContainer.visibility == View.GONE){
                creativeArtsHobbiesContainer.visibility = View.VISIBLE
                creativelayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                creativeArtsHobbiesContainer.visibility = View.GONE
                creativelayoutParams.setMargins(0,0,0,0)
            }
        }

        //Health and Wellness
        val healthAndwellnessCategoryDropdown = findViewById<ImageView>(R.id.HealthandWellnessDropdown)
        val healthandwellnessHobbiesContainer = findViewById<RelativeLayout>(R.id.HealthandWellnesshobbiesContainer)
        val HealthandWellnesslayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        healthAndwellnessCategoryDropdown.setOnClickListener {
            if (healthandwellnessHobbiesContainer.visibility == View.GONE){
                healthandwellnessHobbiesContainer.visibility = View.VISIBLE
                HealthandWellnesslayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                healthandwellnessHobbiesContainer.visibility = View.GONE
                HealthandWellnesslayoutParams.setMargins(0,0,0,0)
            }
        }

        //Music and Performing
        val musicandperformingCategoryDropdown = findViewById<ImageView>(R.id.MusicandperformingDropdown)
        val musicandperformingHobbiesContainer = findViewById<RelativeLayout>(R.id.MusicandPerforminghobbiesContainer)
        val MusicandPerforminglayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        musicandperformingCategoryDropdown.setOnClickListener {
            if (musicandperformingHobbiesContainer.visibility == View.GONE){
                musicandperformingHobbiesContainer.visibility = View.VISIBLE
                MusicandPerforminglayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                musicandperformingHobbiesContainer.visibility = View.GONE
                MusicandPerforminglayoutParams.setMargins(0,0,0,0)
            }
        }

        //Reading and Writing
        val readingandwritingCategoryDropdown = findViewById<ImageView>(R.id.ReadingandWritingDropdown)
        val readingandwritingHobbiesContainer = findViewById<RelativeLayout>(R.id.ReadingandWritinghobbiesContainer)
        val ReadingandWritinglayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        readingandwritingCategoryDropdown.setOnClickListener {
            if (readingandwritingHobbiesContainer.visibility == View.GONE){
                readingandwritingHobbiesContainer.visibility = View.VISIBLE
                ReadingandWritinglayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                readingandwritingHobbiesContainer.visibility = View.GONE
                ReadingandWritinglayoutParams.setMargins(0,0,0,0)
            }
        }

        //Science and Technology
        val scienceandtechCategoryDropdown = findViewById<ImageView>(R.id.ScienceandTechDropdown)
        val scienceandtechHobbiesContainer = findViewById<RelativeLayout>(R.id.ScienceandTechhobbiesContainer)
        val ScienceandTechlayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams

        scienceandtechCategoryDropdown.setOnClickListener {
            if (scienceandtechHobbiesContainer.visibility == View.GONE){
                scienceandtechHobbiesContainer.visibility = View.VISIBLE
                ScienceandTechlayoutParams.setMargins(0,-55.dpToPx(),0,0)
            }
            else{
                scienceandtechHobbiesContainer.visibility = View.GONE
                ScienceandTechlayoutParams.setMargins(0,0,0,0)
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

    private fun fetchHobbiesForCategory(categoryIndex: Int, onComplete: (List<String>) -> Unit) {
        val categoryReference = database.child("categories").child("$categoryIndex").child("hobbies")
        categoryReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbies = if (dataSnapshot.exists()) {
                    val hobbyList = mutableListOf<String>()
                    for (hobbySnapshot in dataSnapshot.children) {
                        val hobby = hobbySnapshot.child("name").getValue(String::class.java)
                        hobby?.let { hobbyList.add(it) }
                    }
                    hobbyList
                } else {
                    emptyList()
                }
                Log.d("Categories", "Fetched hobbies for category $categoryIndex: $hobbies")
                onComplete(hobbies)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Categories", "Failed to fetch hobbies for category $categoryIndex: $databaseError")
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

            fetchHobbiesForCategory(0) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(0)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }

        if (categoryList.size >= 2) {
            val secondCategory = categoryList[1]
            secondCategoryTextView.text = secondCategory

            fetchHobbiesForCategory(1) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(1)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 3) {
            val thirdCategory = categoryList[2]
            thirdCategoryTextView.text = thirdCategory

            fetchHobbiesForCategory(2) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(2)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 4) {
            val fourthCategory = categoryList[3]
            fourthCategoryTextView.text = fourthCategory

            fetchHobbiesForCategory(3) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(3)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 5) {
            val fifthCategory = categoryList[4]
            fifthCategoryTextView.text = fifthCategory

            fetchHobbiesForCategory(4) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(4)
                renderHobbies(hobbies, *hobbyTextViews)
            }
        }
        if (categoryList.size >= 6) {
            val sixthCategory = categoryList[5]
            sixthCategoryTextView.text = sixthCategory

            fetchHobbiesForCategory(5) { hobbies ->
                val hobbyTextViews = getHobbyTextViewsForCategory(5)
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
                findViewById(R.id.SecondCategoryFirstHobby),
                findViewById(R.id.SecondCategorySecondHobby),
                findViewById(R.id.SecondCategoryThirdHobby),
                findViewById(R.id.SecondCategoryFourthHobby)
            )

            2 -> arrayOf(
                findViewById(R.id.ThirdCategoryFirstHobby),
                findViewById(R.id.ThirdCategorySecondHobby),
                findViewById(R.id.ThirdCategoryThirdHobby),
                findViewById(R.id.ThirdCategoryFourthHobby)
            )

            3 -> arrayOf(
                findViewById(R.id.FourthCategoryFirstHobby),
                findViewById(R.id.FourthCategorySecondHobby),
                findViewById(R.id.FourthCategoryThirdHobby),
                findViewById(R.id.FourthCategoryFourthHobby)
            )

            4 -> arrayOf(
                findViewById(R.id.FifthCategoryFirstHobby),
                findViewById(R.id.FifthCategorySecondHobby),
                findViewById(R.id.FifthCategoryThirdHobby),
                findViewById(R.id.FifthCategoryFourthHobby)
            )

            5 -> arrayOf(
                findViewById(R.id.SixthCategoryFirstHobby),
                findViewById(R.id.SixthCategorySecondHobby),
                findViewById(R.id.SixthCategoryThirdHobby),
                findViewById(R.id.SixthCategoryFourthHobby)
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
    private fun fetchHobbiesAndHobbitsForCategory(categoryIndex: Int, onComplete: (List<Pair<String, List<String>>>) -> Unit) {
        val categoryReference = database.child("categories").child("$categoryIndex").child("hobbies")
        categoryReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val hobbiesWithHobbits = if (dataSnapshot.exists()) {
                    val hobbyList = mutableListOf<Pair<String, List<String>>>()
                    for (hobbySnapshot in dataSnapshot.children) {
                        val hobby = hobbySnapshot.child("name").getValue(String::class.java)
                        val hobbitsSnapshot = hobbySnapshot.child("hobbits")
                        val hobbits = if (hobbitsSnapshot.exists()) {
                            val hobbitList = mutableListOf<String>()
                            for (hobbitSnapshot in hobbitsSnapshot.children) {
                                val hobbit = hobbitSnapshot.getValue(String::class.java)
                                hobbit?.let { hobbitList.add(it) }
                            }
                            hobbitList
                        } else {
                            emptyList()
                        }
                        hobby?.let { hobbyList.add(it to hobbits) }
                    }
                    hobbyList
                } else {
                    emptyList()
                }
                Log.d("Categories", "Fetched hobbies with hobbits for category $categoryIndex: $hobbiesWithHobbits")
                onComplete(hobbiesWithHobbits)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Categories", "Failed to fetch hobbies with hobbits for category $categoryIndex: $databaseError")
                // Handle the error here if needed
            }
        })
    }
}
