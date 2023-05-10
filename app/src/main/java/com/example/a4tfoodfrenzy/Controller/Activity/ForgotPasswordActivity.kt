package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var continueBtn:Button
    private lateinit var toolbar: Toolbar
    private lateinit var email:EditText
    private var error:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        continueBtn=findViewById(R.id.continueBtn)
        toolbar=findViewById(R.id.toolbar)
        email=findViewById(R.id.email)

        continueBtn.setOnClickListener {
            error=checkEmail()
            if(!error.isNullOrEmpty())
            {
                email.setError(error)
                error=null
                return@setOnClickListener
            }
           forgotPassword()
        }

        toolbar.setNavigationOnClickListener {
            finish()
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
    private fun checkEmail():String?
    {
        val emailText=email.text.toString()
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
    private fun forgotPassword()
    {
        val auth=FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener{task ->
                if(task.isSuccessful)
                {
                   val sweetAlertDialog=SweetAlertDialog(this)
                    sweetAlertDialog.contentText="Chúng tôi vừa gửi một liên kết khôi phục lại mật khẩu đến email của bạn"
                    sweetAlertDialog.setConfirmButton("OK"){
                        it.dismiss()
                        val intent= Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    sweetAlertDialog.show()
                }
            }
    }
}