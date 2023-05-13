package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Model.DBManagement
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
    private lateinit var pDialog:SweetAlertDialog
    private var error:String?=null
    private lateinit var btnGoogle:Button
    private lateinit var btnFacebook:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        auth = Firebase.auth
        toolbarLogin = findViewById(R.id.toolbarLogin)
        forgotPasswordText = findViewById(R.id.forgotPasswordText)
        loginBtn = findViewById(R.id.loginBtn)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)



        toolbarLogin.setNavigationOnClickListener {
            finish()
        }

        forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        emailFocusListener()
        loginByEmail()
        loginByFacebook()
        loginByGoogle()

    }
    private fun loginByFacebook()
    {
        val intent=Intent(this,FacebookAuthenticateActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
    }
    private fun loginByGoogle()
    {
        val intent = Intent(this, GoogleAuthenticateActivity::class.java)

        startActivity(intent)
    }
    private fun loginByEmail() {
        loginBtn.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            val password = passwordInput.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showErrorAlert("Bạn chưa nhập email hoặc mật khẩu")
                return@setOnClickListener
            }
            if (!error.isNullOrEmpty()) {
                showErrorAlert(error!!)
                error = null
                return@setOnClickListener
            }
            showLoadingAlert()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    stopLoadingAlert()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        DBManagement.addListenerChangeDataUserCurrent { user ->
                            if (user.isAdmin) {
                                val intent = Intent(this, AdminDashboard::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                if (auth.currentUser!!.isEmailVerified) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                } else {
                                    showErrorAlert("Bạn chưa xác thực email")
                                }
                            }
                        }
                    } else {
                        showErrorAlert("Thất bại")
                    }
                }

        }
    }
    private fun emailFocusListener()
    {
        emailInput.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                error=validEmail()
                emailInput.helperText = error
            }
        }


    }
    private fun checkEmailExistence(email: String): Boolean {
        for (user in DBManagement.userList) {
            if (user.email == email) {
                return true
            }
        }
        return false
    }

    private fun validEmail():String?{
        val emailText=emailInput.editText!!.text.toString()
        if(emailText.isBlank())
            return null
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Email không hợp lệ"
        }
        if (!checkEmailExistence(emailText)) {
            return "Email không tồn tại"
        }
        return null
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
    private fun showErrorAlert(title:String) {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        sweetAlertDialog.setTitleText(title)
        sweetAlertDialog.setContentText("Bạn vui lòng đăng nhập lại")
        sweetAlertDialog.setConfirmButton("OK") {
            it.dismissWithAnimation()
        }
        sweetAlertDialog.show()
    }
}