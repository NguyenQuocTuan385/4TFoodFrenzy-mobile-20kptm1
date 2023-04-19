package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar

class AddIngredient : AppCompatActivity() {
    private lateinit var toolbarAddIngredient:MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        initToolbar()
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
}