package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FacebookAuthenticateActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager

    private lateinit var auth: FirebaseAuth

    // ...
// Initialize Firebase Auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_authenticate)

        auth = Firebase.auth

        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)



        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    // get current user's email
                    val userEmail = user?.email
                    val db = Firebase.firestore

                    db.collection("users")
                        .whereEqualTo("email", userEmail)
                        .get()
                        .addOnSuccessListener {
                            // user not exist in DB --> add user
                            if (it.isEmpty) {
                                // get user name from google's database
                                val userFullName = user?.displayName
                                val helperFunctionDB = HelperFunctionDB(this)
                                helperFunctionDB.findSlotIdEmptyInCollection("users") {idSlot ->
                                    val profile = User(
                                        idSlot,
                                        userEmail!!,
                                        userFullName!!,
                                        null,
                                        "",
                                        "users/defaultavt.png",
                                        arrayListOf(),
                                        arrayListOf(),
                                        arrayListOf()
                                    )

                                    // add new user to db
                                    db.collection("users").document(user.uid)
                                        .set(profile)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Đăng ký bằng Facebook thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Đăng ký bằng Facebook không thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                            // user exist in DB --> do nothing
                        }
                        .addOnFailureListener { exception ->
                        }

                    // display success message
                    Toast.makeText(
                        baseContext, "Đăng nhập bằng Facebook thành công",
                        Toast.LENGTH_SHORT
                    ).show()

                    // success --> to home
                    val toHomeIntent = Intent(this, MainActivity::class.java)

                    // remove all previous intent (activity)
                    toHomeIntent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(toHomeIntent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Đăng nhập bằng Facebook thất bại",
                        Toast.LENGTH_SHORT
                    ).show()

                    // email registered with a different provider
                    if (task.exception is FirebaseAuthUserCollisionException)
                        Toast.makeText(
                            baseContext,
                            "Vui lòng đăng nhập bằng Google",
                            Toast.LENGTH_LONG
                        ).show()

                    // failed --> back to sign in
                    val toLoginPage = Intent(this, LoginRegisterActivity::class.java)

                    // remove all previous intent (activity)
                    toLoginPage.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(toLoginPage)
                }
            }
    }

    private fun isSignUp() {}
}