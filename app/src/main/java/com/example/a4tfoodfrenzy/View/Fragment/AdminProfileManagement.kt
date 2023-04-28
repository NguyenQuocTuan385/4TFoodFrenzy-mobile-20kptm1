package com.example.a4tfoodfrenzy.View.Fragment

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.UserAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList


class AdminProfileManagement : Fragment() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUser:ArrayList<User>
    private lateinit var searchView:SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_profile_management, container, false)
        searchView=view.findViewById(R.id.search)
        listUser= arrayListOf<User>()
        listUser.addAll(DBManagement.userList)
        filerUsers()
        userAdapter= UserAdapter(requireContext(),listUser)
        val recyclerView= view?.findViewById<RecyclerView>(R.id.list_user)
        recyclerView?.adapter=userAdapter
        recyclerView?.layoutManager=GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
        return view
    }

    private fun filerUsers() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle submit event if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredList = ArrayList<User>()
                    for (user in listUser) {
                        if (user.fullname.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) ||
                            user.email.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))) {
                            filteredList.add(user)
                        }
                    }
                    userAdapter.filterList(filteredList)
                }
                return true
            }
        })

    }


}