package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchScreen : AppCompatActivity() {
    var adapterTypeRecipeRV: RecipeListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_screen)

        val typeRecipeRV = findViewById<RecyclerView>(R.id.typeRecipeRV)
        var typeRecipeList = generateTypeRecipeData() //implemened below
        adapterTypeRecipeRV = RecipeListAdapter(typeRecipeList, false, false, true)
        typeRecipeRV!!.layoutManager = GridLayoutManager(this, 3)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        typeRecipeRV!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        typeRecipeRV!!.adapter = adapterTypeRecipeRV

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.search).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun generateTypeRecipeData(): ArrayList<RecipeRender> {
        var result = ArrayList<RecipeRender>()
        var recipe: RecipeRender = RecipeRender()
        recipe.titleRecipe = "Đồ uống"
        recipe.recipeImage = R.drawable.drink
        result.add(recipe)

        recipe = RecipeRender()
        recipe.titleRecipe = "Món gà"
        recipe.recipeImage = R.drawable.chicken
        result.add(recipe)

        recipe = RecipeRender()
        recipe.titleRecipe = "Nấu nhanh"
        recipe.recipeImage = R.drawable.time
        result.add(recipe)

        recipe = RecipeRender()
        recipe.titleRecipe = "Đồ ăn vặt"
        recipe.recipeImage = R.drawable.fastfood
        result.add(recipe)

        recipe = RecipeRender()
        recipe.titleRecipe = "Đồ chay"
        recipe.recipeImage = R.drawable.diet
        result.add(recipe)

        recipe = RecipeRender()
        recipe.titleRecipe = "Món chính"
        recipe.recipeImage = R.drawable.mainfood
        result.add(recipe)

        return result
    }
}