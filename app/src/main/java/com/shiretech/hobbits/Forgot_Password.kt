package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Forgot_Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        val Back_ToLoginImageView = findViewById<ImageView>(R.id.backto_login)
        Back_ToLoginImageView.setOnClickListener {
            val intent = Intent(this, Log_In::class.java)
            startActivity(intent)
        }
    }
}