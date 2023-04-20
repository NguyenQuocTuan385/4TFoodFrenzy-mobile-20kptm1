package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.R

class LoginAdminActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        findViewById<Button>(R.id.btnLoginAdmin).setOnClickListener {
            val intent = Intent(this, AdminRecipeManagementActivity::class.java)
            startActivity(intent)
        }
    }
}