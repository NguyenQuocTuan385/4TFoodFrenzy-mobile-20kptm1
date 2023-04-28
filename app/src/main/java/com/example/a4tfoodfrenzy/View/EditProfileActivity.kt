package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
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
    private lateinit var datePicker: EditText
    private lateinit var Dob: Date
    private lateinit var emailET: EditText
    private lateinit var bioET: EditText
    val user_current = DBManagement.user_current

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
        // chọn ảnh
        updateAvt = findViewById(R.id.updateAvt)
        updateAvt.setOnClickListener {
            pickImage()
        }

        // cập nhật thông tin
        updateBtn = findViewById(R.id.updateBtn)
        updateBtn.setOnClickListener {
            val helperFunctionDB= HelperFunctionDB(this)
            helperFunctionDB.showLoadingAlert()
            updateProfile()
            helperFunctionDB.stopLoadingAlert()
            val intent = Intent(this, ProfileActivity::class.java)
            // gửi ảnh đến profile
            intent.putExtra("urlAvt", urlAvt)
            setResult(RESULT_OK, intent)
            finish()
        }

        // quay lại
        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }

        // chọn ngày sinh
        datePicker = findViewById(R.id.Dob_profile)
        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    datePicker.setText("$dayOfMonth/${month + 1}/$year")
                    Dob = Date(year, month, dayOfMonth)
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun updateProfile() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val user_id = user?.uid
        val docRef = db.collection("users").document(user_id.toString())

        val name = nameET.text.toString()
        val bio = bioET.text.toString()

        var user1 = mapOf(
            "fullname" to name,
            "birthday" to Dob,
            "bio" to bio
        )
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
                   user1 = mapOf(
                       "fullname" to name,
                       "birthday" to Dob,
                       "bio" to bio,
                       "avatar" to "users/${user_id}"
                   )
                   docRef.update(user1)
                       .addOnSuccessListener {
                           Log.d("TAG", "DocumentSnapshot successfully updated!")
                       }
                       .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
               }
              Log.d("TAG", "Upload ảnh thành công")
           }
       }else{
           docRef.update(user1)
               .addOnSuccessListener {
                   Log.d("TAG", "DocumentSnapshot successfully updated!")
               }
               .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
       }
   }

    private fun viewProfile() {
        name = findViewById(R.id.name_profile)
        name.text = user_current?.fullname ?: ""

        avatar = findViewById(R.id.creatorImage)
        val imageRef = user_current?.avatar?.let { storageRef.getReference(it) }
        if (imageRef != null) {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(avatar)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }
        nameET = findViewById(R.id.editTextPersonName)
        nameET.setText(user_current?.fullname)
        Dob = user_current?.birthday ?: Date()
        if(user_current?.birthday != null){
            datePicker = findViewById(R.id.Dob_profile)
            val year = user_current?.birthday.toString().split(" ")[5].toInt() - 1900
            val month = when (user_current?.birthday.toString().split(" ")[1]) {
                "Jan" -> 1
                "Feb" -> 2
                "Mar" -> 3
                "Apr" -> 4
                "May" -> 5
                "Jun" -> 6
                "Jul" -> 7
                "Aug" -> 8
                "Sep" -> 9
                "Oct" -> 10
                "Nov" -> 11
                else -> 12
            }
            val date = user_current?.birthday.toString().split(" ")[2] + "/" + month + "/" + year
            datePicker.setText(date)
        }

        emailET = findViewById(R.id.editTextEmail)
        emailET.setText(user_current?.email)
        bioET = findViewById(R.id.editTextBio)
        bioET.setText(user_current?.bio)
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