package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRecipeActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe3)
        var listIngredient=findViewById<RecyclerView>(R.id.listIgredient)
        var ingredient=Ingredient(250,"Hải sản")
        var list=ArrayList<Ingredient>()
        list.add(Ingredient(250,"Hải sản"))
        list.add(Ingredient(100,"Thịt bò"))
        list.add(Ingredient(10,"Muối"))
        list.add(Ingredient(8,"Đường"))

        val adapter=ListIngredientAdapter(this,list)
        listIngredient.adapter=adapter
        listIngredient.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }
}