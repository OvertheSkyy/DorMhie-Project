package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shiretech.hobbits.databinding.LoginBinding

class Log_In : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var loginAttempts = 0
    private var lastLoginAttemptTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

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
                            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val verification = firebaseAuth.currentUser?.isEmailVerified
                                    if (verification == true) {
                                        // Login successful
                                        val user = firebaseAuth.currentUser
                                        val intent = Intent(this, Home::class.java)
                                        startActivity(intent)
                                    } else {
                                        // Email not verified
                                        Toast.makeText(this, "Please Verify your Email", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    // Login failed
                                    Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                                    loginAttempts++
                                    lastLoginAttemptTime = currentTime
                                }
                            }
                        } else {
                            // Too many login attempts within 1 minute
                            Toast.makeText(this, "Too many login attempts. Try again in 1 minute.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // User not found based on email
                        Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
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
    private fun getUserIdFromEmail(email: String, callback: (String?) -> Unit) {
        val database = FirebaseDatabase.getInstance().reference
        val usersRef = database.child("users")

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
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

}