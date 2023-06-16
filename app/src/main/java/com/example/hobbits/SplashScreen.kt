package com.example.hobbits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {

    private val Delay_Splash_Screen: Long = 3000 //delay duration for 3 seconds

    private val splashRunnable = Runnable{
        val intent = Intent(this@SplashScreen, GetStarted::class.java) //To go to Main Activity or next page.
        startActivity(intent)
        finish()
    }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        handler.postDelayed(splashRunnable, Delay_Splash_Screen)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(splashRunnable) // this removes any pending splashrunnable once activity is destroyed,
    }
}