package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AfterSearchActivity : AppCompatActivity() {
    var adapterRecipeAfterSearchRV: RecipeListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        val recipeAfterSearchRV = findViewById<RecyclerView>(R.id.recipeAfterSearchRV)
        var recipeAfterSearch = generateRecipeTodayEatData() //implemened below
        adapterRecipeAfterSearchRV = RecipeListAdapter(recipeAfterSearch, true, false, false)
        recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
        recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 3)

    }

    private fun generateRecipeTodayEatData(): ArrayList<RecipeRender> {
        var result = ArrayList<RecipeRender>()

        var typeRecipe: RecipeRender = RecipeRender()
        typeRecipe.titleRecipe = "Canh khổ qua nhồi thịt"
        typeRecipe.recipeImage = R.drawable.khoquanhoithit
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Canh chua cá lóc"
        typeRecipe.recipeImage = R.drawable.canhcaloc
        result.add(typeRecipe)


        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bò sốt me"
        typeRecipe.recipeImage = R.drawable.bosotme
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bò kho"
        typeRecipe.recipeImage = R.drawable.bokho
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bún bò huế"
        typeRecipe.recipeImage = R.drawable.bunbohue
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bưởi trộn khô gà"
        typeRecipe.recipeImage = R.drawable.buoitronkhoga
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Cơm rang dưa bò"
        typeRecipe.recipeImage = R.drawable.comrangduabo
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Mì trứng xào bò"
        typeRecipe.recipeImage = R.drawable.mitrungxaobo
        result.add(typeRecipe)


        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Mì quảng gà"
        typeRecipe.recipeImage = R.drawable.miquangga
        result.add(typeRecipe)

        return result
    }
}