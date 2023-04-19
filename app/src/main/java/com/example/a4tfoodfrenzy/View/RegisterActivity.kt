package com.example.a4tfoodfrenzy.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var toolbarRegister: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //init
        toolbarRegister=findViewById(R.id.toolbarRegister)

        toolbarRegister.setNavigationOnClickListener {
            finish()
        }
    }
}