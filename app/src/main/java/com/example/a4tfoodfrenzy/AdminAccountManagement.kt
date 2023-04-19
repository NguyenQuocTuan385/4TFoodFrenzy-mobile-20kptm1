package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.User
import java.util.*
import kotlin.collections.ArrayList

class AdminAccountManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account_management)
       val list= arrayListOf<User>()
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        list.add(User(1,"","","Dương Chí Thông", Date(1997),"","",R.drawable.avt, ArrayList(), ArrayList(), ArrayList()))
        val adapter=UserAdapter(this,list)
        val recyclerView=findViewById<RecyclerView>(R.id.list_user)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
    }
}