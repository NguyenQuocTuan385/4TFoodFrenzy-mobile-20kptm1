package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class LoginActivity : AppCompatActivity() {
    private lateinit var toolbarLogin:Toolbar
    private lateinit var forgotPasswordText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        toolbarLogin=findViewById(R.id.toolbarLogin)
        forgotPasswordText=findViewById(R.id.forgotPasswordText)

        toolbarLogin.setNavigationOnClickListener {
            finish()
        }

        forgotPasswordText.setOnClickListener {
            val intent= Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}