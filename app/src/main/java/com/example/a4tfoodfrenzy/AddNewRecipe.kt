package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddNewRecipe : AppCompatActivity() {
    private lateinit var addRecipeBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)
        setContinueBtn()

    }
    private fun setContinueBtn()
    {
        addRecipeBtn=findViewById(R.id.addRecipeBtn)
        addRecipeBtn.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity1::class.java)
            startActivity(intent)
        }
    }
}