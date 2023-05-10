package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var toolbarRegister: Toolbar
    private lateinit var auth: FirebaseAuth
    private lateinit var nameInput: EditText
    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var confirmPasswordInput: TextInputLayout
    private lateinit var createBtn: Button
    private lateinit var db:FirebaseFirestore
    private lateinit var pDialog:SweetAlertDialog
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var confirmPassword:String


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
        confirmPasswordInput=findViewById(R.id.confirmPassword)
        createBtn = findViewById(R.id.btnCreateAccount)
        pDialog=  SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
    }
    private fun getText()
    {
        name=nameInput.text.toString()
        email=emailInput.editText!!.text.toString()
        password=passwordInput.editText!!.text.toString()
        confirmPassword=confirmPasswordInput.editText!!.text.toString()
    }

    private fun initListeners() {
        toolbarRegister.setNavigationOnClickListener {
            finish()
        }
        emailFocusListener()
        checkPassword()
        createBtn.setOnClickListener {
            getText()
            if(!validEmail().isNullOrEmpty()) {
                return@setOnClickListener
            }
            if(!password.equals(confirmPassword))
                return@setOnClickListener
            createAccount()
        }
    }


    private fun createAccount() {
        showLoadingAlert()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener {action ->
                        if(action.isSuccessful)
                        {
                            val helperFunctionDB = HelperFunctionDB(this)
                            helperFunctionDB.findSlotIdEmptyInCollection("users") {idSlot ->
                                val profile = User(idSlot, email, name, null, "", "users/defaultavt.png", arrayListOf(), arrayListOf(), arrayListOf(), false)
                                if (user != null) {
                                    writeUserProfileToFirestore(user.uid, profile)
                                }
                            }
                        }
                        else {
                            stopLoadingAlert()
                            showErrorAlert()
                        }

                    }

                } else {
                    stopLoadingAlert()
                    showErrorAlert()
                }
            }
    }

    private fun emailFocusListener()
    {
        emailInput.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                emailInput.helperText = validEmail()
            }
        }

    }
    private fun checkPassword()
    {
        passwordInput.editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                passwordInput.helperText=validPassword()
            }
        }

        confirmPasswordInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               confirmPasswordInput.helperText=confirmPassword()
            }
        })

    }

    private fun checkEmailExistence(email: String): Boolean {
        for (user in DBManagement.userList) {
            if (user.email == email) {
                return true
            }
        }
        return false
    }
    private fun validPassword():String?{
        val passwordText=passwordInput.editText!!.text.toString()
        if(passwordText.length<6)
            return "Mật khẩu phải ít nhất 6 kí tự"
        return null
    }
    private fun confirmPassword():String?{
        val confirmPasswordText=confirmPasswordInput.editText!!.text.toString()
        val passwordText=passwordInput.editText!!.text.toString()
        if(!passwordText.equals(confirmPasswordText))
        {
            return "Mật khẩu không trùng khớp"
        }
        return null
    }

    private fun validEmail():String?{
        val emailText=emailInput.editText!!.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Email không hợp lệ"
        }
        if (checkEmailExistence(emailText)) {
            return "Email đã tồn tại"
        }
        return null
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
        HelperFunctionDB(this).showSuccessAlert("Thành công","Bạn vui lòng xác thực email của bạn"){confirm ->
            if(confirm)
            {
                val intent=Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun showErrorAlert() {
       HelperFunctionDB(this).showErrorAlert("Thất bại","Vui lòng đăng ký lại"){

       }
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