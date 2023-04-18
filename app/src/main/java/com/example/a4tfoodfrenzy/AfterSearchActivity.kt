package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.FoodRecipe
import com.google.android.material.bottomnavigation.BottomNavigationView

class AfterSearchActivity : AppCompatActivity() {
    var adapterRecipeAfterSearchRV: RecipeListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        val recipeAfterSearchRV = findViewById<RecyclerView>(R.id.recipeAfterSearchRV)
        var recipeAfterSearch = generateRecipeTodayEatData() //implemened below
        adapterRecipeAfterSearchRV = RecipeListAdapter(recipeAfterSearch)
        recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
        recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 3)

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
                    val intent = Intent(this, SearchScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddRecipeActivity1::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun generateRecipeTodayEatData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe: FoodRecipe = FoodRecipe(1,"Canh khổ qua nhồi thịt",R.drawable.khoquanhoithit,2,15,4)
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1,"Canh chua cá lóc",R.drawable.canhcaloc,2,15,4)
        result.add(foodRecipe)


        foodRecipe = FoodRecipe(1,"Bò sốt me",R.drawable.bosotme,2,15,4)
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1,"Bò kho",R.drawable.bokho,2,15,4)
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1,"Bún bò huế",R.drawable.bunbohue,2,15,4)
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1,"Bưởi trộn khô gà",R.drawable.buoitronkhoga,2,15,4)
        result.add(foodRecipe)

        return result
    }
}
