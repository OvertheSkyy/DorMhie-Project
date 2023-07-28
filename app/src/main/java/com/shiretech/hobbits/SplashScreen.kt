package com.shiretech.hobbits

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import com.shiretech.hobbits.R

class SplashScreen : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private val Delay_Splash_Screen: Long = 3000 //delay duration for 3 seconds
    private val Total_Progress: Long = 3000

    private val splashRunnable = Runnable{
        val intent = Intent(this@SplashScreen, GetStarted::class.java) //To go to Main Activity or next page.
        startActivity(intent)
        finish()
    }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        progressBar = findViewById(R.id.Progress_Bar)
        progressBar.max = 75

        var currentProgress = 0
        val progressIncrement = 100
        val progressUpdateInterval = Total_Progress /progressIncrement

        handler.postDelayed(object: Runnable{
            override fun run() {
                progressBar.progress = currentProgress

                if(currentProgress < 100){
                    currentProgress++
                    handler.postDelayed(this, progressUpdateInterval)
                }
                else{
                    finish()
                }
            }
        }, progressUpdateInterval)

        handler.postDelayed(splashRunnable, Delay_Splash_Screen)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // this removes any pending splashrunnable once activity is destroyed,
    }
}