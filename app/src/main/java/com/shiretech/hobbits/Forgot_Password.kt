package com.shiretech.hobbits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.shiretech.hobbits.databinding.ForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class Forgot_Password : AppCompatActivity() {

    private lateinit var binding: ForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var resetPassword: EditText
    private lateinit var btnResetPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resetPassword = findViewById(R.id.EditTxtForgotPassEmail)
        btnResetPassword = findViewById(R.id.sendEmailButton)

        firebaseAuth = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener {
            val sPassword = resetPassword.text.toString()

            if (sPassword.isNotEmpty()){
            firebaseAuth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Check your Email for password reset.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this,"No email provided.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backtoLogin.setOnClickListener{
            finish()
        }
    }
}
