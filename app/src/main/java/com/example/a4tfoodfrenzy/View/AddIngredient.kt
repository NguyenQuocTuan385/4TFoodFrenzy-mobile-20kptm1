package com.example.a4tfoodfrenzy.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddIngredient : AppCompatActivity() {
    private lateinit var toolbarAddIngredient:MaterialToolbar
    private lateinit var unitDropdown: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        initToolbar()
        setupUnitDropdown()
        setBackToolbar()
        setCloseToolbar()

    }
    private fun initToolbar()
    {
        toolbarAddIngredient=findViewById(R.id.toolbarAddIngredient)
    }
    private fun setBackToolbar()
    {
        toolbarAddIngredient.setNavigationOnClickListener {
            finish()
        }
    }
    private fun setCloseToolbar()
    {
        toolbarAddIngredient.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    finish()
                    // Xử lý khi người dùng chọn Save
                    true
                }
                else -> false
            }
        }
    }
    private fun setupUnitDropdown() {
        unitDropdown=findViewById(R.id.unitDropdown)
        val items = listOf("g","kg","ml","l","mm","cm","m" )
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        unitDropdown.setAdapter(adapter)
    }

}