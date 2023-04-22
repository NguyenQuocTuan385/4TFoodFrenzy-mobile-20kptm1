package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.*
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AfterSearchActivity : AppCompatActivity() {
    var adapterRecipeAfterSearchRV: RecipeListAdapter? = null
    var recipeAfterSearchRV:RecyclerView? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val searchET = findViewById<EditText>(R.id.searchET)
        recipeAfterSearchRV = findViewById(R.id.recipeAfterSearchRV)

        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imgFilter).setOnClickListener {
            val intent = Intent(this, SortRecipeActivity::class.java)
            startActivity(intent)
        }
        val keySearch = intent.getStringExtra("keySearch")
        val typeSearch = intent.getStringExtra("typeSearch")
        searchET.setText(keySearch)
        if (typeSearch.toString() == "cookTime") {
            generateRecipeWithCookTime(searchET.text.toString()) { recipeAfterSearch ->
                setRecipeListAdapter(recipeAfterSearch)
            }
        } else if (typeSearch.toString() == "recipeCategory") {
//            generateRecipeCategory(searchET.text.toString()) {recipeCategory ->
//                generateRecipeWithCategory(recipeCategory) {recipeAfterSearch ->
//                    setRecipeListAdapter(recipeAfterSearch)
//                }
//            }
        } else {

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
    fun setRecipeListAdapter(recipeAfterSearch:ArrayList<FoodRecipe>) {
        adapterRecipeAfterSearchRV = RecipeListAdapter(this,recipeAfterSearch)
        recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
        recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeAfterSearchRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }
    }
    fun generateRecipeWithCookTime(cookTime:String ,callback:(ArrayList<FoodRecipe>) -> Unit ) {
        var result = ArrayList<FoodRecipe>()

        val recipesCollection = db.collection("RecipeFoods")
        val query = recipesCollection.whereEqualTo("cookTime",cookTime)
        query.get()
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
//    fun generateRecipeCategory(recipeCategory:String ,callback:(RecipeCategory) -> Unit ) {
//        val recipeCategory = RecipeCategory()
//        val recipeCatesCollection = db.collection("RecipeCates")
//        val recipeCateQuery = recipeCatesCollection.whereEqualTo("recipeCateName",recipeCategory)
//
//        recipeCateQuery.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val recipeCategory = document.toObject(RecipeCategory::class.java)
//                }
//                callback(recipeCategory)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(recipeCategory)
//            }
//    }
//    fun generateRecipeWithCategory(recipeCategory: RecipeCategory, callback: (ArrayList<FoodRecipe>) -> Unit) {
//        var result = ArrayList<FoodRecipe>()
//
//        val recipeQuery = db.collection("RecipeFoods").whereIn("id",recipeCategory.foodRecipes)
//
//        recipeQuery.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val foodRecipe = document.toObject(FoodRecipe::class.java)
//                    result.add(foodRecipe)
//                }
//                callback(result)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
}
