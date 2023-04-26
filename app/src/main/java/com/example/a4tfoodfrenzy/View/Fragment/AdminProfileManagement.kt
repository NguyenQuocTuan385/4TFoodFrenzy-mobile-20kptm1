package com.example.a4tfoodfrenzy.View.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.UserAdapter
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList


class AdminProfileManagement : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_profile_management, container, false)
        val list= arrayListOf<User>()
        list.add(
            User(1,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        list.add(
            User(2,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        list.add(
            User(3,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        list.add(
            User(4,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        list.add(
            User(5,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        list.add(
            User(6,"","Dương Chí Thông", Date(1997),"",
                "avt", ArrayList(), ArrayList(), ArrayList(), false)
        )
        val adapter= UserAdapter(requireContext(),list)
        val recyclerView= view?.findViewById<RecyclerView>(R.id.list_user)
        recyclerView?.adapter=adapter
        recyclerView?.layoutManager=GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
        return view
    }

}