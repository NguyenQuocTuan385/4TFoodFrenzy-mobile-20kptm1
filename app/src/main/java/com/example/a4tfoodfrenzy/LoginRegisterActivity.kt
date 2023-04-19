package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginRegisterActivity : AppCompatActivity() {
    private lateinit var loginMail:FloatingActionButton
    private lateinit var registerMail:TextView
    private lateinit var closeBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        //init
        loginMail=findViewById(R.id.btnMail)
        closeBtn=findViewById(R.id.closeBtn)
        registerMail=findViewById(R.id.registerMail)

        setMailBtn()
        registerByMail()
        setCloseBtn()



    }
    private fun setMailBtn()
    {
        loginMail.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
   }
    }

    private fun registerByMail()
    {
        registerMail.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCloseBtn()
    {
        closeBtn.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}