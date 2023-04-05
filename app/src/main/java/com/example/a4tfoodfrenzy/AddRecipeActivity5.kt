package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class AddRecipeActivity5 : AppCompatActivity() {
    private lateinit var dropdown: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe5)
        val items=listOf("Khai vị","Món chính","Ăn vặt","Nấu nhanh","Ăn chay","Món tráng miệng","Thức uống")
        dropdown=findViewById(R.id.dropdown_typeFood)
        val adapter= ArrayAdapter(this,R.layout.list_item_dropdown,items)
        dropdown.setAdapter(adapter)
    }
}