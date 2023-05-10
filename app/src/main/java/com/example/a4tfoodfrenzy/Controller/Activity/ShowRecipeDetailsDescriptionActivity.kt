package com.example.a4tfoodfrenzy.Controller.Activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.StepsAdapter
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R

class ShowRecipeDetailsDescriptionActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details_description)

        val rv = findViewById<RecyclerView>(R.id.stepsRecyclerView)
        val currentFoodRecipe: FoodRecipe? =
            intent.extras?.getParcelable("stepFoodRecipe")
        val stepList = currentFoodRecipe?.recipeSteps
        val adapter = StepsAdapter(stepList!!, this, rv, currentFoodRecipe)

        // set adapter
        rv.adapter = adapter

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // attach recycler view to snap helper
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv)

    }
}