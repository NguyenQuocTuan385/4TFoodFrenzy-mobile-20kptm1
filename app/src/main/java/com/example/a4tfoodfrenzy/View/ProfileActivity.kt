package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.a4tfoodfrenzy.Adapter.TabFoodRecipeSaved
import com.example.a4tfoodfrenzy.Adapter.TabMyFoodRecipe
import com.example.a4tfoodfrenzy.Adapter.TabProfileAdapter
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // tạo dữ liệu mẫu
//        createDataUsers()

//        // lấy dữ liệu từ firebase
//        val db = Firebase.firestore
//        val user = db.collection("users").document("ngoctien")
//        user.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    val user = document.toObject(User::class.java)
//                    val name = findViewById<TextView>(R.id.name_profile)
//                    name.text = user?.fullname
//                    val avatar = findViewById<ImageView>(R.id.creatorImage)
//                    avatar.setImageResource(user?.avatar!!)
//                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "get failed with ", exception)
//            }


        val option_adapter = findViewById<ImageView>(R.id.option_profile)
        val list_option = listOf<String>("Chỉnh sửa thông tin", "Đăng xuất")
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
                        val intent = Intent(this, LogoutActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }


        val adapter = TabProfileAdapter(supportFragmentManager)
        adapter.addFragment(TabFoodRecipeSaved(), "Món đã lưu")
        adapter.addFragment(TabMyFoodRecipe(), "Món của tôi")
        val view_pager = findViewById<ViewPager>(R.id.view_pager)
        view_pager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        tabs.setupWithViewPager(view_pager)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu
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
}