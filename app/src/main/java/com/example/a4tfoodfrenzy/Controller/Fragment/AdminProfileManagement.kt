package com.example.a4tfoodfrenzy.Controller.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.a4tfoodfrenzy.Adapter.UserAdapter.UserAdapter
import com.example.a4tfoodfrenzy.Adapter.UserAdapter.ViewPagerAdapter
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.Controller.Activity.AccountDetailsManagementActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class AdminProfileManagement : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_profile_management, container, false)
        var tabLayout=view.findViewById<TabLayout>(R.id.tabLayout)
        var viewPager=view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(requireContext(), childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        return view
    }


}