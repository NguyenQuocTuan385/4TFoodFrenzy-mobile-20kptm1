package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Model.RecipeCategorySuggest
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchScreen : AppCompatActivity() {
    var adapterTypeRecipeRV: RecipeCateListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_screen)

        val cateRecipeRV = findViewById<RecyclerView>(R.id.cateRecipeRV)
        var searchET = findViewById<EditText>(R.id.searchET)

        var cateRecipeList = generateCateRecipeData() //implemened below
        adapterTypeRecipeRV = RecipeCateListAdapter(cateRecipeList, false, true)
        cateRecipeRV!!.layoutManager = GridLayoutManager(this, 3)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        cateRecipeRV!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        cateRecipeRV!!.adapter = adapterTypeRecipeRV

        searchET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val intent = Intent(this, AfterSearchActivity::class.java)
                startActivity(intent)
            }
            true
        }
        adapterTypeRecipeRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnMore1HCook).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess1HCook).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess45MCook).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess30MCook).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess15MCook).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnFastFood).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnDrink).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnDessert).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnMainFood).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnAppetizer).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.search).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
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
    }

    private fun generateCateRecipeData(): ArrayList<RecipeCategorySuggest> {
        var result = ArrayList<RecipeCategorySuggest>()

        var cateRecipe = RecipeCategorySuggest("Đồ uống", R.drawable.drink)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Món gà", R.drawable.chicken)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Nấu nhanh", R.drawable.time)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Đồ ăn vặt", R.drawable.fastfood)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Ăn chay", R.drawable.diet)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Món chính", R.drawable.mainfood)
        result.add(cateRecipe)

        return result
    }
}