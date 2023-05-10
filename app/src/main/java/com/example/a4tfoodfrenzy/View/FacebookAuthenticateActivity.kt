package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
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
import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FacebookAuthenticateActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private val storageRef = Firebase.storage

    // ...
// Initialize Firebase Auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


                    // check email exist in firestore users collection
                    db.collection("users")
                        .whereEqualTo("email", userEmail)
                        .get()
                        .addOnSuccessListener {
                            // user not exist in DB --> add user
                            if (it.isEmpty) {
                                // get user name from google's database
                                val userFullName = user?.displayName
                                val helperFunctionDB = HelperFunctionDB(this)

                                helperFunctionDB.findSlotIdEmptyInCollection("users") { idSlot ->
                                    // generate url for user avatar getting from facebook
                                    val fbAvatar = "fb_${idSlot}"
                                    val avtImageUrl = "${auth.currentUser?.photoUrl}?access_token=${token.token}"

                                    val profile = User(
                                        idSlot,
                                        userEmail!!,
                                        userFullName!!,
                                        null,
                                        "",
                                        "users/${fbAvatar}",
                                        arrayListOf(),
                                        arrayListOf(),
                                        arrayListOf(), false
                                    )

                                    // async --> run on another thread except main thread
                                    Thread {
                                        // upload facebook avatar to storage on first time login
                                        UploadAvatarImageToStorage(avtImageUrl, fbAvatar)
                                        runOnUiThread {
                                        }
                                    }.start()

                                    // add new user to db
                                    db.collection("users").document(user.uid)
                                        .set(profile)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Đăng ký bằng Facebook thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()

//                                            // assign current user with the new facebook login user
//                                            DBManagement.user_current =
//                                                DBManagement.userList.filter { u -> u.id == idSlot }[0]
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Đăng ký bằng Facebook không thành công",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            } else {
//                                // user exist => find user in userlist and assign to current user
//                                DBManagement.user_current = DBManagement.userList.filter { u ->
//                                    u.id == it.documents[0].get("id")
//                                }[0]
                            }
                            // user exist in DB --> do nothing
                        }
                        .addOnFailureListener { exception -> }

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
                            "Vui lòng đăng nhập bằng Google với địa chỉ email đã đăng ký Facebook",
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

    private fun UploadAvatarImageToStorage(path: String?, avtName: String){
        var inStream: InputStream? = null
        var responseCode = -1
        var res : InputStream? = null

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
                val uploadTask =  imageRef.putStream(inStream)

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