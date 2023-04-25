package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CheckboxAdapter
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.example.a4tfoodfrenzy.Model.RecipeDiet
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity2 : AppCompatActivity() {
    private lateinit var timedropdown:AutoCompleteTextView
    private lateinit var cateFoodDropdown:AutoCompleteTextView
    private lateinit var list_checkbox:RecyclerView
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var dietList:ArrayList<Long>
    private lateinit var ration:EditText
    private lateinit var foodRecipe:FoodRecipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        dietList= arrayListOf()
        ration=findViewById(R.id.amountServingEdit)
        recieveData()
        initToolbar()
        setCateFoodDropdown()
        setupTimeDropdown()
        setDietCheckbox()
        setBackToolbar()
        setupContinueButton()
        setCloseToolbar()
    }

    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }
    private fun setCateFoodDropdown() {
        var items : ArrayList<String> = ArrayList()
        for (recipeCateTemp:RecipeCategory in DBManagement.recipeCateList) {
            items.add(recipeCateTemp.recipeCateName)
        }
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        cateFoodDropdown = findViewById(R.id.dropdown_typeFood)
        cateFoodDropdown.setAdapter(adapter)
    }
    private fun setupTimeDropdown() {
        val items = listOf("Dưới 15 phút", "Dưới 30 phút", "Dưới 45 phút", "Dưới 1 tiếng", "Trên 1 tiếng")
        timedropdown = findViewById(R.id.dropdown_time)
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        timedropdown.setAdapter(adapter)
    }
    private fun setDietCheckbox()
    {
        list_checkbox = findViewById(R.id.list_checkbox)
        var adapter=CheckboxAdapter(this,DBManagement.recipeDietList)
        list_checkbox.adapter= adapter
        dietList=adapter.getDietList()
        list_checkbox.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }
    private fun validateInput():Boolean
    {
        if(ration.text.isNullOrEmpty())
        {
            HelperFunctionDB(this).showRemindAlert("Bạn vui lòng nhập khẩu phần")
            return false

        }
        return true
    }

    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            if(validateInput()) {
                val intent = Intent(this, AddRecipeActivity3::class.java)
                sendData(intent)
                startActivity(intent)
            }


        }
    }

    private fun sendData(intent: Intent)
    {
        // nhận data từ màn hình 1

        //thêm data vào foodRecipe
        foodRecipe.ration=ration.text.toString().toInt()
        foodRecipe.recipeDiets=dietList
        foodRecipe.cookTime=timedropdown.text.toString()

        //gửi loại món ăn sang màn hình 3
        intent.putExtra("cate",cateFoodDropdown.text.toString())

        //gửi đối tượng FoodRecipe
        intent.putExtra("foodRecipe",foodRecipe)
    }
    private fun recieveData()
    {
        foodRecipe=intent.getParcelableExtra<FoodRecipe>("foodRecipe") as FoodRecipe

    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    startActivity(Intent(this, AddNewRecipe::class.java))
                    true
                }
                else -> false
            }
        }
    }

}