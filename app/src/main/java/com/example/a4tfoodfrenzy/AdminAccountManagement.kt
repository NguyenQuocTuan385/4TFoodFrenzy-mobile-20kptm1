package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminAccountManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_account_management)
       val list= arrayListOf<User>()
        list.add(User("Dương Chí Thông",R.drawable.avt))
        list.add(User("Dương Chí Thông",R.drawable.avt))
        list.add(User("Dương Chí Thông",R.drawable.avt))
        list.add(User("Dương Chí Thông",R.drawable.avt))
        list.add(User("Dương Chí Thông",R.drawable.avt))
        list.add(User("Dương Chí Thông",R.drawable.avt))
        val adapter=UserAdapter(this,list)
        val recyclerView=findViewById<RecyclerView>(R.id.list_user)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
    }
}