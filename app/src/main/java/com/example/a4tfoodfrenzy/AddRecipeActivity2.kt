package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class AddRecipeActivity2 : AppCompatActivity() {
    private lateinit var dropdown:AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        val items=listOf("Dưới 15 phút","Dưới 30 phút","Dưới 45 phút","Dưới 1 tiếng","Trên 1 tiếng")
        dropdown=findViewById(R.id.dropdown_time)
        val adapter=ArrayAdapter(this,R.layout.list_item_dropdown,items)
        dropdown.setAdapter(adapter)
    }
}