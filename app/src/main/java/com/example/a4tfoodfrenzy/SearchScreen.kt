package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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