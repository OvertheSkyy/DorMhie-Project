package com.shiretech.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

class Progress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_page)

        val UnselectedHomeImageClick = findViewById<ImageView>(R.id.ClickHome)
        UnselectedHomeImageClick.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val UnselectedUserImageClick = findViewById<ImageView>(R.id.ClickUnselectedUser)
        UnselectedUserImageClick.setOnClickListener {
            val intent = Intent(this, User_Profile::class.java)
            startActivity(intent)
        }
        val UnselectedCategoriesImageClick = findViewById<ImageView>(R.id.ClickUnselectedCategories)
        UnselectedCategoriesImageClick.setOnClickListener {
            val intent = Intent(this, Categories::class.java)
            startActivity(intent)
        }
        val UnselectedCalendarImageClick = findViewById<ImageView>(R.id.ClickUnselectedCalendar)
        UnselectedCalendarImageClick.setOnClickListener{
            val intent = Intent(this,Calendar::class.java)
            startActivity(intent)
        }
    }
}