package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class AddRecipeActivity1 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: Toolbar
    private lateinit var continueBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe1)
        initToolbar()
        setBackToolbar()
        setupContinueButton()
        setupCloseToolbar()
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }

    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity2::class.java))
        }
    }

    private fun setupCloseToolbar() {
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