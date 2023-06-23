package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import com.shiretech.hobbits.R
import com.google.firebase.auth.FirebaseAuth
import com.shiretech.hobbits.databinding.CreateAccountBinding

class Create_Account : AppCompatActivity() {
    private lateinit var binding: CreateAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.redirecttoLogIn.setOnClickListener{
            val loginIntent = Intent(this, Log_In::class.java)
            startActivity(loginIntent)
        }

        binding.BacktoLogInpage.setOnClickListener{
            val backtologinIntent = Intent(this, Log_In::class.java)
            startActivity(backtologinIntent)
        }

        binding.CreateAccButton.setOnClickListener {
            val name = binding.EditTxtName.text.toString()
            val email = binding.EditTxtEmailAccCreate.text.toString()
            val password = binding.EditTxtPasswordAccCreate.text.toString()
            val confirmpassword = binding.EditTxtConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()) {
                if (password == confirmpassword) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, Log_In::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}