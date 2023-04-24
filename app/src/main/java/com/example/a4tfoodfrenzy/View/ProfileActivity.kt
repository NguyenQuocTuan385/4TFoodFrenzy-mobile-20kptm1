package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Adapter.TabFoodRecipeSaved
import com.example.a4tfoodfrenzy.Adapter.TabMyFoodRecipe
import com.example.a4tfoodfrenzy.Adapter.TabProfileAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var name_profile: TextView
    private lateinit var avatar_profile: ImageView
    private lateinit var email_profile: TextView
    private lateinit var tabAdapter: TabProfileAdapter
    private lateinit var view_pager: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var option_adapter: ImageView
    private lateinit var list_option: List<String>
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var menu: Menu
    private lateinit var progressDialog: ProgressDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val storageRef = FirebaseStorage.getInstance()
    val dbManagement = DBManagement()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        viewProfile()

        tabProfile()
        optionProfile()
        bottomNavigation()
    }

    private fun viewProfile() {
        val user = auth.currentUser
        if(user==null)
        {
            val intent= Intent(this,LogoutActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
//            DBManagement().addListenerChangeDataUserCurrent {  }
            val user_current = DBManagement.user_current
            name_profile = findViewById(R.id.name_profile)
            avatar_profile = findViewById(R.id.creatorImage)
            email_profile = findViewById(R.id.email_profile)

            name_profile.text = user_current?.fullname ?: ""
            email_profile.text = user_current?.email ?: ""
            val imageRef = user_current?.avatar?.let { storageRef.getReference(it) }
            if (imageRef != null) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(this)
                        .load(uri)
                        .into(avatar_profile)
                }.addOnFailureListener { exception ->
                    // Xử lý lỗi
                }
            }
        }
    }

    private fun tabProfile() {
        tabAdapter = TabProfileAdapter(this, supportFragmentManager)
        tabAdapter.addFragment(TabFoodRecipeSaved(this), "Món đã lưu")
        tabAdapter.addFragment(TabMyFoodRecipe(this), "Món của tôi")
        view_pager = findViewById(R.id.view_pager)
        view_pager.adapter = tabAdapter
        tabs = findViewById(R.id.tab_layout)
        tabs.setupWithViewPager(view_pager)
    }
    private fun optionProfile() {
        option_adapter = findViewById(R.id.option_profile)
        list_option = listOf("Chỉnh sửa thông tin", "Đăng xuất")
        option_adapter.setOnClickListener {
            val popup = PopupMenu(this, option_adapter)
            for (i in list_option) {
                popup.menu.add(i)
            }
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Chỉnh sửa thông tin" -> {
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    "Đăng xuất" -> {
                        auth.signOut()
                        DBManagement.user_current = null

                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
    private fun bottomNavigation() {
        bottomNavigationView = findViewById(R.id.botNavbar)
        menu = bottomNavigationView.menu
        menu.findItem(R.id.profile).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    val intent = Intent(this, SearchScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }

//    override fun onResume()
//    {
//        super.onResume()
//        val user = auth.currentUser
//        if(user==null)
//        {
//            val intent= Intent(this,LogoutActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        else
//        {
//            progressDialog = ProgressDialog(this)
//            progressDialog.setTitle("Đang tải dữ liệu...")
//            progressDialog.show()
//
//            db.collection("users")
//                .document(user.uid)
//                .get()
//                .addOnSuccessListener { document ->
//                    val user = document.toObject(User::class.java)
//                    val name = findViewById<TextView>(R.id.name_profile)
//                    name.text = user?.fullname
//
//                    val avatar = findViewById<ImageView>(R.id.creatorImage)
//                    val imageRef = user?.avatar?.let { storageRef.getReference(it) }
//                    if (imageRef != null) {
//                        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
//                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                            avatar.setImageBitmap(bitmap)
//                        }.addOnFailureListener { exception ->
//                            // Xử lý ngoại lệ nếu có lỗi xảy ra
//                        }
//                    }
//
//                    val email = findViewById<TextView>(R.id.email_profile)
//                    email.text = user?.email
//                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
//
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("hihi", "Error getting documents: ", exception)
//                }
//
//            progressDialog.dismiss()
//        }
//    }
}