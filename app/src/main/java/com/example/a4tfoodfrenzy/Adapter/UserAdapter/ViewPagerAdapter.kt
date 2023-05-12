package com.example.a4tfoodfrenzy.Adapter.UserAdapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a4tfoodfrenzy.Controller.Fragment.UserFragment

class ViewPagerAdapter(private var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> UserFragment(false)
            1 -> UserFragment(true)
            else -> UserFragment(false)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Người dùng"
            1 -> "Quản trị viên"
            else -> null
        }
    }
}