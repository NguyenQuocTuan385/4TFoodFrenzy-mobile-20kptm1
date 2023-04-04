package com.example.a4tfoodfrenzy

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeManagementActivity: AppCompatActivity() {
    private lateinit var list:ArrayList<RecipeFood>
    private lateinit var listAdapter: DraftRecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var draftText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_management)
        recyclerView=findViewById(R.id.recyclerView)
        draftText=findViewById(R.id.draftText)
        list=ArrayList<RecipeFood>()
        var recipe=RecipeFood("Đậu đua xào thịt heo",R.drawable.monan)
        list.add(recipe)
        recipe=RecipeFood("Thịt bò nấu canh chua",R.drawable.monan)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        list.add(recipe)
        draftText.text="${list.size} món nháp"
        listAdapter= DraftRecipeAdapter(this,list)
        recyclerView.adapter=listAdapter
        recyclerView.layoutManager = GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.addRecipe).isChecked = true

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

                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
}