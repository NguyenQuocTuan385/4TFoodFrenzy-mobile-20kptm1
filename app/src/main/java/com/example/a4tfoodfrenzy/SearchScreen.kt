package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast

class SearchScreen : AppCompatActivity() {
    var adapter: SearchRecipeGridAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_screen)

        val grid = findViewById<GridView>(R.id.typeRecipeGV)
        var companyList = generateCompanyData() //implemened below
        adapter = SearchRecipeGridAdapter(this, companyList)
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
        company.titleRecipe = "Pasta"
        company.recipeImage = R.drawable.pasta
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Món gà"
        company.recipeImage = R.drawable.chicken
        result.add(company)


        company = SearchRecipe()
        company.titleRecipe = "Nấu nhanh"
        company.recipeImage = R.drawable.time
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Đồ ăn vặt"
        company.recipeImage = R.drawable.fastfood
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Đồ chay"
        company.recipeImage = R.drawable.diet
        result.add(company)

        company = SearchRecipe()
        company.titleRecipe = "Bữa sáng"
        company.recipeImage = R.drawable.breakfast
        result.add(company)
        return result
    }
}