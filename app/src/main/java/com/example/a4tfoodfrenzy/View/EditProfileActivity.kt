package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var updateAvt: ImageView
    private lateinit var updateBtn: Button
    private lateinit var backBtn: ImageView
    private lateinit var name: TextView
    private lateinit var avatar: ImageView
    private var urlAvt = ""
    private lateinit var nameET: EditText
    private lateinit var emailET: EditText
    private lateinit var bioET: EditText

    val storageRef = FirebaseStorage.getInstance()

    private lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        viewProfile()
        event()
    }

    private fun event() {
        updateAvt = findViewById(R.id.updateAvt)
        updateAvt.setOnClickListener {
            pickImage()
        }

        updateBtn = findViewById(R.id.updateBtn)
        updateBtn.setOnClickListener {
            updateProfile()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateProfile() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Đang cập nhật...")
        progressDialog.show()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val user_id = user?.uid
        val docRef = db.collection("users").document(user_id.toString())

        val name = nameET.text.toString()

       if(urlAvt != "")
       {
           // upload ảnh lên firebase storage
           val imageRef = storageRef.reference.child("users/${user_id}")
           val uploadTask = imageRef.putFile(urlAvt.toUri())

           uploadTask.addOnFailureListener {
              Log.d("TAG", "Upload ảnh thất bại")
           }.addOnSuccessListener { taskSnapshot ->
               imageRef.downloadUrl.addOnSuccessListener { uri ->
                   urlAvt = uri.toString()
               }
              Log.d("TAG", "Upload ảnh thành công")
           }
       }

        val email = emailET.text.toString()
        val bio = bioET.text.toString()
        val user1 = mapOf(
            "fullname" to name,
            "avatar" to "users/${user_id}",
            "email" to email,
            "bio" to bio
        )
        docRef.update(user1)
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
   }

    private fun viewProfile() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Đang tải...")
        progressDialog.show()

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val user_id = user?.uid
        val docRef = db.collection("users").document(user_id.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    name = findViewById(R.id.name_profile)
                    name.text = user?.fullname
                    avatar = findViewById(R.id.creatorImage)
                    val imageRef = user?.avatar?.let { storageRef.getReference(it) }
                    if (imageRef != null) {
                        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            avatar.setImageBitmap(bitmap)
                        }.addOnFailureListener { exception ->
                            // Xử lý ngoại lệ nếu có lỗi xảy ra
                        }
                    }
                    nameET = findViewById(R.id.editTextPersonName)
                    nameET.setText(user?.fullname)
                    emailET = findViewById(R.id.editTextEmail)
                    emailET.setText(user?.email)
                    bioET = findViewById<EditText>(R.id.editTextBio)
                    bioET.setText(user?.bio)

                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        if(progressDialog.isShowing)
        {
            progressDialog.dismiss()
        }
    }

    private fun pickImage()
    {
        val intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type="image/*"
        startActivityForResult(intent, AddRecipeActivity1.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== AddRecipeActivity1.IMAGE_REQUEST_CODE &&resultCode== RESULT_OK)
        {
            avatar.setImageURI(data?.data)
            urlAvt = data?.data.toString()
        }
    }
}