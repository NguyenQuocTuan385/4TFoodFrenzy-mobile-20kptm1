package com.example.a4tfoodfrenzy.Controller.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Controller.Fragment.UserFragment
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AccountDetailsManagementActivity: AppCompatActivity() {
    private lateinit var user: User
    private lateinit var updateAvt: ImageView
    private lateinit var updateBtn: Button
    private lateinit var backBtn: Toolbar
    private lateinit var nameET: EditText
    private lateinit var datePicker: EditText
    private lateinit var DoB: Date
    private lateinit var emailET: EditText
    private lateinit var bioET: EditText
    private lateinit var checkBoxAdmin: CheckBox
    private lateinit var db: FirebaseFirestore
    val storageRef = FirebaseStorage.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details_management)

        viewProfile()
        event()
    }


    private fun updateEvent() {
        // update button
        val nameUpdate = nameET.text.toString()
        val bioUpdate = bioET.text.toString()

        var mapUpdate = mapOf(
            "fullname" to nameUpdate,
            "birthday" to DoB,
            "bio" to bioUpdate,
            "admin" to checkBoxAdmin.isChecked
        )



        db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("id", user.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("users").document(document.id)
                        .update(mapUpdate)
                        .addOnSuccessListener {
                            val intent = Intent(this, AdminDashboard::class.java)
                            startActivity(intent)
                            Log.d("MAPPPPPPPPPPP", mapUpdate.toString())
                        }
                }

        }
    }

    private fun event() {
        // back toolbar
        backBtn.setNavigationOnClickListener {
            finish()
        }

        // chọn ngày sinh
        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    datePicker.setText("$dayOfMonth/${month + 1}/$year")
                    DoB = Date(year, month, dayOfMonth)
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }

        // update button
        updateBtn.setOnClickListener {
            val helperFunctionDB= HelperFunctionDB(this)
            helperFunctionDB.showLoadingAlert()
            updateEvent()
            helperFunctionDB.stopLoadingAlert()

        }

    }

    private fun viewProfile() {
        // get extra from intent
        user = intent.getParcelableExtra("user")!!

        // hiển thị thông tin
        updateAvt = findViewById(R.id.updateAvt)
        val imageRef = user?.avatar?.let { storageRef.getReference(it) }
        if(imageRef != null){
            imageRef.downloadUrl.addOnSuccessListener {
                Glide.with(this)
                    .load(it)
                    .into(updateAvt)
            }
        }

        nameET = findViewById(R.id.editTextPersonName)
        nameET.setText(user.fullname)

//        val myFormat = "dd-MM-yyyy"
//        val sdf = SimpleDateFormat(myFormat, Locale.UK)
//        if(user.birthday != null){
//            datePicker = findViewById(R.id.Dob_profile)
//            datePicker.setText(sdf.format(user.birthday))
//            DoB = user.birthday!!
//        }
        

        DoB = user?.birthday ?: Date()
        datePicker = findViewById(R.id.Dob_profile)
        if(user.birthday != null){
            val year = user.birthday.toString().split(" ")[5].toInt() - 1900
            val month = when (user.birthday.toString().split(" ")[1]) {
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
            val date = user.birthday.toString().split(" ")[2] + "/" + month + "/" + year
            datePicker.setText(date)
        }

        emailET = findViewById(R.id.editTextEmail)
        emailET.setText(user.email)

        bioET = findViewById(R.id.editTextBio)
        bioET.setText(user.bio)

        checkBoxAdmin = findViewById(R.id.checkAdmin)
        checkBoxAdmin.isChecked = user.isAdmin

        backBtn = findViewById(R.id.toolbar3)
        updateBtn = findViewById(R.id.buttonUpdate)
    }
}