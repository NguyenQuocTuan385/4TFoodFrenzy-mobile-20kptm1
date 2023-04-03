package com.example.a4tfoodfrenzy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
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

    }
}