package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.R

class VerificationCodeActivity : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        continueBtn=findViewById(R.id.continueBtn)
        toolbar=findViewById(R.id.toolbar)

        continueBtn.setOnClickListener {
            val intent= Intent(this, NewPasswordActivity::class.java)
            startActivity(intent)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}