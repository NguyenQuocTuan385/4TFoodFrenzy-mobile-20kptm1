package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.Model.RecipeDiet
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity3 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var addIngredient: Button
    private lateinit var listIngredient: ArrayList<RecipeIngredient>
    private lateinit var listIngredientAdapter: ListIngredientAdapter
    private lateinit var cate:String
    private lateinit var foodRecipe:FoodRecipe



    private val ADD_INGREDIENT_REQUEST_CODE = 100
    private val EDIT_INGREDIENT_REQUEST_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe3)
        recieveData()
        initToolbar()
        setBackToolbar()
        setCloseToolbar()
        setupRecyclerView()
        setupContinueButton()
        setOnItemClick()
        optionMenu()
        setupAddIngredientButton()
    }

    private fun initToolbar() {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }


    private fun setupRecyclerView() {
        val listIngredientRecyclerView = findViewById<RecyclerView>(R.id.listIgredient)
        listIngredient = arrayListOf<RecipeIngredient>()
        listIngredientAdapter = ListIngredientAdapter(this, listIngredient)
        listIngredientRecyclerView.adapter = listIngredientAdapter
        listIngredientRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
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