package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.model.RecipeCategory

class AddRecipeActivity2 : AppCompatActivity() {
    private lateinit var timedropdown:AutoCompleteTextView
    private lateinit var cateFoodDropdown:AutoCompleteTextView
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        initToolbar()
        setcateFoodDropdown()
        setupTimeDropdown()
        setBackToolbar()
        setupContinueButton()
        setCloseToolbar()
    }

    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }
    private fun setcateFoodDropdown() {
        var recipeCateList : ArrayList<RecipeCategory> = ArrayList()
        recipeCateList.add(RecipeCategory(1,"Khai vị", ArrayList()))
        recipeCateList.add(RecipeCategory(2,"Món chính",ArrayList()))
        recipeCateList.add(RecipeCategory(3,"Ăn vặt",ArrayList()))
        recipeCateList.add(RecipeCategory(4,"Nấu nhanh",ArrayList()))
        recipeCateList.add(RecipeCategory(5,"Ăn chay",ArrayList()))
        recipeCateList.add(RecipeCategory(6,"Món tráng miệng",ArrayList()))
        recipeCateList.add(RecipeCategory(7,"Thức uống",ArrayList()))

        var items : ArrayList<String> = ArrayList()
        for (recipeCateTemp:RecipeCategory in recipeCateList) {
            items.add(recipeCateTemp.recipeCateName)
        }
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        cateFoodDropdown = findViewById(R.id.dropdown_typeFood)
        cateFoodDropdown.setAdapter(adapter)
    }
    private fun setupTimeDropdown() {
        val items = listOf("Dưới 15 phút", "Dưới 30 phút", "Dưới 45 phút", "Dưới 1 tiếng", "Trên 1 tiếng")
        timedropdown = findViewById(R.id.dropdown_time)
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        timedropdown.setAdapter(adapter)
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