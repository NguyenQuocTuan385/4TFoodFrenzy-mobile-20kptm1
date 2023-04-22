package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.*
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AfterSearchActivity : AppCompatActivity() {
    var adapterRecipeAfterSearchRV: RecipeListAdapter? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        val recipeAfterSearchRV = findViewById<RecyclerView>(R.id.recipeAfterSearchRV)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imgFilter).setOnClickListener {
            val intent = Intent(this, SortRecipeActivity::class.java)
            startActivity(intent)
        }

        var recipeAfterSearch = generateRecipeTodayEatData() { recipeAfterSearch ->
            adapterRecipeAfterSearchRV = RecipeListAdapter(this,recipeAfterSearch)
            recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
            recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 3)
            adapterRecipeAfterSearchRV!!.onItemClick = { foodRecipe, i ->
                val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                startActivity(intent)
            }
        }

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
                    val intent = Intent(this, AddNewRecipe::class.java)
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

    fun generateRecipeTodayEatData(callback:(ArrayList<FoodRecipe>) -> Unit ) {
        var result = ArrayList<FoodRecipe>()

        val recipesCollection = db.collection("RecipeFoods")

        recipesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val foodRecipe = document.toObject(FoodRecipe::class.java)
                    result.add(foodRecipe)
                }
                callback(result)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
                callback(arrayListOf())
            }

    }
}
