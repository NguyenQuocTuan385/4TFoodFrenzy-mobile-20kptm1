package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var adapterTypeRecipeRV: RecipeListAdapter? = null
    var adapterRecipeTodayEatRV: RecipeListAdapter? = null
    var adapterRecipeMostLikesRV: RecipeListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val typeRecipeRV = findViewById<RecyclerView>(R.id.typeRecipeRV)
        var typeRecipeList = generateTypeRecipeData() //implemened below
        adapterTypeRecipeRV = RecipeListAdapter(typeRecipeList, false, true, false)
        typeRecipeRV!!.adapter = adapterTypeRecipeRV
        typeRecipeRV!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        val recipeTodayEatRV = findViewById<RecyclerView>(R.id.recipeTodayEatRV)
        var recipeTodayEat = generateRecipeTodayEatData() //implemened below
        adapterRecipeTodayEatRV = RecipeListAdapter(recipeTodayEat, true, false, false)
        recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
        recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)

        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        var recipeMostLikes = generateRecipeMostLikesData() //implemened below
        adapterRecipeMostLikesRV = RecipeListAdapter(recipeMostLikes, true, false, false)
        recipeMostLikesRV!!.adapter = adapterRecipeMostLikesRV
        recipeMostLikesRV!!.layoutManager = GridLayoutManager(this, 3)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.home).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    true
                }
                R.id.search -> {
                    val intent = Intent(this, SearchScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, RecipeManagementActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, TrangCaNhan::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
    private fun generateTypeRecipeData(): ArrayList<RecipeRender> {
        var result = ArrayList<RecipeRender>()
        var typeRecipe: RecipeRender = RecipeRender()
        typeRecipe.titleRecipe = "Nấu nhanh"
        typeRecipe.recipeImage = R.drawable.donghonaunhanh
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Đồ uống"
        typeRecipe.recipeImage = R.drawable.douonghome
        result.add(typeRecipe)


        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Món chính"
        typeRecipe.recipeImage = R.drawable.monchinh
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Đồ ăn vặt"
        typeRecipe.recipeImage = R.drawable.doanvathome
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Hải sản"
        typeRecipe.recipeImage = R.drawable.haisanhome
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Rau củ"
        typeRecipe.recipeImage = R.drawable.raucuhome
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Điểm tâm"
        typeRecipe.recipeImage = R.drawable.banhmihome
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Khai vị"
        typeRecipe.recipeImage = R.drawable.khaivihome
        result.add(typeRecipe)

        return result
    }
    private fun generateRecipeTodayEatData(): ArrayList<RecipeRender> {
        var result = ArrayList<RecipeRender>()

        var typeRecipe: RecipeRender = RecipeRender()
        typeRecipe.titleRecipe = "Canh khổ qua nhồi thịt"
        typeRecipe.recipeImage = R.drawable.khoquanhoithit
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Canh chua cá lóc"
        typeRecipe.recipeImage = R.drawable.canhcaloc
        result.add(typeRecipe)


        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bò sốt me"
        typeRecipe.recipeImage = R.drawable.bosotme
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bò kho"
        typeRecipe.recipeImage = R.drawable.bokho
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bún bò huế"
        typeRecipe.recipeImage = R.drawable.bunbohue
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Bưởi trộn khô gà"
        typeRecipe.recipeImage = R.drawable.buoitronkhoga
        result.add(typeRecipe)

        return result
    }

    private fun generateRecipeMostLikesData(): ArrayList<RecipeRender> {
        var result = ArrayList<RecipeRender>()

        var typeRecipe: RecipeRender = RecipeRender()
        typeRecipe.titleRecipe = "Cơm rang dưa bò"
        typeRecipe.recipeImage = R.drawable.comrangduabo
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Mì trứng xào bò"
        typeRecipe.recipeImage = R.drawable.mitrungxaobo
        result.add(typeRecipe)


        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Mì quảng gà"
        typeRecipe.recipeImage = R.drawable.miquangga
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Thịt xiên nướng cà ri"
        typeRecipe.recipeImage = R.drawable.thitxiennuong
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Mực nướng Malaysia"
        typeRecipe.recipeImage = R.drawable.mucnuongmalaysia
        result.add(typeRecipe)

        typeRecipe = RecipeRender()
        typeRecipe.titleRecipe = "Thịt ba chỉ nướng mật ong"
        typeRecipe.recipeImage = R.drawable.thitbachimatong
        result.add(typeRecipe)

        return result
    }
}