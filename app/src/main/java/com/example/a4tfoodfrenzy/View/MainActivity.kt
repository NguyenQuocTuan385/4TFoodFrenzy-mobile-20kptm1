package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
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
    var adapterRecipeTodayEatRV: RecipeListAdapter? = null
    var adapterRecipeMostLikesRV: RecipeListAdapter? = null
    val db = Firebase.firestore
    val generateDBModel = GenerateDBModel(this)
    val dbManagement = DBManagement()
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
            startActivity(intent)
        }

        if (DBManagement.isInitialized == false) {
            btnViewMoreTodayEat.visibility = View.GONE
            btnViewMoreMostLikes.visibility = View.GONE
            dbManagement.addListenerChangeDataFoodRecipe { foodRecipes ->
                val recipesListToday = generateRecipeTodayEatData(foodRecipes)
                adapterRecipeTodayEatRV = RecipeListAdapter(this, recipesListToday)
                recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
                recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)

                adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
                    val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                    intent.putExtra("foodRecipe",foodRecipe)
                    startActivity(intent)
                }
                btnViewMoreTodayEat.visibility = View.VISIBLE

                adapterRecipeMostLikesRV = RecipeListAdapter(this,generateRecipeMostLikesData(foodRecipes))
                recipeMostLikesRV!!.adapter = adapterRecipeMostLikesRV
                recipeMostLikesRV!!.layoutManager = GridLayoutManager(this, 3)
                btnViewMoreMostLikes.visibility = View.VISIBLE
                adapterRecipeMostLikesRV!!.onItemClick = { foodRecipe, i ->
                    val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                    startActivity(intent)
                }
            }
            dbManagement.addListenerChangeDataRecipeCategories {  }
            dbManagement.addListenerChangeDataRecipeComment {  }
            dbManagement.addListenerChangeDataRecipeDiets {  }
            dbManagement.addListenerChangeDataUser {  }
            if(FirebaseAuth.getInstance().currentUser != null) {
                dbManagement.addListenerChangeDataUserCurrent {  }
            }
            DBManagement.isInitialized = true
        }
        else {
            val recipesListToday = generateRecipeTodayEatData(DBManagement.foodRecipeList)
            adapterRecipeTodayEatRV = RecipeListAdapter(this,recipesListToday)
            recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
            recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)

            adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
                val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                intent.putExtra("foodRecipe",foodRecipe)
                startActivity(intent)
            }

            adapterRecipeMostLikesRV = RecipeListAdapter(this, generateRecipeMostLikesData(DBManagement.foodRecipeList))
            recipeMostLikesRV!!.adapter = adapterRecipeMostLikesRV
            recipeMostLikesRV!!.layoutManager = GridLayoutManager(this, 3)
            adapterRecipeMostLikesRV!!.onItemClick = { foodRecipe, i ->
                val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                startActivity(intent)
            }
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
            startActivity(intent)
        }
        btnViewMoreMostLikes.setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            intent.putExtra("keySearch","Các món được yêu thích nhất")
            intent.putExtra("pageSearch","home")
            intent.putExtra("typeSearch","recipeMostLikes")
            startActivity(intent)
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
            if (!randomIndexSet.contains(randomIndex)) {
                randomIndexSet.add(randomIndex)
                result.add(recipeList.get(randomIndex))
            }
        }
        return result
    }

    fun generateRecipeMostLikesData(recipeList:ArrayList<FoodRecipe>): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        val sortedRecipes = recipeList.sortedByDescending { it.numOfLikes }
        for (i in 0 until 6) {
            result.add(sortedRecipes.get(i))
        }
        return result
    }
//    fun generateRecipeMostLikesData(): ArrayList<FoodRecipe> {
//        var result = ArrayList<FoodRecipe>()
//
//        var foodRecipe = FoodRecipe(1, "Cơm rang dưa bò", "comrangduabo", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//        foodRecipe= FoodRecipe(1, "Mì trứng xào bò", "mitrungxaobo", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//
//        foodRecipe= FoodRecipe(1, "Mì quảng gà", "miquangga", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//        foodRecipe= FoodRecipe(1, "Thịt xiên nướng cà ri", "thitxiennuong", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//        foodRecipe= FoodRecipe(1, "Mực nướng Malaysia", "mucnuongmalaysia", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//        foodRecipe= FoodRecipe(1, "Thịt ba chỉ nướng mật ong", "thitbachimatong", 2, "15 phút",
//            Date(2022, 2,2), true,
//            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
//        result.add(foodRecipe)
//
//        return result
//    }

}