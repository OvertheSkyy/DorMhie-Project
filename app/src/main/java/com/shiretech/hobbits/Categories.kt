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
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat

class Categories : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_page)

        database = FirebaseDatabase.getInstance().reference

        editTextSearch = findViewById(R.id.EditTxtSearch)

        fetchCategories()


        //First Category
        val cookingCategoryDropdown = findViewById<ImageView>(R.id.CookingCategorydropdown)
        val cookingHobbiesContainer = findViewById<RelativeLayout>(R.id.CookinghobbiesContainer)
        val cookinglayoutParams = cookingHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val cookingCategoryTextView = findViewById<TextView>(R.id.FirstCategory)
        val firstcategory = findViewById<TextView>(R.id.FirstCategory)
        handleCategoryClick(
            cookingCategoryDropdown,
            cookingHobbiesContainer,
            cookinglayoutParams,
            cookingCategoryTextView,
            firstcategory)
        handleCategoryClick(
            cookingCategoryDropdown,
            cookingHobbiesContainer,
            cookinglayoutParams,
            cookingCategoryTextView,
            firstcategory)

        val addFirstCategoryFirstHobbyButton = findViewById<Button>(R.id.AddFirstCategoryFirstHobby)
        val addFirstCategorySecondHobbyButton = findViewById<Button>(R.id.AddFirstCategorySecondHobby)
        val addFirstCategoryThirdHobbyButton = findViewById<Button>(R.id.AddFirstCategoryThirdHobby)
        val addFirstCategoryFourthHobbyButton = findViewById<Button>(R.id.AddFirstCategoryFourthHobby)

        addFirstCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 0
            val hobbyIndex = 0

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

        addFirstCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 0
            val hobbyIndex = 1

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

        addFirstCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 0
            val hobbyIndex = 2

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

        addFirstCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 0
            val hobbyIndex = 3

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

        //Second Hobby
        val creativeartsCategoryDropdown = findViewById<ImageView>(R.id.CreativeArtsDropdown)
        val creativeArtsHobbiesContainer = findViewById<RelativeLayout>(R.id.CreativeArtshobbiesContainer)
        val creativelayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val creativeartsCategoryTextView = findViewById<TextView>(R.id.SecondCategory)
        val secondcategory = findViewById<TextView>(R.id.SecondCategory)
        handleCategoryClick(
            creativeartsCategoryDropdown,
            creativeArtsHobbiesContainer,
            creativelayoutParams,
            creativeartsCategoryTextView,
            secondcategory)
        handleCategoryClick(
            creativeartsCategoryDropdown,
            creativeArtsHobbiesContainer,
            creativelayoutParams,
            creativeartsCategoryTextView,
            secondcategory)

        val addSecondCategoryFirstHobbyButton = findViewById<Button>(R.id.AddSecondCategoryFirstHobby)
        val addSecondCategorySecondHobbyButton = findViewById<Button>(R.id.AddSecondCategorySecondHobby)
        val addSecondCategoryThirdHobbyButton = findViewById<Button>(R.id.AddSecondCategoryThirdHobby)
        val addSecondCategoryFourthHobbyButton = findViewById<Button>(R.id.AddSecondCategoryFourthHobby)



        addSecondCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 1
            val hobbyIndex = 0

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

        addSecondCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 1
            val hobbyIndex = 1

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

        addSecondCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 1
            val hobbyIndex = 2

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

        addSecondCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 1
            val hobbyIndex = 3

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

        //Third Hobby
        val healthAndwellnessCategoryDropdown = findViewById<ImageView>(R.id.HealthandWellnessDropdown)
        val healthandwellnessHobbiesContainer = findViewById<RelativeLayout>(R.id.HealthandWellnesshobbiesContainer)
        val HealthandWellnesslayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val healthAndwellnessCategoryTextView = findViewById<TextView>(R.id.ThirdCategory)
        val thirdcategory = findViewById<TextView>(R.id.ThirdCategory)
        handleCategoryClick(
            healthAndwellnessCategoryDropdown,
            healthandwellnessHobbiesContainer,
            HealthandWellnesslayoutParams,
            healthAndwellnessCategoryTextView,
            thirdcategory)
        handleCategoryClick(
            healthAndwellnessCategoryDropdown,
            healthandwellnessHobbiesContainer,
            HealthandWellnesslayoutParams,
            healthAndwellnessCategoryTextView,
            thirdcategory)

        val addThirdCategoryFirstHobbyButton = findViewById<Button>(R.id.AddThirdCategoryFirstHobby)
        val addThirdCategorySecondHobbyButton = findViewById<Button>(R.id.AddThirdCategorySecondHobby)
        val addThirdCategoryThirdHobbyButton = findViewById<Button>(R.id.AddThirdCategoryThirdHobby)
        val addThirdCategoryFourthHobbyButton = findViewById<Button>(R.id.AddThirdCategoryFourthHobby)

        addThirdCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 2
            val hobbyIndex = 0

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

        addThirdCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 2
            val hobbyIndex = 1

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

        addThirdCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 2
            val hobbyIndex = 2

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

        addThirdCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 2
            val hobbyIndex = 3

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

        //Fourth Hobby
        val musicandperformingCategoryDropdown = findViewById<ImageView>(R.id.MusicandperformingDropdown)
        val musicandperformingHobbiesContainer = findViewById<RelativeLayout>(R.id.MusicandPerforminghobbiesContainer)
        val MusicandPerforminglayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val musicandperformingCategoryTextView = findViewById<TextView>(R.id.FourthCategory)
        val fourthcategory = findViewById<TextView>(R.id.FourthCategory)
        handleCategoryClick(
            musicandperformingCategoryDropdown,
            musicandperformingHobbiesContainer,
            MusicandPerforminglayoutParams,
            musicandperformingCategoryTextView,
            fourthcategory)
        handleCategoryClick(
            musicandperformingCategoryDropdown,
            musicandperformingHobbiesContainer,
            MusicandPerforminglayoutParams,
            musicandperformingCategoryTextView,
            fourthcategory)

        val addFourthCategoryFirstHobbyButton = findViewById<Button>(R.id.AddFourthCategoryFirstHobby)
        val addFourthCategorySecondHobbyButton = findViewById<Button>(R.id.AddFourthCategorySecondHobby)
        val addFourthCategoryThirdHobbyButton = findViewById<Button>(R.id.AddFourthCategoryThirdHobby)
        val addFourthCategoryFourthHobbyButton = findViewById<Button>(R.id.AddFourthCategoryFourthHobby)


        addFourthCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 3
            val hobbyIndex = 0

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

        addFourthCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 3
            val hobbyIndex = 1

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

        addFourthCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 3
            val hobbyIndex = 2

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

        addFourthCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 3
            val hobbyIndex = 3

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

        //Fifth Hobby
        val readingandwritingCategoryDropdown = findViewById<ImageView>(R.id.ReadingandWritingDropdown)
        val readingandwritingHobbiesContainer = findViewById<RelativeLayout>(R.id.ReadingandWritinghobbiesContainer)
        val ReadingandWritinglayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val readingandwritingCategoryTextView = findViewById<TextView>(R.id.FifthCategory)
        val fifthcategory = findViewById<TextView>(R.id.FifthCategory)
        handleCategoryClick(
            readingandwritingCategoryDropdown,
            readingandwritingHobbiesContainer,
            ReadingandWritinglayoutParams,
            readingandwritingCategoryTextView,
            fifthcategory)
        handleCategoryClick(
            readingandwritingCategoryDropdown,
            readingandwritingHobbiesContainer,
            ReadingandWritinglayoutParams,
            readingandwritingCategoryTextView,
            fifthcategory)

        val addFifthCategoryFirstHobbyButton = findViewById<Button>(R.id.AddFifthCategoryFirstHobby)
        val addFifthCategorySecondHobbyButton = findViewById<Button>(R.id.AddFifthCategorySecondHobby)
        val addFifthCategoryThirdHobbyButton = findViewById<Button>(R.id.AddFifthCategoryThirdHobby)
        val addFifthCategoryFourthHobbyButton = findViewById<Button>(R.id.AddFifthCategoryFourthHobby)

        addFifthCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 4
            val hobbyIndex = 0

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

        addFifthCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 4
            val hobbyIndex = 1

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

        addFifthCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 4
            val hobbyIndex = 2

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

        addFifthCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 4
            val hobbyIndex = 3

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

        //Sixth Hobby
        val scienceandtechCategoryDropdown = findViewById<ImageView>(R.id.ScienceandTechDropdown)
        val scienceandtechHobbiesContainer = findViewById<RelativeLayout>(R.id.ScienceandTechhobbiesContainer)
        val ScienceandTechlayoutParams = creativeArtsHobbiesContainer.layoutParams as LinearLayout.LayoutParams
        val scienceandtechCategoryTextView = findViewById<TextView>(R.id.SixthCategory)
        val sixthcategory = findViewById<TextView>(R.id.SixthCategory)
        handleCategoryClick(
            scienceandtechCategoryDropdown,
            scienceandtechHobbiesContainer,
            ScienceandTechlayoutParams,
            scienceandtechCategoryTextView,
            sixthcategory)
        handleCategoryClick(
            scienceandtechCategoryDropdown,
            scienceandtechHobbiesContainer,
            ScienceandTechlayoutParams,
            scienceandtechCategoryTextView,
            sixthcategory) // Replace this with the actual TextView for the selected category)

        val addSixthCategoryFirstHobbyButton = findViewById<Button>(R.id.AddSixthCategoryFirstHobby)
        val addSixthCategorySecondHobbyButton = findViewById<Button>(R.id.AddSixthCategorySecondHobby)
        val addSixthCategoryThirdHobbyButton = findViewById<Button>(R.id.AddSixthCategoryThirdHobby)
        val addSixthCategoryFourthHobbyButton = findViewById<Button>(R.id.AddSixthCategoryFourthHobby)

        addSixthCategoryFirstHobbyButton.setOnClickListener {
            val categoryIndex = 5
            val hobbyIndex = 0

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

        addSixthCategorySecondHobbyButton.setOnClickListener {
            val categoryIndex = 5
            val hobbyIndex = 1

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

        addSixthCategoryThirdHobbyButton.setOnClickListener {
            val categoryIndex = 5
            val hobbyIndex = 2

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

        addSixthCategoryFourthHobbyButton.setOnClickListener {
            val categoryIndex = 5
            val hobbyIndex = 3

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

        val UnselectedHomeImageClick = findViewById<ImageView>(R.id.ClickUnselectedHome)
        UnselectedHomeImageClick.setOnClickListener {
            val intent = Intent(this, Home::class.java)
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
                        val hobbyName = hobbySnapshot.child("name").getValue(String::class.java)
                        val hobbitsSnapshot = hobbySnapshot.child("hobbits")
                        val hobbits = if (hobbitsSnapshot.exists()) {
                            val hobbitList = mutableListOf<String>()
                            for (hobbitSnapshot in hobbitsSnapshot.children) {
                                val hobbitName = hobbitSnapshot.child("name").getValue(String::class.java)
                                val bitsSnapshot = hobbitSnapshot.child("bits")
                                val bits = if (bitsSnapshot.exists()) {
                                    bitsSnapshot.getValue(object : GenericTypeIndicator<List<String>>() {})
                                } else {
                                    emptyList()
                                }
                                hobbitName?.let { hobbitList.add(it) }
                            }
                            hobbitList
                        } else {
                            emptyList()
                        }
                        hobbyName?.let { hobbyList.add(it to hobbits) }
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
    private fun handleCategoryClick(
        dropdownImageView: ImageView,
        categoryContainer: RelativeLayout,
        layoutParams: LinearLayout.LayoutParams,
        categoryTextView: TextView,
        selectedCategoryTextView: TextView? = null
    ) {
        var isDropdownVisible = false

        fun toggleDropdown() {
            if (isDropdownVisible) {
                categoryContainer.visibility = View.GONE
                layoutParams.setMargins(0, 0, 0, 0)
                dropdownImageView.setImageResource(R.drawable.down)

                selectedCategoryTextView?.typeface = Typeface.DEFAULT
            } else {
                categoryContainer.visibility = View.VISIBLE
                layoutParams.setMargins(0, -55.dpToPx(), 0, 0)
                dropdownImageView.setImageResource(R.drawable.up)

                selectedCategoryTextView?.typeface = Typeface.DEFAULT_BOLD
            }
            isDropdownVisible = !isDropdownVisible
        }

        dropdownImageView.setOnClickListener {
            toggleDropdown()
        }

        categoryTextView.setOnClickListener {
            toggleDropdown()
        }
        selectedCategoryTextView?.typeface = Typeface.DEFAULT
    }

}
