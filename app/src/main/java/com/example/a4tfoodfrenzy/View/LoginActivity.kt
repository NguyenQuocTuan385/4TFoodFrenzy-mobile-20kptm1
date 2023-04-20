package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var toolbarLogin:Toolbar
    private lateinit var forgotPasswordText:TextView
    private lateinit var loginBtn: Button
    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        auth = Firebase.auth
        toolbarLogin=findViewById(R.id.toolbarLogin)
        forgotPasswordText=findViewById(R.id.forgotPasswordText)
        loginBtn=findViewById(R.id.loginBtn)
        emailInput=findViewById(R.id.emailInput)
        passwordInput=findViewById(R.id.passwordInput)


        toolbarLogin.setNavigationOnClickListener {
            finish()
        }

        forgotPasswordText.setOnClickListener {
            val intent= Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            val email= emailInput.editText?.text.toString()
            val password= passwordInput.editText?.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}