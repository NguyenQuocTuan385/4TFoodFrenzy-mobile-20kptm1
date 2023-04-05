package com.example.a4tfoodfrenzy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class TrangCaNhan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thong_tin_tai_khoan)
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(TabFragment1(), "Món đã lưu")
        adapter.addFragment(TabFragment2(), "Món của tôi")
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
                    val intent = Intent(this, RecipeManagementActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
//                    val intent = Intent(this, TrangCaNhan::class.java)
//                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}