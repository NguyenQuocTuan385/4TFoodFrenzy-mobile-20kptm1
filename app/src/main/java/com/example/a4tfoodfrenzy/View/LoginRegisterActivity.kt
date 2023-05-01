package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class LoginRegisterActivity : AppCompatActivity() {
    private lateinit var loginMail:FloatingActionButton
    private lateinit var registerMail:Button
    private lateinit var closeBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        val fbButton : FloatingActionButton = findViewById(R.id.btnFacebook)
        val googleButton : Button = findViewById(R.id.btnGoogle)

        //init
        loginMail=findViewById(R.id.btnMail)
        closeBtn=findViewById(R.id.closeBtn)
        registerMail=findViewById(R.id.registerMail)

        changeColorText()
        setMailBtn()
        registerByMail()
        setCloseBtn()

        fbButton.setOnClickListener {
            val intent = Intent(this, FacebookAuthenticateActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }

        googleButton.setOnClickListener {
            val intent = Intent(this, GoogleAuthenticateActivity::class.java)

            startActivity(intent)
        }
    }
    private fun setMailBtn()
    {
        loginMail.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
   }
    }

    private fun registerByMail()
    {
        registerMail.setOnClickListener {
            val intent=Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCloseBtn()
    {
        closeBtn.setOnClickListener {
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun changeColorText()
    {
        var textView=findViewById<TextView>(R.id.information)
        val text =
            "Khi đăng ký, tức là bạn đồng ý với Điều Khoản Dịch Vụ và Chính Sách Bảo Mật Thông Tin của 4TFoodFrenzy"
        val spannable: Spannable = SpannableString(text)
        var startIndex = text.indexOf("Điều Khoản Dịch Vụ")
        var endIndex = startIndex + "Điều Khoản Dịch Vụ".length
        spannable.setSpan(
            ForegroundColorSpan(0xFFFDBF38.toInt()),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        startIndex = text.indexOf("Chính Sách Bảo Mật")
        endIndex = startIndex + "Chính Sách Bảo Mật".length
        spannable.setSpan(
            ForegroundColorSpan(0xFFFDBF38.toInt()),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(UnderlineSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannable

    }
}