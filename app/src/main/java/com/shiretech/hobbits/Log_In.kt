package com.shiretech.hobbits

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.shiretech.hobbits.databinding.LoginBinding





class Log_In : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var loginAttempts = 0
    private var lastLoginAttemptTime: Long = 0
    private val RC_SIGN_IN = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)

        val logoGoogle = findViewById<ImageView>(R.id.ClickGoogle)
        logoGoogle.setOnClickListener {
            // Call the function to initiate Google Sign-In
            signInWithGoogle()
            onLogoGoogle()
        }


        //Check if the user have already logged in
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val userEmail = sharedPreferences.getString("userEmail", "")

        if (isLoggedIn && !userEmail.isNullOrEmpty()) {
            // User is already logged in, redirect to the Home activity
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        } else {
            binding.LoginButton.setOnClickListener {
                val email = binding.EditTxtEmail.text.toString()
                val password = binding.EditTxtPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val loginAttemptsLimit = 3
                    val loginAttemptsWindow = 60000L // 1 minute in milliseconds

                    getUserIdFromEmail(email) { userId ->
                        if (userId != null) {
                            // User found based on email
                            val currentTime = System.currentTimeMillis()
                            if (loginAttempts < loginAttemptsLimit || currentTime - lastLoginAttemptTime >= loginAttemptsWindow) {
                                // Perform login
                                firebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val verification =
                                                firebaseAuth.currentUser?.isEmailVerified
                                            if (verification == true) {
                                                // Login successful
                                                val user = firebaseAuth.currentUser
                                                val intent = Intent(this, Home::class.java)
                                                startActivity(intent)
                                            } else {
                                                // Email not verified
                                                Toast.makeText(
                                                    this,
                                                    "Please Verify your Email",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            // Login failed
                                            Toast.makeText(
                                                this,
                                                "Login failed. Please check your credentials.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            loginAttempts++
                                            lastLoginAttemptTime = currentTime
                                        }
                                    }
                            } else {
                                // Too many login attempts within 1 minute
                                Toast.makeText(
                                    this,
                                    "Too many login attempts. Try again in 1 minute.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            // User not found based on email
                            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //save login status (remember me)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("userEmail", email)
                    editor.apply()
                } else {
                    Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.CreateAccButton.setOnClickListener {
            val createAccountIntent = Intent(this, Create_Account::class.java)
            startActivity(createAccountIntent)
        }
        binding.redirectforgotPassword.setOnClickListener {
            val intent = Intent(this, Forgot_Password::class.java)
            startActivity(intent)
        }


    }


    private fun signInWithGoogle() {
        // Your existing code to initiate Google Sign-In
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun getUserIdFromEmail(email: String, callback: (String?) -> Unit) {
        val database = FirebaseDatabase.getInstance().reference
        val usersRef = database.child("users")

        usersRef.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Loop through the snapshot to find the user ID
                        for (userSnapshot in dataSnapshot.children) {
                            val userId = userSnapshot.key
                            callback(userId)
                            return
                        }
                    }
                    callback(null) // No matching user found
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error, if needed
                    callback(null)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Google Sign-In was successful, check if the user is new
                    val isNewUser = task.result?.additionalUserInfo?.isNewUser ?: false
                    if (isNewUser) {
                        // New user, perform additional actions if needed
                        val user = firebaseAuth.currentUser
                        // Add user data to your database, e.g., name and email
                        val usersRef = FirebaseDatabase.getInstance().getReference("users")
                        usersRef.child(user?.uid ?: "").child("name").setValue(user?.displayName)
                        usersRef.child(user?.uid ?: "").child("email").setValue(user?.email)
                    }

                    // Login successful
                    val user = firebaseAuth.currentUser
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {

                }
            }
    }

    private fun onLogoGoogle() {
        // Check if the user is already signed in with Google
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            // If the user is already signed in, sign them out first
            googleSignInClient.signOut().addOnCompleteListener {
                // Start the Google Sign-In flow after sign-out
                signInWithGoogle()
            }
        } else {
            // If the user is not signed in, start the Google Sign-In flow directly
            signInWithGoogle()
        }
    }


}



