package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeCateAdapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.RecipeCategorySuggest
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchScreen : AppCompatActivity() {
    var adapterTypeRecipeRV: RecipeCateListAdapter? = null
    lateinit var bottomNavigationView:BottomNavigationView

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
        val intent = Intent(this, AfterSearchActivity::class.java)
        adapterTypeRecipeRV!!.onItemClick = { recipeCate, i ->
            if (recipeCate.recipeCateTitle.equals("Đồ uống")) {
                intent.putExtra("keySearch","Thức uống")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Món gà")) {
                intent.putExtra("keySearch","Gà")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","recipe")
            }
            else if (recipeCate.recipeCateTitle.equals("Nấu nhanh")) {
                intent.putExtra("keySearch","Dưới 30 phút")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","cookTime")
            }
            else if (recipeCate.recipeCateTitle.equals("Đồ ăn vặt")) {
                intent.putExtra("keySearch","Ăn vặt")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Điểm tâm")) {
                intent.putExtra("keySearch","Điểm tâm")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","recipeCategory")
            }
            else if (recipeCate.recipeCateTitle.equals("Món chính")) {
                intent.putExtra("keySearch","Món chính")
                intent.putExtra("pageSearch","search")
                intent.putExtra("typeSearch","recipeCategory")
            }
            startActivity(intent)
        }

        searchET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!searchET.text.isNullOrEmpty()) {
                    val intent = Intent(this, AfterSearchActivity::class.java)
                    intent.putExtra("keySearch",searchET.text.toString())
                    intent.putExtra("pageSearch","search")
                    intent.putExtra("typeSearch","recipe")
                    startActivity(intent)
                }
            }
            true
        }
        findViewById<Button>(R.id.btnMore1HCook).setOnClickListener {
            intent.putExtra("keySearch","Trên 1 tiếng")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","cookTime")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess1HCook).setOnClickListener {
            intent.putExtra("keySearch","Dưới 1 tiếng")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","cookTime")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess45MCook).setOnClickListener {
            intent.putExtra("keySearch","Dưới 45 phút")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","cookTime")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess30MCook).setOnClickListener {
            intent.putExtra("keySearch","Dưới 30 phút")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","cookTime")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnLess15MCook).setOnClickListener {
            intent.putExtra("keySearch","Dưới 15 phút")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","cookTime")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnFastFood).setOnClickListener {
            intent.putExtra("keySearch","Ăn vặt")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","recipeCategory")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnDrink).setOnClickListener {
            intent.putExtra("keySearch","Thức uống")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","recipeCategory")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnDessert).setOnClickListener {
            intent.putExtra("keySearch","Món tráng miệng")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","recipeCategory")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnMainFood).setOnClickListener {
            intent.putExtra("keySearch","Món chính")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","recipeCategory")
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnAppetizer).setOnClickListener {
            intent.putExtra("keySearch","Khai vị")
            intent.putExtra("pageSearch","search")
            intent.putExtra("typeSearch","recipeCategory")
            startActivity(intent)
        }

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.search).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    true
                }
                R.id.search -> {
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    true
                }
                R.id.profile -> {
                    if (DBManagement.user_current == null) {
                        val intent = Intent(this, LogoutActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    } else {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
                    }
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

        cateRecipe = RecipeCategorySuggest("Điểm tâm", R.drawable.english_breakfast)
        result.add(cateRecipe)

        cateRecipe = RecipeCategorySuggest("Món chính", R.drawable.mainfood)
        result.add(cateRecipe)

        return result
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.search).isChecked = true
    }
}