package com.example.a4tfoodfrenzy.Controller.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Helper.GenerateDBModel
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class MainActivity : AppCompatActivity() {
    private var adapterCateRecipeRV: RecipeCateListAdapter? = null
    val db = Firebase.firestore
    val generateDBModel = GenerateDBModel(this)
    private val REQUEST_RECIPE_DETAILS = 2222
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var btnViewMoreTodayEat: Button
    private lateinit var btnViewMoreMostLikes: Button
    private lateinit var recipeMostLikesRV: RecyclerView
    private lateinit var recipeTodayEatRV: RecyclerView
    private lateinit var cateRecipeRV: RecyclerView
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cateRecipeRV = findViewById(R.id.cateRecipeRV)
        recipeTodayEatRV = findViewById(R.id.recipeTodayEatRV)
        recipeMostLikesRV = findViewById(R.id.recipeMostLikesRV)
        bottomNavigationView = findViewById(R.id.botNavbar)
        btnViewMoreTodayEat = findViewById(R.id.btnViewMoreTodayEat)
        btnViewMoreMostLikes = findViewById(R.id.btnViewMoreMostLikes)

        setAdapterRecipeCategory()
        fetchDatabaseFirebase()
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recipeTodayEatRV.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recipeMostLikesRV.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        findViewById<LinearLayout>(R.id.searchLL).setOnClickListener {
            if (!DBManagement.existAfterSearch) {
                val intent = Intent(this, SearchScreen::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            } else {
                val intent = Intent(this, AfterSearchActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            }
        }
        btnViewMoreTodayEat.setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            intent.putExtra("keySearch", "Hôm nay ăn gì")
            intent.putExtra("pageSearch", "home")
            intent.putExtra("typeSearch", "recipeTodayEat")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        btnViewMoreMostLikes.setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            intent.putExtra("keySearch", "Các món được yêu thích nhất")
            intent.putExtra("pageSearch", "home")
            intent.putExtra("typeSearch", "recipeMostLikes")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }

        val menu = bottomNavigationView.menu

        menu.findItem(R.id.home).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    true
                }
                R.id.search -> {
                    if (!DBManagement.existAfterSearch) {
                        val intent = Intent(this, SearchScreen::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    } else {
                        val intent = Intent(this, AfterSearchActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    }
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    true
                }
                R.id.profile -> {
                    if (DBManagement.user_current == null) {
                        val intent = Intent(this, LogoutActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    } else {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    }
                    true
                }
                else -> false
            }
        }
//        generateDBModel.generateDatabaseRecipeFoodCate()
//        generateDBModel.generateDatabaseRecipeComment()
//        generateDBModel.generateDatabaseRecipeDiets()
//        generateDBModel.generateDatabaseRecipeFood()
//        generateDBModel.generateDatabaseUsers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_RECIPE_DETAILS) {
            if (resultCode === Activity.RESULT_OK) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Hủy đăng ký listener
        DBManagement.destroyListener()
    }

    fun setAdapterRecipeCategory() {
        var cateRecipeList = generateCateRecipeData() //implemened below
        adapterCateRecipeRV = RecipeCateListAdapter(cateRecipeList, true, false)
        cateRecipeRV!!.adapter = adapterCateRecipeRV
        cateRecipeRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterCateRecipeRV!!.onItemClick = { recipeCate, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            if (recipeCate.recipeCateTitle.equals("Đồ uống")) {
                intent.putExtra("keySearch", "Thức uống")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Nấu nhanh")) {
                intent.putExtra("keySearch", "Dưới 30 phút")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "cookTime")
            } else if (recipeCate.recipeCateTitle.equals("Đồ ăn vặt")) {
                intent.putExtra("keySearch", "Ăn vặt")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Điểm tâm")) {
                intent.putExtra("keySearch", "Điểm tâm")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Món chính")) {
                intent.putExtra("keySearch", "Món chính")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateTitle.equals("Khai vị")) {
                intent.putExtra("keySearch", "Khai vị")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            }
            startActivity(intent)
        }
    }
    fun fetchDatabaseFirebase() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            DBManagement.addListenerChangeDataUserCurrent { user ->
                if (user.email.equals("")) {
                    DBManagement.fetchDataUserCurrent { }
                } else if (user.isAdmin) {
                    val intent = Intent(this, AdminDashboard::class.java)
                    startActivity(intent)
                }
            }
        }
        DBManagement.addListenerChangeDataFoodRecipe { foodRecipes ->
            if (foodRecipes.isEmpty()) {
                DBManagement.fetchDataFoodRecipe { foodRecipeList ->
                    DBManagement.addListenerChangeDataUser { users ->
                        setRecipeListAdapter(
                            RecipeListAdapter(
                                this,
                                generateRecipeTodayEatData(foodRecipeList, users)
                            ), recipeTodayEatRV
                        )
                        btnViewMoreTodayEat.visibility = View.VISIBLE

                        setRecipeListAdapter(
                            RecipeListAdapter(
                                this,
                                generateRecipeMostLikesData(foodRecipeList, users)
                            ), recipeMostLikesRV
                        )
                        btnViewMoreMostLikes.visibility = View.VISIBLE
                    }
                }
            } else {
                DBManagement.addListenerChangeDataUser { users ->
                    setRecipeListAdapter(
                        RecipeListAdapter(
                            this,
                            generateRecipeTodayEatData(foodRecipes, users)
                        ), recipeTodayEatRV
                    )
                    btnViewMoreTodayEat.visibility = View.VISIBLE

                    setRecipeListAdapter(
                        RecipeListAdapter(
                            this,
                            generateRecipeMostLikesData(foodRecipes, users)
                        ), recipeMostLikesRV
                    )
                    btnViewMoreMostLikes.visibility = View.VISIBLE
                }
            }
        }
        DBManagement.addListenerChangeDataRecipeCategories { recipeCategories ->
            if (recipeCategories.isEmpty()) {
                DBManagement.fetchDataRecipeCate { }
            }
        }
        DBManagement.addListenerChangeDataRecipeComment { recipeComments ->
            if (recipeComments.isEmpty()) {
                DBManagement.fetchDataRecipeCmt { }
            }
        }
        DBManagement.addListenerChangeDataRecipeDiets { recipeDiets ->
            if (recipeDiets.isEmpty()) {
                DBManagement.fetchDataRecipeDiet { }
            }
        }
    }

    fun generateCateRecipeData(): ArrayList<RecipeCategorySuggest> {
        var result = ArrayList<RecipeCategorySuggest>()
        var typeRecipe = RecipeCategorySuggest(
            "Nấu nhanh",
            R.drawable.donghonaunhanh
        )
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ uống", R.drawable.douonghome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Món chính", R.drawable.monchinh)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ ăn vặt", R.drawable.doanvathome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Điểm tâm", R.drawable.banhmihome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Khai vị", R.drawable.khaivihome)
        result.add(typeRecipe)

        return result
    }

    fun generateRecipeTodayEatData(
        recipeList: ArrayList<FoodRecipe>,
        userList: ArrayList<User>
    ): LinkedHashMap<FoodRecipe, User> {
        val result = LinkedHashMap<FoodRecipe, User>()
        val randomIndexSet = mutableSetOf<Int>()

        while (randomIndexSet.size != 6) {
            val randomIndex = (0 until recipeList.size).random()
            if (!randomIndexSet.contains(randomIndex) && recipeList[randomIndex].isPublic) {
                randomIndexSet.add(randomIndex)
                for (user in userList) {
                    if (user.myFoodRecipes.contains(recipeList[randomIndex].id)) {
                        result[recipeList[randomIndex]] = user
                        break
                    }
                }
            }
        }
        return result
    }

    fun generateRecipeMostLikesData(
        recipeList: ArrayList<FoodRecipe>,
        userList: ArrayList<User>
    ): LinkedHashMap<FoodRecipe, User> {
        var result = LinkedHashMap<FoodRecipe, User>()

        val sortedRecipes = recipeList.sortedByDescending { it.numOfLikes }
        var index = 0
        var numberRecipe = 0
        while (numberRecipe != 6) {
            if (sortedRecipes[index].isPublic) {
                for (user in userList) {
                    if (user.myFoodRecipes.contains(sortedRecipes[index].id)) {
                        result.put(sortedRecipes[index], user)
                        break
                    }
                }
                numberRecipe++
            }
            index++
        }
        return result
    }

    fun setRecipeListAdapter(adapterRecipeList: RecipeListAdapter?, recipeRV: RecyclerView) {
        recipeRV.adapter = adapterRecipeList
        recipeRV.layoutManager = GridLayoutManager(this, 2)
        adapterRecipeList!!.onItemClick = { foodRecipe, user, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            intent.putExtra("foodRecipe", foodRecipe)
            intent.putExtra("user", user)
            startActivityForResult(intent, REQUEST_RECIPE_DETAILS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.home).isChecked = true
    }

}