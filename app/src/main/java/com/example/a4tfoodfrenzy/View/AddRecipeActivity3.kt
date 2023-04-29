package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.ListIngredientAdapter
import com.example.a4tfoodfrenzy.Api.Food
import com.example.a4tfoodfrenzy.Api.NinjasApiService
import com.example.a4tfoodfrenzy.Api.TranslateUtil
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.Model.RecipeDiet
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class AddRecipeActivity3 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var addIngredient: Button
    private lateinit var listIngredient: ArrayList<RecipeIngredient>
    private lateinit var listIngredientRecyclerView:RecyclerView
    private lateinit var listIngredientAdapter: ListIngredientAdapter
    private lateinit var cate:String
    private lateinit var foodRecipe:FoodRecipe
    private lateinit var sharedPreferences: SharedPreferences



    private val ADD_INGREDIENT_REQUEST_CODE = 100
    private val EDIT_INGREDIENT_REQUEST_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe3)
        initView()

        setupRecyclerView()
        restoreData()

        recieveData()
        setBackToolbar()
        setCloseToolbar()
        setupContinueButton()
        setOnItemClick()
        optionMenu()
        setupAddIngredientButton()
    }

    private fun initView() {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
        listIngredientRecyclerView=findViewById(R.id.listIgredient)
    }


    private fun setupRecyclerView() {
        listIngredient = arrayListOf<RecipeIngredient>()
        listIngredientAdapter = ListIngredientAdapter(this, listIngredient)
        listIngredientRecyclerView.adapter = listIngredientAdapter
        listIngredientRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private fun restoreData()
    {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        if(sharedPreferences.contains("listIngredient"))
        {
            val ingredient = sharedPreferences.getString("listIngredient", null)
            val list: ArrayList<RecipeIngredient>? = Gson().fromJson(ingredient, object : TypeToken<ArrayList<RecipeIngredient>>() {}.type)
            if(list!=null)
            {
                listIngredient.clear()
                listIngredient.addAll(list)
                listIngredientAdapter.notifyDataSetChanged()
            }

        }
    }
    private fun saveData()
    {
        val editor=sharedPreferences.edit()
        val gson = Gson()
        val json=gson.toJson(listIngredient)
        editor.putString("listIngredient", json)
        editor.apply()
    }
    private fun deleteAllSharePreference()
    {
        val editor=sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener {
            saveData()
            finish() }
    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    deleteAllSharePreference()
                    val intent = Intent(this, AddNewRecipe::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finishAffinity()
                    true
                }
                else -> false
            }
        }
    }

    private fun optionMenu() {
        listIngredientAdapter.onButtonClick = { view, ingredient ->
            val popUpMenu = PopupMenu(this, view)
            popUpMenu.menuInflater.inflate(R.menu.menu_ingredient, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.update-> {
                        sendDataToUpdate(ingredient)
                        true
                    }
                    R.id.delete-> {
                        var helperFunctionDB=HelperFunctionDB(this)
                        helperFunctionDB.showWarningAlert("Xóa nguyên liệu",
                            "Bạn có chắc là sẽ xóa nguyên liệu này?")
                        {confirm ->
                            if(confirm)
                            {
                                deleteIngredient(ingredient)
                            }
                        }
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }

    }

    private fun sendDataToUpdate(ingredient: RecipeIngredient)
    {
        val intent = Intent(this, AddIngredient::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("index", listIngredient.indexOf(ingredient))
        intent.putExtra("ingredient", ingredient)
        startActivityForResult(intent, EDIT_INGREDIENT_REQUEST_CODE)
    }
    private fun setOnItemClick()
    {
        listIngredientAdapter.onItemClick={ingredient ->
            sendDataToUpdate(ingredient)

        }
    }
    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            if(validateAddIngredient()) {
                val intent = Intent(this, AddRecipeActivity4::class.java)
                saveData()
                sendData(intent)
                startActivity(intent)
            }
        }
    }
    private fun validateAddIngredient():Boolean
    {
        if(listIngredient.isNullOrEmpty())
        {
            HelperFunctionDB(this).showRemindAlert("Bạn vui lòng thêm nguyên liệu")
            return false

        }
        return true
    }
    private fun sendData(intent: Intent)
    {
        foodRecipe.recipeIngres=listIngredient

        //gửi loại món ăn sang màn hình 3
        intent.putExtra("cate",cate)

        //gửi đối tượng FoodRecipe
        intent.putExtra("foodRecipe",foodRecipe)
    }
    private fun recieveData()
    {
        foodRecipe=intent.getParcelableExtra<FoodRecipe>("foodRecipe") as FoodRecipe
        cate= intent.getStringExtra("cate").toString()

        if(!foodRecipe.recipeIngres.isEmpty())
        {
            listIngredient.clear()
            listIngredient.addAll(foodRecipe.recipeIngres)
            listIngredientAdapter.notifyDataSetChanged()
        }
    }


    private fun setupAddIngredientButton() {
        addIngredient = findViewById(R.id.addIngredientBtn)
        addIngredient.setOnClickListener {
            val intent = Intent(this, AddIngredient::class.java)
            intent.putExtra("mode", "add")
            startActivityForResult(intent, ADD_INGREDIENT_REQUEST_CODE)
        }
    }

    private fun deleteIngredient(ingredient: RecipeIngredient) {
        listIngredient.remove(ingredient)
        listIngredientAdapter.notifyDataSetChanged()
    }

    private fun handleAddIngredient(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val ingredient =
                data?.getParcelableExtra<RecipeIngredient>("ingredient") as RecipeIngredient
            listIngredient.add(ingredient)
            listIngredientAdapter.notifyDataSetChanged()
        }
    }

    private fun handleUpdateIngredient(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val ingredient =
                data?.getParcelableExtra<RecipeIngredient>("ingredient") as RecipeIngredient
            val index = data?.getIntExtra("index", -1)
            if (index != null) {
                listIngredient.set(index, ingredient)
            }
            listIngredientAdapter.notifyDataSetChanged()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_INGREDIENT_REQUEST_CODE -> handleAddIngredient(resultCode, data)
            EDIT_INGREDIENT_REQUEST_CODE -> handleUpdateIngredient(resultCode, data)
        }

    }


}