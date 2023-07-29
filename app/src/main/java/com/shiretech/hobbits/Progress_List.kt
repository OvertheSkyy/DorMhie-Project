package com.shiretech.hobbits

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shiretech.hobbits.databinding.ProgressListBinding

class Progress_List : AppCompatActivity() {
    private lateinit var binding: ProgressListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProgressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
