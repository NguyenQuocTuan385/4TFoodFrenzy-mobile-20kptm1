package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GoogleAuthenticateActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_authenticate)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient =
            GoogleSignIn.getClient(this@GoogleAuthenticateActivity, googleSignInOptions)

        val intent: Intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 100)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            val acc: GoogleSignInAccount = signInAccountTask.result

            // check condition
            if (signInAccountTask.isSuccessful) {
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                            googleSignInAccount.idToken, null
                        )

                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(this) { task ->
                                // Check condition
                                if (task.isSuccessful) {
                                    // get current user's email
                                    val userEmail = firebaseAuth.currentUser?.email
                                    val db = Firebase.firestore

                                    // query to check if email of current user exist
                                    db.collection("users")
                                        .whereEqualTo("email", userEmail)
                                        .get()
                                        .addOnSuccessListener {
                                            // user not exist in DB --> add user
                                            if (it.isEmpty) {
                                                // get user name from google's database
                                                val userFullName =
                                                    firebaseAuth.currentUser?.displayName
                                                val profile = User(
                                                    1125, userEmail!!,
                                                    userFullName!!,
                                                    null,
                                                    "",
                                                    "defaultavt",
                                                    arrayListOf(),
                                                    arrayListOf(),
                                                    arrayListOf()
                                                )

                                                // add new user to db
                                                db.collection("users")
                                                    .document(firebaseAuth.currentUser!!.uid)
                                                    .set(profile)
                                                    .addOnSuccessListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Đăng ký bằng Google thành công",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            this,
                                                            "Đăng ký bằng Google không thành công",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }
                                            // user exist in DB --> do nothing
                                        }
                                        .addOnFailureListener {
                                        }

                                    // When task is successful redirect to profile activity
                                    // Display Toast
                                    Toast.makeText(
                                        this,
                                        "Đăng nhập bằng Google thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // success --> load home
                                    val toHomeIntent = Intent(this, MainActivity::class.java)

                                    // remove all previous intent (activity)
                                    toHomeIntent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(toHomeIntent)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(
                                        baseContext, "Đăng nhập bằng Google thất bại",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // email registered with a different provider
                                    if (task.exception is FirebaseAuthUserCollisionException)
                                        Toast.makeText(
                                            baseContext,
                                            "Tài khoản email đã được đăng ký bằng một dịch vụ khác, vui lòng truy cập đúng dịch vụ mà bạn đã đăng ký",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    // failed --> back to sign in
                                    val toLoginPage =
                                        Intent(this, LoginRegisterActivity::class.java)

                                    // remove all previous intent (activity)
                                    toLoginPage.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(toLoginPage)
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
}