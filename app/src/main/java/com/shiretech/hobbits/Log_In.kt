package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shiretech.hobbits.R
import com.shiretech.hobbits.databinding.LoginBinding

class Log_In : AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.LoginButton.setOnClickListener {
            val email = binding.EditTxtEmail.text.toString()
            val password = binding.EditTxtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
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
            val intent = Intent(this, Forgot_Passwrod::class.java)
            startActivity(intent)
        }
    }
}