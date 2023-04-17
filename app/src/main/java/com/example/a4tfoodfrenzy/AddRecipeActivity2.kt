package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class AddRecipeActivity2 : AppCompatActivity() {
    private lateinit var Timedropdown:AutoCompleteTextView
    private lateinit var TypeFooddropdown:AutoCompleteTextView
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        initToolbar()
        setTypeFoodDropdown()
        setupTimeDropdown()
        setBackToolbar()
        setupContinueButton()
        setCloseToolbar()
    }

    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }
    private fun setTypeFoodDropdown() {
        val items = listOf("Khai vị", "Món chính", "Ăn vặt", "Nấu nhanh", "Ăn chay", "Món tráng miệng", "Thức uống")
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        TypeFooddropdown = findViewById(R.id.dropdown_typeFood)
        TypeFooddropdown.setAdapter(adapter)
    }
    private fun setupTimeDropdown() {
        val items = listOf("Dưới 15 phút", "Dưới 30 phút", "Dưới 45 phút", "Dưới 1 tiếng", "Trên 1 tiếng")
        Timedropdown = findViewById(R.id.dropdown_time)
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        Timedropdown.setAdapter(adapter)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }

    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity3::class.java))
        }
    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    startActivity(Intent(this, AddNewRecipe::class.java))
                    true
                }
                else -> false
            }
        }
    }

}