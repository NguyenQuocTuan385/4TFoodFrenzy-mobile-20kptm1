package com.example.a4tfoodfrenzy.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.UserAdapter
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList

class AdminAccountManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account_management)
       val list= arrayListOf<User>()
        list.add(User(1,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(2,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(3,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(4,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(5,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(6,"","Dương Chí Thông", Date(1997),"",
            R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        val adapter= UserAdapter(this,list)
        val recyclerView=findViewById<RecyclerView>(R.id.list_user)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
    }
}