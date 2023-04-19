package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var continueBtn:Button
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        continueBtn=findViewById(R.id.continueBtn)
        toolbar=findViewById(R.id.toolbar)

        continueBtn.setOnClickListener {
            val intent= Intent(this,VerificationCodeActivity::class.java)
            startActivity(intent)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }
}