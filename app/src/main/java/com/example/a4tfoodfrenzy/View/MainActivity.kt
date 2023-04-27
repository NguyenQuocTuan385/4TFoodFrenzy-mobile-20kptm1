package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Helper.GenerateDBModel
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var adapterCateRecipeRV: RecipeCateListAdapter? = null
    val db = Firebase.firestore
    val generateDBModel = GenerateDBModel(this)
    val dbManagement = DBManagement()
    val REQUEST_CODE_SEARCH = 1111
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cateRecipeRV = findViewById<RecyclerView>(R.id.cateRecipeRV)
        val recipeTodayEatRV = findViewById<RecyclerView>(R.id.recipeTodayEatRV)
        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val btnViewMoreTodayEat = findViewById<Button>(R.id.btnViewMoreTodayEat)
        val btnViewMoreMostLikes = findViewById<Button>(R.id.btnViewMoreMostLikes)
        var cateRecipeList = generateCateRecipeData() //implemened below

        adapterCateRecipeRV = RecipeCateListAdapter(cateRecipeList, true, false)
        cateRecipeRV!!.adapter = adapterCateRecipeRV
        cateRecipeRV!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterCateRecipeRV!!.onItemClick = { recipeCate, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            if (recipeCate.recipeCateTitle.equals("Đồ uống")) {
                intent.putExtra("keySearch","Thức uống")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Nấu nhanh")) {
                intent.putExtra("keySearch","Nấu nhanh")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Đồ ăn vặt")) {
                intent.putExtra("keySearch","Ăn vặt")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Ăn chay")) {
                intent.putExtra("keySearch","Ăn chay")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Món chính")) {
                intent.putExtra("keySearch","Món chính")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Khai vị")) {
                intent.putExtra("keySearch","Khai vị")
                intent.putExtra("pageSearch","home")
                intent.putExtra("typeSearch","recipeCategory")
            }
            startActivityForResult(intent, REQUEST_CODE_SEARCH)
        }

        if (DBManagement.isInitialized == false) {
            if(FirebaseAuth.getInstance().currentUser != null) {
                dbManagement.addListenerChangeDataUserCurrent {user ->
                    if (user.email.equals("")) {
                        dbManagement.fetchDataUserCurrent {  }
                    }
                    else if (user.isAdmin == true) {
                        val intent= Intent(this, AdminDashboard::class.java)
                        startActivity(intent)
                    }
                }
            }
            dbManagement.addListenerChangeDataFoodRecipe { foodRecipes ->
                if (foodRecipes.isEmpty()) {
                    dbManagement.fetchDataFoodRecipe { foodRecipeList ->
                        setRecipeListAdapter(RecipeListAdapter(this, generateRecipeTodayEatData(foodRecipeList)), recipeTodayEatRV)
                        btnViewMoreTodayEat.visibility = View.VISIBLE

                        setRecipeListAdapter(RecipeListAdapter(this, generateRecipeMostLikesData(foodRecipeList)), recipeMostLikesRV)
                        btnViewMoreMostLikes.visibility = View.VISIBLE
                    }
                } else {
                    setRecipeListAdapter(RecipeListAdapter(this, generateRecipeTodayEatData(foodRecipes)), recipeTodayEatRV)
                    btnViewMoreTodayEat.visibility = View.VISIBLE

                    setRecipeListAdapter(RecipeListAdapter(this, generateRecipeMostLikesData(foodRecipes)), recipeMostLikesRV)
                    btnViewMoreMostLikes.visibility = View.VISIBLE
                }
            }
            dbManagement.addListenerChangeDataRecipeCategories { recipeCategories ->
                if (recipeCategories.isEmpty()) {
                    dbManagement.fetchDataRecipeCate {  }
                }
            }
            dbManagement.addListenerChangeDataRecipeComment { recipeComments ->
                if (recipeComments.isEmpty()) {
                    dbManagement.fetchDataRecipeCmt {  }
                }
            }
            dbManagement.addListenerChangeDataRecipeDiets { recipeDiets ->
                if (recipeDiets.isEmpty()) {
                    dbManagement.fetchDataRecipeDiet {  }
                }
            }
            dbManagement.addListenerChangeDataUser { users ->
                if (users.isEmpty()) {
                    dbManagement.fetchDataUser {  }
                }
            }
            DBManagement.isInitialized = true
        }
        else {
            setRecipeListAdapter(RecipeListAdapter(this, generateRecipeTodayEatData(DBManagement.foodRecipeList)), recipeTodayEatRV)
            btnViewMoreTodayEat.visibility = View.VISIBLE

            setRecipeListAdapter(RecipeListAdapter(this, generateRecipeMostLikesData(DBManagement.foodRecipeList)), recipeMostLikesRV)
            btnViewMoreMostLikes.visibility = View.VISIBLE
        }

        findViewById<LinearLayout>(R.id.searchLL).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
        btnViewMoreTodayEat.setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            intent.putExtra("keySearch","Hôm nay ăn gì")
            intent.putExtra("pageSearch","home")
            intent.putExtra("typeSearch","recipeTodayEat")
            startActivityForResult(intent, REQUEST_CODE_SEARCH)
        }
        btnViewMoreMostLikes.setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            intent.putExtra("keySearch","Các món được yêu thích nhất")
            intent.putExtra("pageSearch","home")
            intent.putExtra("typeSearch","recipeMostLikes")
            startActivityForResult(intent, REQUEST_CODE_SEARCH)
        }

        val menu = bottomNavigationView.menu

        menu.findItem(R.id.home).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
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
//        generateDBModel.generateDatabaseRecipeFoodCate()
//        generateDBModel.generateDatabaseRecipeComment()
//        generateDBModel.generateDatabaseRecipeDiets()
//        generateDBModel.generateDatabaseRecipeFood()
//        generateDBModel.generateDatabaseUsers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE_SEARCH) {
            if (resultCode === Activity.RESULT_OK) {

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Hủy đăng ký listener
        dbManagement.destroyListener()
    }
    fun generateCateRecipeData(): ArrayList<RecipeCategorySuggest> {
        var result = ArrayList<RecipeCategorySuggest>()
        var typeRecipe: RecipeCategorySuggest = RecipeCategorySuggest("Nấu nhanh",
            R.drawable.donghonaunhanh
        )
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ uống", R.drawable.douonghome)
        result.add(typeRecipe)


        typeRecipe = RecipeCategorySuggest("Món chính", R.drawable.monchinh)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ ăn vặt", R.drawable.doanvathome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Ăn chay", R.drawable.raucuhome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Khai vị", R.drawable.khaivihome)
        result.add(typeRecipe)

        return result
    }
    fun generateRecipeTodayEatData(recipeList:ArrayList<FoodRecipe>): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()
        val randomIndexSet = mutableSetOf<Int>()

        while(randomIndexSet.size != 6) {
            val randomIndex = (0 until recipeList.size).random()
            if (!randomIndexSet.contains(randomIndex) && (recipeList.get(randomIndex).isPublic == true)) {
                randomIndexSet.add(randomIndex)
                result.add(recipeList.get(randomIndex))
            }
        }
        return result
    }

    fun generateRecipeMostLikesData(recipeList:ArrayList<FoodRecipe>): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        val sortedRecipes = recipeList.sortedByDescending { it.numOfLikes }
        var index = 0
        var numberRecipe = 0
        while (numberRecipe != 6) {
            if (sortedRecipes.get(index).isPublic == true) {
                result.add(sortedRecipes.get(index))
                numberRecipe++
            }
            index++
        }
        return result
    }
    fun setRecipeListAdapter(adapterRecipeList: RecipeListAdapter?, recipeRV: RecyclerView) {
        recipeRV!!.adapter = adapterRecipeList
        recipeRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeList!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            intent.putExtra("foodRecipe",foodRecipe)
            startActivity(intent)
        }
    }

}