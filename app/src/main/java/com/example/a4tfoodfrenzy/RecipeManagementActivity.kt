package com.example.a4tfoodfrenzy

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeManagementActivity: AppCompatActivity() {
    private lateinit var list:ArrayList<RecipeFood>
    private lateinit var listAdapter: DraftRecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var draftText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_management)
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
    }
}