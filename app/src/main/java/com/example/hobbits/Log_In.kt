package com.example.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class Log_In : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val CreateAccountButton = findViewById<Button>(R.id.CreateAccButton)
        CreateAccountButton.setOnClickListener {
            startActivity(Intent(this, Create_Account::class.java)) //this will start Create Account Activity
        }
    }
}