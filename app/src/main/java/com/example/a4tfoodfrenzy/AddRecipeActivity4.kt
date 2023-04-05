package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRecipeActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe4)

        val listStep=findViewById<RecyclerView>(R.id.listStep)
        var list=ArrayList<AddStep>()
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))
        list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa",R.drawable.monan1))


        val adapter=AddStepAdapter(this,list)
        listStep.adapter=adapter
        listStep.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }
}