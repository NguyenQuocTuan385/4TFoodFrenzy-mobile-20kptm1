package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.Fragment.AdminCommentManagement
import com.example.a4tfoodfrenzy.View.Fragment.AdminProfileManagement
import com.example.a4tfoodfrenzy.View.Fragment.AdminRecipeManagement
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdminDashboard : AppCompatActivity() {

    private val FRAGMENT_PROFILE=0
    private val FRAGMENT_COMMENT=1
    private val FRAGMENT_RECIPE=2

    private var currentFragment=FRAGMENT_PROFILE


    private lateinit var toolBar:Toolbar
    private lateinit var navigationView:NavigationView
    private lateinit var drawLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        init()
        setNavigationview()
        replaceFragment(AdminProfileManagement())
        toolBar.setNavigationOnClickListener {
            drawLayout.openDrawer(GravityCompat.START)

        }
        navigationView.itemIconTintList=null



    }
    private fun init()
    {
        toolBar=findViewById(R.id.toolBar)
        navigationView=findViewById(R.id.navigationView)
        drawLayout=findViewById(R.id.drawlayout)

    }
    private fun setNavigationview()
    {
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                // Xử lý sự kiện click vào một mục trong NavigationView
                when (menuItem.itemId) {
                    R.id.navUser -> {
                        if(currentFragment!=FRAGMENT_PROFILE) {
                            toolBar.title="Quản lý tài khoản"
                            navigationView.setCheckedItem(R.id.navUser)
                            replaceFragment(AdminProfileManagement())
                            currentFragment=FRAGMENT_PROFILE
                        }
                    }
                    R.id.navComment -> {
                        if(currentFragment!=FRAGMENT_COMMENT) {
                            toolBar.title="Quản lý bình luận"
                            navigationView.setCheckedItem(R.id.navUser)
                            replaceFragment(AdminCommentManagement())
                            currentFragment=FRAGMENT_COMMENT
                        }

                    }
                    R.id.navRecipe ->{
                        if(currentFragment!=FRAGMENT_RECIPE) {
                            toolBar.title="Quản lý công thức"
                            navigationView.setCheckedItem(R.id.navUser)
                            replaceFragment(AdminRecipeManagement())
                            currentFragment=FRAGMENT_RECIPE
                        }
                    }
                    R.id.navLogout->{
                        val auth = Firebase.auth
                        auth.signOut()
                        navigateToLogin()




                    }
                }
                // Đóng DrawerLayout khi người dùng chọn một mục
                drawLayout.closeDrawer(GravityCompat.START)

                return true
            }
        })
    }

    override fun onBackPressed() {
        if(drawLayout.isDrawerOpen(GravityCompat.START))
        {
            drawLayout.closeDrawer(GravityCompat.START)

        }
        else
            super.onBackPressed()
    }
    private fun replaceFragment(fragment:Fragment)
    {
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame,fragment)
        transaction.commit() // thêm dòng này
    }
    private fun navigateToLogin()
    {
        val intent= Intent(this, LoginRegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}