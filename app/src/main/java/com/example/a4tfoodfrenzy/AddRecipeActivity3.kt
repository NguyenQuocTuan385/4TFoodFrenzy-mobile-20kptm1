package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.RecipeIngredient
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity3 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var addIngredient: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe3)
        initToolbar()
        setBackToolbar()
        setCloseToolbar()
        setupRecyclerView()
        setupContinueButton()
        setupAddIngredientButton()
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }
    private fun createIngredientList(): ArrayList<RecipeIngredient> {
        val list = ArrayList<RecipeIngredient>()
        list.add(RecipeIngredient(1,250, "Hải sản","g"))
        list.add(RecipeIngredient(2,100, "Thịt bò","kg"))
        list.add(RecipeIngredient(3,10, "Muối","g"))
        list.add(RecipeIngredient(4,8, "Đường","g"))
        return list
    }


    private fun setupRecyclerView() {
        val listIngredient = findViewById<RecyclerView>(R.id.listIgredient)
        val list = createIngredientList()
        val adapter = ListIngredientAdapter(this, list)
        listIngredient.adapter = adapter
        listIngredient.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }
    private fun setCloseToolbar()
    {
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

    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity4::class.java)
            startActivity(intent)
        }
    }

    private fun setupAddIngredientButton() {
        addIngredient = findViewById(R.id.addIngredientBtn)
        addIngredient.setOnClickListener {
            val intent = Intent(this, AddIngredient::class.java)
            startActivity(intent)
        }
    }
}