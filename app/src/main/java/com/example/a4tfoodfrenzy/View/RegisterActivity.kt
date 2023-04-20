package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity() {
    private lateinit var toolbarRegister: Toolbar
    private lateinit var auth: FirebaseAuth
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var createBtn: Button
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initViews()
        initListeners()
    }

    private fun initViews() {
        auth = Firebase.auth
        db = Firebase.firestore
        toolbarRegister = findViewById(R.id.toolbarRegister)
        nameInput = findViewById(R.id.name)
        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        createBtn = findViewById(R.id.btnCreateAccount)
    }

    private fun initListeners() {
        toolbarRegister.setNavigationOnClickListener {
            finish()
        }
        createBtn.setOnClickListener {
            createAccount()
        }
    }


    private fun createAccount() {
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profile = User(1, email, name, null, "", R.drawable.defaultavt, arrayListOf(), arrayListOf(), arrayListOf())
                    if (user != null) {
                        writeUserProfileToFirestore(user.uid, profile)
                    }
                    showSuccessMessage()
                } else {
                    showErrorMessage()
                }
            }
    }

    private fun writeUserProfileToFirestore(userId: String, profile: User) {
        val db = Firebase.firestore
        db.collection("users").document(userId)
            .set(profile)
            .addOnSuccessListener {
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                Log.d("hihi", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("hihi", "Error writing document", e)
            }
    }

    private fun showSuccessMessage() {
        Toast.makeText(this, "Tạo thành công", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage() {
        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }

}
