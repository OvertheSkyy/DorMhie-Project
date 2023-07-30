package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.view.View
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.bumptech.glide.Glide
import android.content.SharedPreferences


class User_Profile : AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_STORAGE_REQUEST = 101
    }

    private lateinit var profileImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)

        profileImage = findViewById(R.id.ProfileImage)

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

        profileImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST
                )
            } else {
                // Permission already granted, open the gallery
                openGallery()
            }
        }
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Retrieve the saved image URI and load it if available
        val savedImageUriString = sharedPreferences.getString("imageUri", null)
        if (savedImageUriString != null) {
            val savedImageUri = Uri.parse(savedImageUriString)
            Glide.with(this)
                .load(savedImageUri)
                .circleCrop()
                .into(profileImage)
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

        // Handle the logout button click
        val logoutButton = findViewById<View>(R.id.btnlogout)
        logoutButton.setOnClickListener {
            // Clear login status (remember me)
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)
            editor.remove("userEmail") // Remove the userEmail as well, if you stored it
            editor.apply()

            // Redirect to the Log_In activity
            val intent = Intent(this, Log_In::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

            val logoutButton = findViewById<View>(R.id.btnlogout)
            logoutButton.setOnClickListener {
                logoutAndNavigateToLogin() //
            }
        }
    }

    private fun logoutAndNavigateToLogin() {
        val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut()
            .addOnCompleteListener {
                FirebaseAuth.getInstance().signOut()

                // Clear the shared preferences indicating the user is logged in
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", false)
                editor.putString("userEmail", null)
                editor.apply()

                // Redirect the user back to the login screen
                val intent = Intent(this, Log_In::class.java)
                startActivity(intent)
                finish() // Optional: finish the current activity to prevent back navigation
            }
    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)

    }
    // ActivityResultLauncher to handle gallery intent result
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                // Retrieve the selected image URI from the intent data
                val imageUri: Uri? = data?.data
                // Now you can use the selected image URI to display the image using Glide
                if (imageUri != null) {
                    Glide.with(this)
                        .load(imageUri)
                        .circleCrop() // This will make the image circular
                        .into(profileImage)// Apply circle crop transformation

                    val editor = sharedPreferences.edit()
                    editor.putString("imageUri", imageUri.toString())
                    editor.apply()
                }
            }
        }
    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the gallery
                openGallery()
            } else {
                // Permission denied, show a toast or handle the case
                Toast.makeText(
                    this,
                    "Storage permission denied. Cannot open gallery.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
