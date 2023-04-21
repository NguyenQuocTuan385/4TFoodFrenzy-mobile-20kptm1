package com.example.a4tfoodfrenzy.View

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
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
    private lateinit var pDialog:SweetAlertDialog

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
        pDialog=  SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
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
        showLoadingAlert()
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profile = User(1, email, name, null, "", "defaultavt", arrayListOf(), arrayListOf(), arrayListOf())
                    if (user != null) {
                        writeUserProfileToFirestore(user.uid, profile)
                    }
                } else {
                    stopLoadingAlert()
                    showErrorAlert()
                }
            }
    }

    private fun writeUserProfileToFirestore(userId: String, profile: User) {
        val db = Firebase.firestore
        db.collection("users").document(userId)
            .set(profile)
            .addOnSuccessListener {
                stopLoadingAlert()
                showSuccessAlert()
                Log.d("hihi", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                stopLoadingAlert()
                showErrorAlert()
                Log.w("hihi", "Error writing document", e)
            }
    }


    private fun showSuccessAlert() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        sweetAlertDialog.setTitleText("Thành công")
        sweetAlertDialog.setContentText("Bạn đã đăng ký thành công")
        sweetAlertDialog.setConfirmButton("OK") {
            it.dismissWithAnimation()
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        sweetAlertDialog.setCancelable(false)
        sweetAlertDialog.show()
    }
    private fun showErrorAlert() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        sweetAlertDialog.setTitleText("Thất bại")
        sweetAlertDialog.setContentText("Bạn vui lòng thực hiện lại")
        sweetAlertDialog.setConfirmButton("OK") {
            it.dismissWithAnimation()
        }
        sweetAlertDialog.show()
    }
    private fun showLoadingAlert()
    {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#FFB200")
        pDialog.titleText = "Vui lòng đợi..."
        pDialog.setCancelable(false)
        pDialog.show()
    }
    private fun stopLoadingAlert()
    {
        pDialog.cancel()
    }

}
