package com.example.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.ImageView

class Create_Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        val backtoLogInImageView = findViewById<ImageView>(R.id.BacktoLogInpage)
        backtoLogInImageView.setOnClickListener {

            val intent = Intent(this, Log_In::class.java)
            startActivity(intent)
            finish()
        }

        val TextViewredirecttoLogIn = findViewById<TextView>(R.id.redirecttoLogIn)
        TextViewredirecttoLogIn.setOnClickListener {

            val intent = Intent(this, Log_In::class.java)
            startActivity(intent)
            finish()
        }
    }
}