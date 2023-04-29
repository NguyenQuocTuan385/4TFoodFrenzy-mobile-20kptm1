package com.example.a4tfoodfrenzy.View

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.*
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.example.a4tfoodfrenzy.Model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class AfterSearchActivity : AppCompatActivity() {
    private var adapterRecipeAfterSearchRV: RecipeListAdapter? = null
    private var recipeAfterSearchRV: RecyclerView? = null
    val db = Firebase.firestore
    private val REQUEST_CODE_FILTER = 1111
    private val REQUEST_CODE_BACK_FILTER = 2222
    private val REQUEST_CODE_APPLY_FILTER = 3333
    private val REQUEST_RECIPE_DETAILS = 4444
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
            intent.putExtra("SearchedList", adapterRecipeAfterSearchRV?.getList())
            startActivityForResult(intent, REQUEST_CODE_FILTER)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        setRecipeListAdapterWithCondition(keySearch!!, typeSearch!!)

        searchET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!searchET.text.isNullOrEmpty()) {
                    val recipeAfterSearch = findRecipeListWithKeyword(searchET.text.toString())
                    setRecipeListAdapter(recipeAfterSearch)
                } else {
                    setRecipeListAdapter(generateRecipeDatabase(DBManagement.foodRecipeList))
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
        if(requestCode == REQUEST_CODE_FILTER){
//            if (requestCode == REQUEST_CODE_BACK_FILTER) {
//
//            } else if (requestCode == REQUEST_CODE_APPLY_FILTER) {
            val filteredFoodList = data?.extras?.get("filterdFoodRecipe") as ArrayList<FoodRecipe>
            var finalFilteredFoodList = generateRecipeDatabase(filteredFoodList)
            setRecipeListAdapter(finalFilteredFoodList)

            Toast.makeText(
                this,
                    "${filteredFoodList.size}",
                    Toast.LENGTH_SHORT
                ).show()
//            }
        }

    }

    fun setRecipeListAdapterWithCondition(keySearch: String, typeSearch: String) {
        if (typeSearch == "cookTime") {
            val recipeAfterSearch = findRecipeListWithCookTime(keySearch.toString())
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else if (typeSearch == "recipeCategory") {
            val recipeAfterSearch = findRecipeListWithCategory(keySearch.toString())
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else if (typeSearch == "recipeTodayEat") {
            setRecipeListAdapter(generateRecipeDatabase(DBManagement.foodRecipeList))
            searchET.setHint(keySearch)
        } else if (typeSearch == "recipeMostLikes") {
            val recipeAfterSearch = generateRecipeMostLikesData(DBManagement.foodRecipeList)
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setHint(keySearch)
        } else {
            val recipeAfterSearch = findRecipeListWithKeyword(keySearch)
            setRecipeListAdapter(recipeAfterSearch)
            searchET.setText(keySearch)
        }
    }

    fun setRecipeListAdapter(recipeAfterSearch: LinkedHashMap<FoodRecipe, User>) {
        adapterRecipeAfterSearchRV = RecipeListAdapter(this, recipeAfterSearch)
        recipeAfterSearchRV!!.adapter = adapterRecipeAfterSearchRV
        recipeAfterSearchRV!!.layoutManager = GridLayoutManager(this, 2)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recipeAfterSearchRV!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        adapterRecipeAfterSearchRV!!.onItemClick = { foodRecipe,user, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            intent.putExtra("foodRecipe", foodRecipe)
            intent.putExtra("user", user)
            startActivityForResult(intent, REQUEST_RECIPE_DETAILS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }

    fun findRecipeListWithCookTime(cookTime: String): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (foodRecipe.cookTime == cookTime && foodRecipe.isPublic) {
                for (user in DBManagement.userList) {
                    if (user.myFoodRecipes.contains(foodRecipe.id)) {
                        result.put(foodRecipe, user)
                        break
                    }
                }
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

    fun findRecipeListWithCategory(recipeCateName: String): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        val recipeCategory = findRecipeCategory(recipeCateName)

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (recipeCategory.foodRecipes.contains(foodRecipe.id) && foodRecipe.isPublic) {
                for (user in DBManagement.userList) {
                    if (user.myFoodRecipes.contains(foodRecipe.id)) {
                        result[foodRecipe] = user
                        break
                    }
                }
            }
        }
        return result
    }

    fun findRecipeListWithKeyword(keySearch: String): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        for (foodRecipe in DBManagement.foodRecipeList) {
            if (foodRecipe.recipeName.toLowerCase()
                    .contains(keySearch.toLowerCase()) && foodRecipe.isPublic
            ) {
                for (user in DBManagement.userList) {
                    if (user.myFoodRecipes.contains(foodRecipe.id)) {
                        result[foodRecipe] = user
                        break
                    }
                }
            }
        }
        return result
    }

    fun generateRecipeMostLikesData(recipeList: ArrayList<FoodRecipe>): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        val sortedRecipes = recipeList.sortedByDescending { it.numOfLikes }
        for (foodRecipe in sortedRecipes) {
            if (foodRecipe.isPublic) {
                for (user in DBManagement.userList) {
                    if (user.myFoodRecipes.contains(foodRecipe.id)) {
                        result[foodRecipe] = user
                        break
                    }
                }
            }
        }
        return result
    }

    fun generateRecipeDatabase(recipeList: ArrayList<FoodRecipe>): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        for (foodRecipe in recipeList) {
            if (foodRecipe.isPublic) {
                for (user in DBManagement.userList) {
                    if (user.myFoodRecipes.contains(foodRecipe.id)) {
                        result[foodRecipe] = user
                        break
                    }
                }
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
