package com.example.a4tfoodfrenzy.Controller.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeCateAdapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.BroadcastReceiver.InternetConnectionBroadcast
import com.example.a4tfoodfrenzy.Helper.GenerateDBModel
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    // override fun of InternetConnectionBroadcast class specific for this case
    private val myBroadcastReceiver = object : InternetConnectionBroadcast() {
        override fun onReceive(context : Context?, intent : Intent?) {
            if (HelperFunctionDB.isConnectedToInternet(context!!)) {
                findViewById<BottomNavigationView>(R.id.botNavbar).visibility = View.VISIBLE
                findViewById<ScrollView>(R.id.scrollView2).visibility = View.VISIBLE
                findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).visibility = View.VISIBLE
                findViewById<ConstraintLayout>(R.id.noInternetLayout).visibility = View.GONE

                GlobalScope.launch {
                    fetchDatabaseFirebase()
                }
            } else {
                findViewById<BottomNavigationView>(R.id.botNavbar).visibility = View.GONE
                findViewById<ScrollView>(R.id.scrollView2).visibility = View.GONE
                findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.noInternetLayout).visibility = View.VISIBLE

            }
        }
    }

    override fun onStart() {
        super.onStart()
        myBroadcastReceiver.registerInternetConnBroadcast(this@MainActivity)
    }

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

//        if(HelperFunctionDB.isConnectedToInternet(this))
//            fetchDatabaseFirebase()

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recipeTodayEatRV.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recipeMostLikesRV.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        recipeTodayEatRV.isNestedScrollingEnabled = false
        recipeMostLikesRV.isNestedScrollingEnabled = false

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
            intent.putExtra("keySearch", "Chào bạn món mới")
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

        myBroadcastReceiver.unregisterInternetConnBroadcast(this@MainActivity)
        // Hủy đăng ký listener
        DBManagement.destroyListener()
    }

    fun setAdapterRecipeCategory(cateRecipeList: ArrayList<RecipeCategory>) {
//        var cateRecipeList = generateCateRecipeData(recipeCateArr) //implemened below

        adapterCateRecipeRV = RecipeCateListAdapter(this, cateRecipeList, true, false)
        cateRecipeRV!!.adapter = adapterCateRecipeRV
        cateRecipeRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterCateRecipeRV!!.onItemClick = { recipeCate, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            if (recipeCate.recipeCateName.equals("Thức uống")) {
                intent.putExtra("keySearch", "Thức uống")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateName.equals("Ăn vặt")) {
                intent.putExtra("keySearch", "Ăn vặt")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateName.equals("Điểm tâm")) {
                intent.putExtra("keySearch", "Điểm tâm")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateName.equals("Món chính")) {
                intent.putExtra("keySearch", "Món chính")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateName.equals("Khai vị")) {
                intent.putExtra("keySearch", "Khai vị")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            } else if (recipeCate.recipeCateName.equals("Món tráng miệng")) {
                intent.putExtra("keySearch", "Món tráng miệng")
                intent.putExtra("pageSearch", "home")
                intent.putExtra("typeSearch", "recipeCategory")
            }
            startActivity(intent)
        }
    }

    fun fetchDatabaseFirebase() {
        DBManagement.addListenerChangeDataFoodRecipe { foodRecipes ->
            if (foodRecipes.isEmpty()) {
                DBManagement.fetchDataFoodRecipe { foodRecipeList ->
                    DBManagement.addListenerChangeDataUser { users ->
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            DBManagement.addListenerChangeDataUserCurrent { user ->
                                if (DBManagement.isInitialized == false) {
                                    if (user.email.equals("")) {
                                        DBManagement.fetchDataUserCurrent { }
                                    } else if (user.isAdmin) {
                                        val intent = Intent(this, AdminDashboard::class.java)
                                        startActivity(intent)
                                    }
                                    DBManagement.isInitialized = true
                                }
                            }
                        }
                        setRecipeListAdapter(
                            RecipeListAdapter(
                                this,
                                generateRecipeNewData(foodRecipeList, users)
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
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        DBManagement.addListenerChangeDataUserCurrent { user ->
                            if (DBManagement.isInitialized == false) {
                                if (user.email.equals("")) {
                                    DBManagement.fetchDataUserCurrent { }
                                } else if (user.isAdmin) {
                                    val intent = Intent(this, AdminDashboard::class.java)
                                    startActivity(intent)
                                }
                                DBManagement.isInitialized = true
                            }
                        }
                    }
                    setRecipeListAdapter(
                        RecipeListAdapter(
                            this,
                            generateRecipeNewData(foodRecipes, users)
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
            setAdapterRecipeCategory(recipeCategories)
            if (recipeCategories.isEmpty()) {
                DBManagement.fetchDataRecipeCate { recipeCategoriess ->
                    setAdapterRecipeCategory(recipeCategoriess)
                }
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

    fun generateRecipeNewData(
        recipeList: ArrayList<FoodRecipe>,
        userList: ArrayList<User>
    ): LinkedHashMap<FoodRecipe, User> {
        val result = LinkedHashMap<FoodRecipe, User>()

        val sortedRecipes = recipeList.sortedByDescending { it.date }
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


