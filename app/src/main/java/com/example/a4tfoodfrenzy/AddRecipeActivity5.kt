package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.Toolbar

class AddRecipeActivity5 : AppCompatActivity() {
    private lateinit var dropdown: AutoCompleteTextView
    private lateinit var toolbarAddRecipe: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe5)
        setDropdownMenu()
        initToolbar()
        setBackToolbar()
        setCloseToolbar()
    }

    private fun setDropdownMenu() {
        val items = listOf("Khai vị", "Món chính", "Ăn vặt", "Nấu nhanh", "Ăn chay", "Món tráng miệng", "Thức uống")
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        dropdown = findViewById(R.id.dropdown_typeFood)
        dropdown.setAdapter(adapter)
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById<Toolbar>(R.id.toolbarAddRecipe)

    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }

    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}