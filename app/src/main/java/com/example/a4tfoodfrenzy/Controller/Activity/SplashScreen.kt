package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList
import java.util.LinkedHashMap

class SplashScreen : AppCompatActivity() {
    private lateinit var btnViewMoreTodayEat: Button
    private lateinit var btnViewMoreMostLikes: Button
    private lateinit var recipeMostLikesRV: RecyclerView
    private lateinit var recipeTodayEatRV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        fetchDatabaseFirebase()
    }
    fun fetchDatabaseFirebase() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            DBManagement.addListenerChangeDataUserCurrent { user ->
                if (!user.isAdmin) {
                    Handler().postDelayed({
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },2000)

                }
                else if (user.isAdmin) {
                    Handler().postDelayed({
                        val intent = Intent(this, AdminDashboard::class.java)
                        startActivity(intent)
                        finish()
                    },2000)
                }
            }
        }
        if (FirebaseAuth.getInstance().currentUser != null) {
            DBManagement.addListenerChangeDataUserCurrent { user ->
            }
        }
        DBManagement.addListenerChangeDataFoodRecipe { foodRecipes ->
            if (foodRecipes.isEmpty()) {
                DBManagement.fetchDataFoodRecipe { foodRecipeList ->
                    DBManagement.addListenerChangeDataUser { users ->
                    }
                }
            } else {
                DBManagement.addListenerChangeDataUser { users ->

                }
            }
        }
        DBManagement.addListenerChangeDataRecipeCategories { recipeCategories ->
            if (recipeCategories.isEmpty()) {
                DBManagement.fetchDataRecipeCate { }
            }
        }
        DBManagement.addListenerChangeDataRecipeComment { recipeComments ->
            if (recipeComments.isEmpty()) {
                DBManagement.fetchDataRecipeCmt { }
            }
        }
        DBManagement.addListenerChangeDataRecipeDiets { recipeDiets ->
            if (recipeDiets.isEmpty()) {
                DBManagement.fetchDataRecipeDiet { }
            }
        }
    }
}