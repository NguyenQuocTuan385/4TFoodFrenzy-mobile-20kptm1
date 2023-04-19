package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.R

class NewPasswordActivity : AppCompatActivity() {
    private lateinit var finishBtn: Button
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        finishBtn=findViewById(R.id.finishBtn)
        toolbar=findViewById(R.id.toolbar)

        finishBtn.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }


    }
}