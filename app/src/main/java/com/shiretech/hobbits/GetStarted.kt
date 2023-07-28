package com.shiretech.hobbits

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.content.Intent
import android.preference.PreferenceManager
import com.shiretech.hobbits.R

class GetStarted : AppCompatActivity() {

    private lateinit var NaviDot: LinearLayout
    private var currentPage = 0

    private val PREFS_NAME = "MyPrefsFile"
    companion object{
        private const val PREF_KEY_HAS_VIEWED_PAGES = "has_viewed_pages"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val hasViewedPages = sharedPrefs.getBoolean(PREF_KEY_HAS_VIEWED_PAGES, false)

        if (hasViewedPages){
            startLog_InActivity()
            return
        }
        
        setContentView(R.layout.getstarted_welcome_page)

        NaviDot = findViewById(R.id.NaviDot)
    }
    private fun navigatetoNextPage(){
        when(currentPage){
            0 -> {
                val NextButton = findViewById<Button>(R.id.Nextbutton)
                NextButton.setOnClickListener {
                    currentPage++
                    navigatetoNextPage()
                }
            }
            1 -> {
                setContentView(R.layout.getstarted_next_page)
                val NextPage = findViewById<Button>(R.id.Nextpagebutton)
                NextPage.setOnClickListener {
                    currentPage++
                    navigatetoNextPage()
                }
            }
            2 -> {
                setContentView(R.layout.getstarted_last_page)
                val GetStartedButton = findViewById<Button>(R.id.GetStartedbutton)
                GetStartedButton.setOnClickListener {
                    currentPage++
                    navigatetoNextPage()
                    val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.putBoolean(PREF_KEY_HAS_VIEWED_PAGES, true)
                    editor.apply()
                    startLog_InActivity()
                }
            }
            else -> {
                startLog_InActivity()
            }
        }
    }
    private fun startLog_InActivity(){
        val intent = Intent(this, Log_In::class.java)
        startActivity(intent)
        finish()
    }
    override fun onResume() {
        super.onResume()
        navigatetoNextPage()
    }
}