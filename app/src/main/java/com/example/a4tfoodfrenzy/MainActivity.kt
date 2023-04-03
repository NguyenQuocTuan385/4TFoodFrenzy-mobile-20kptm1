package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var adapter: TypeRecipeHomepage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val grid = findViewById<GridView>(R.id.typeRecipeGV)
        var companyList = generateCompanyData() //implemened below
        adapter = TypeRecipeHomepage(this, companyList)
        grid.adapter = adapter
        grid.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                this, " Selected Company is " + companyList.get(i).titleRecipe,

                Toast.LENGTH_SHORT
            ).show()

        }
    }
    private fun generateCompanyData(): ArrayList<SearchRecipe> {
        var result = ArrayList<SearchRecipe>()
        var company: SearchRecipe = SearchRecipe()
        company.titleRecipe = "Nấu nhanh"
        company.recipeImage = R.drawable.donghonaunhanh
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Đồ uống"
        company.recipeImage = R.drawable.douonghome
        result.add(company)


        company = SearchRecipe()
        company.titleRecipe = "Món chính"
        company.recipeImage = R.drawable.monchinh
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Đồ ăn vặt"
        company.recipeImage = R.drawable.doanvathome
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Hải sản"
        company.recipeImage = R.drawable.haisanhome
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Rau củ"
        company.recipeImage = R.drawable.raucuhome
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Điểm tâm"
        company.recipeImage = R.drawable.banhmihome
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Khai vị"
        company.recipeImage = R.drawable.khaivihome
        result.add(company)

        return result
    }

}