package com.example.a4tfoodfrenzy.View

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.*
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
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
    var recipeAfterSearchRV: RecyclerView? = null
    val db = Firebase.firestore
    val REQUEST_CODE_FILTER = 1111
    val REQUEST_CODE_BACK_FILTER = 2222
    val REQUEST_CODE_APPLY_FILTER = 3333
    val REQUEST_RECIPE_DETAILS = 4444
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var searchET: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        searchET = findViewById<EditText>(R.id.searchET)
        recipeAfterSearchRV = findViewById(R.id.recipeAfterSearchRV)
        DBManagement.existAfterSearch = true

        val keySearch = intent.getStringExtra("keySearch")
        val typeSearch = intent.getStringExtra("typeSearch")

        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            finish()
        }
        findViewById<ImageView>(R.id.imgFilter).setOnClickListener {
            val intent = Intent(this, SortRecipeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        setRecipeListAdapterWithCondition(keySearch!!, typeSearch!!)

        searchET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!searchET.text.isNullOrEmpty()) {
                    val recipeAfterSearch = findRecipeListWithKeyword(searchET.text.toString())
                    setRecipeListAdapter(recipeAfterSearch)
                } else {
                    setRecipeListAdapter(DBManagement.foodRecipeList)
                }
            }
            true
        }

        val menu = bottomNavigationView.menu
        menu.findItem(R.id.search).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    true
                }
                R.id.profile -> {
                    if (DBManagement.user_current == null) {
                        val intent = Intent(this, LogoutActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    } else {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE_FILTER) {
            if (requestCode === REQUEST_CODE_BACK_FILTER) {

            } else if (requestCode === REQUEST_CODE_APPLY_FILTER) {

            }
        }
    }

    fun setRecipeListAdapterWithCondition(keySearch: String, typeSearch: String) {
        if (typeSearch.toString() == "cookTime") {
            val recipeAfterSearch = findRecipeListWithCookTime(keySearch.toString())
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else if (typeSearch.toString() == "recipeCategory") {
            val recipeAfterSearch = findRecipeListWithCategory(keySearch.toString())
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else if (typeSearch.toString() == "recipeTodayEat") {
            setRecipeListAdapter(DBManagement.foodRecipeList)
            searchET.setHint(keySearch)
        } else if (typeSearch.toString() == "recipeMostLikes") {
            val recipeAfterSearch = generateRecipeMostLikesData(DBManagement.foodRecipeList)
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else {
            val recipeAfterSearch = findRecipeListWithKeyword(keySearch.toString())
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setText(keySearch)
        }
    }

    fun setRecipeListAdapter(recipeAfterSearch: ArrayList<FoodRecipe>) {
        adapterRecipeAfterSearchRV = RecipeListAdapter(this, recipeAfterSearch)
        recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
        recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeAfterSearchRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            intent.putExtra("foodRecipe", foodRecipe)
            startActivityForResult(intent, REQUEST_RECIPE_DETAILS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }

    fun findRecipeListWithCookTime(cookTime: String): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (foodRecipe.cookTime == cookTime && (foodRecipe.isPublic == true)) {
                result.add(foodRecipe)
            }
        }
        return result
    }

    fun findRecipeCategory(recipeCateName: String): RecipeCategory {
        for (recipeCate in DBManagement.recipeCateList) {
            if (recipeCate.recipeCateName == recipeCateName) {
                return recipeCate
            }
        }
        return RecipeCategory()
    }

    fun findRecipeListWithCategory(recipeCateName: String): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        val recipeCategory = findRecipeCategory(recipeCateName)

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (recipeCategory.foodRecipes.contains(foodRecipe.id) && (foodRecipe.isPublic == true)) {
                result.add(foodRecipe)
            }
        }
        return result
    }

    fun findRecipeListWithKeyword(keySearch: String): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (foodRecipe.recipeName.toLowerCase()
                    .contains(keySearch.toLowerCase()) && (foodRecipe.isPublic == true)
            ) {
                result.add(foodRecipe)
            }
        }
        return result
    }

    fun generateRecipeMostLikesData(recipeList: ArrayList<FoodRecipe>): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        val sortedRecipes = recipeList.sortedByDescending { it.numOfLikes }
        for (recipe in sortedRecipes) {
            if (recipe.isPublic == true) {
                result.add(recipe)
            }
        }
        return result
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.search).isChecked = true
        val keySearch = intent.getStringExtra("keySearch")
        val typeSearch = intent.getStringExtra("typeSearch")
        if (!keySearch.isNullOrEmpty() && !typeSearch.isNullOrEmpty()) {
            setRecipeListAdapterWithCondition(keySearch, typeSearch)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DBManagement.existAfterSearch = false
    }
}
