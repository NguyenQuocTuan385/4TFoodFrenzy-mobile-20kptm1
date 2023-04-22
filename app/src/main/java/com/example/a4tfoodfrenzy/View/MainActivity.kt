package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.Query
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
    val storage = FirebaseStorage.getInstance()
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cateRecipeRV = findViewById<RecyclerView>(R.id.cateRecipeRV)
        var cateRecipeList = generateCateRecipeData() //implemened below

        adapterCateRecipeRV = RecipeCateListAdapter(cateRecipeList, true, false)
        cateRecipeRV!!.adapter = adapterCateRecipeRV
        cateRecipeRV!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterCateRecipeRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val recipeTodayEatRV = findViewById<RecyclerView>(R.id.recipeTodayEatRV)
        var recipeTodayEat = generateRecipeTodayEatData() //implemened below
        adapterRecipeTodayEatRV = RecipeListAdapter(this, recipeTodayEat)
        recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
        recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        var recipeMostLikes = generateRecipeMostLikesData() //implemened below
        adapterRecipeMostLikesRV = RecipeListAdapter(this, recipeMostLikes)
        recipeMostLikesRV!!.adapter = adapterRecipeMostLikesRV
        recipeMostLikesRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeMostLikesRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.searchLL).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnViewMoreTodayEat).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnViewMoreMostLikes).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
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
//        generateDatabaseRecipeFoodCate()
//        generateDatabaseRecipeComment()
//        generateDatabaseRecipeDiets()
//        generateDatabaseRecipeFood()
//        generateDatabaseUsers()
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

        typeRecipe = RecipeCategorySuggest("Hải sản", R.drawable.haisanhome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Rau củ", R.drawable.raucuhome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Điểm tâm", R.drawable.banhmihome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Khai vị", R.drawable.khaivihome)
        result.add(typeRecipe)

        return result
    }
    fun generateRecipeTodayEatData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe: FoodRecipe = FoodRecipe(1, "Canh khổ qua nhồi thịt",
            "khoquanhoithit", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Canh chua cá lóc", "canhcaloc", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe = FoodRecipe(1, "Bò sốt me", "bosotme", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bò kho", "bokho", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bún bò huế","bunbohue", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bưởi trộn khô gà", "buoitronkhoga", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

    fun generateRecipeMostLikesData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe = FoodRecipe(1, "Cơm rang dưa bò", "comrangduabo", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mì trứng xào bò", "mitrungxaobo", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe= FoodRecipe(1, "Mì quảng gà", "miquangga", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt xiên nướng cà ri", "thitxiennuong", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mực nướng Malaysia", "mucnuongmalaysia", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt ba chỉ nướng mật ong", "thitbachimatong", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

}