package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class GoogleAuthenticateActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    val storageRef = FirebaseStorage.getInstance()
    private var pDialog: SweetAlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_authenticate)

        showLoadingAlert()

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
                                    endLoadingAlert()

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
                                                val helperFunctionDB = HelperFunctionDB(this)

                                                // get avatar url from google
                                                val userAvatarUrl =
                                                    firebaseAuth.currentUser?.photoUrl.toString()

                                                // upload avatar to firebase storage
                                                val storageRef = Firebase.storage.reference
                                                val user_id = firebaseAuth.currentUser?.uid
                                                val avatarRef = "$user_id"

                                                helperFunctionDB.findSlotIdEmptyInCollection(
                                                    "users"
                                                ) { idSlot ->
                                                    val profile = User(
                                                        idSlot, userEmail!!,
                                                        userFullName!!,
                                                        null,
                                                        "",
                                                        "users/$user_id",
                                                        arrayListOf(),
                                                        arrayListOf(),
                                                        arrayListOf(), false
                                                    )

                                                    // async --> run on another thread except main thread
                                                    Thread {
                                                        // upload facebook avatar to storage on first time login
                                                        uploadAvatarImageToStorage(
                                                            userAvatarUrl,
                                                            avatarRef
                                                        )
                                                        runOnUiThread {
                                                        }
                                                    }.start()

                                                    if (user_id != null) {
                                                        db.collection("users")
                                                            .document(user_id)
                                                            .set(profile)
                                                            .addOnSuccessListener {
                                                                Log.d(
                                                                    "addUser",
                                                                    "Add user success"
                                                                )
                                                            }
                                                            .addOnFailureListener {
                                                                Log.d(
                                                                    "addUser",
                                                                    "Add user failed"
                                                                )
                                                            }
                                                    }

                                                    DBManagement.user_current =
                                                        DBManagement.userList.find { user -> user.id == idSlot }
                                                }
                                            } else { // user exist in DB
                                                DBManagement.user_current =
                                                    DBManagement.userList.find { user ->
                                                        user.id == it.documents[0].get("id")
                                                    }

                                                if (DBManagement.user_current!!.isAdmin) {
                                                    // Display Toast
                                                    Toast.makeText(
                                                        this,
                                                        "Đăng nhập bằng Google thành công",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    val intent =
                                                        Intent(this, AdminDashboard::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            }

                                            // When task is successful redirect to profile activity
                                            // Display Toast
                                            Toast.makeText(
                                                this,
                                                "Đăng nhập bằng Google thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            // success --> load home
                                            val toHomeIntent =
                                                Intent(this, MainActivity::class.java)

                                            // remove all previous intent (activity)
                                            toHomeIntent.flags =
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(toHomeIntent)
                                        }
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

    private fun showLoadingAlert() {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#FFB200")
        pDialog!!.titleText = "Vui lòng đợi..."
        pDialog!!.setCancelable(false)
        pDialog!!.show()
    }

    private fun endLoadingAlert() {
        pDialog!!.dismiss()
    }

    private fun uploadAvatarImageToStorage(path: String?, avtName: String) {
        var inStream: InputStream? = null
        var responseCode = -1

        // download image from url provided by access token of Facebook
        try {
            val url = URL(path)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection

            con.doInput = true
            con.connect()
            responseCode = con.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // download
                inStream = con.inputStream

                // upload downloaded image to firebase storage
                val imageRef = storageRef.reference.child("users/${avtName}")
                val uploadTask = imageRef.putStream(inStream)

                uploadTask
                    .addOnSuccessListener {
                        inStream.close()
                    }
                    .addOnFailureListener {
                        inStream.close()
                    }

            }
        } catch (ex: Exception) {
            Log.e("Exception", ex.toString())
        }
    }
}