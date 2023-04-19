package com.example.a4tfoodfrenzy.View

import android.content.Intent
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
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategorySuggest
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var adapterCateRecipeRV: RecipeCateListAdapter? = null
    var adapterRecipeTodayEatRV: RecipeListAdapter? = null
    var adapterRecipeMostLikesRV: RecipeListAdapter? = null
    val db = Firebase.firestore
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
        adapterRecipeTodayEatRV = RecipeListAdapter(recipeTodayEat)
        recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
        recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        var recipeMostLikes = generateRecipeMostLikesData() //implemened below
        adapterRecipeMostLikesRV = RecipeListAdapter(recipeMostLikes)
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

// Create a new user with a first, middle, and last name
        val user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }

    }
    private fun generateCateRecipeData(): ArrayList<RecipeCategorySuggest> {
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
    private fun generateRecipeTodayEatData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe: FoodRecipe = FoodRecipe(1, "Canh khổ qua nhồi thịt",
            R.drawable.khoquanhoithit, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Canh chua cá lóc", R.drawable.canhcaloc, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe = FoodRecipe(1, "Bò sốt me", R.drawable.bosotme, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bò kho", R.drawable.bokho, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bún bò huế", R.drawable.bunbohue, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bưởi trộn khô gà", R.drawable.buoitronkhoga, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

    private fun generateRecipeMostLikesData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe = FoodRecipe(1, "Cơm rang dưa bò", R.drawable.comrangduabo, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mì trứng xào bò", R.drawable.mitrungxaobo, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe= FoodRecipe(1, "Mì quảng gà", R.drawable.miquangga, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt xiên nướng cà ri", R.drawable.thitxiennuong, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mực nướng Malaysia", R.drawable.mucnuongmalaysia, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt ba chỉ nướng mật ong", R.drawable.thitbachimatong, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }
}