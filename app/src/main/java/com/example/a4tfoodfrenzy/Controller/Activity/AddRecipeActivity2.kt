package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.AddRecipeAdapter.CheckboxAdapter
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class AddRecipeActivity2 : AppCompatActivity() {
    private lateinit var timedropdown:AutoCompleteTextView
    private lateinit var cateFoodDropdown:AutoCompleteTextView
    private lateinit var list_checkbox:RecyclerView
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private var dietList:ArrayList<Long>?=null
    private lateinit var ration:EditText
    private lateinit var foodRecipe:FoodRecipe
    private lateinit var adapter: CheckboxAdapter
    private var category:String?=null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        initView()
        setDietCheckbox()
        restoreData()
        recieveData()
        setCateFoodDropdown()
        setupTimeDropdown()
        setBackToolbar()
        setupContinueButton()
        setCloseToolbar()
    }

    private fun initView()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
        ration=findViewById(R.id.amountServingEdit)
        timedropdown = findViewById(R.id.dropdown_time)
        list_checkbox = findViewById(R.id.list_checkbox)
        cateFoodDropdown=findViewById(R.id.dropdown_typeFood)
    }
    private fun setCateFoodDropdown() {
        var items : ArrayList<String> = ArrayList()
        for (recipeCateTemp:RecipeCategory in DBManagement.recipeCateList) {
            items.add(recipeCateTemp.recipeCateName)
        }
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        cateFoodDropdown.setAdapter(adapter)
    }
    private fun setupTimeDropdown() {
        val items = listOf("Dưới 15 phút", "Dưới 30 phút", "Dưới 45 phút", "Dưới 60 phút", "Trên 60 phút")
        timedropdown = findViewById(R.id.dropdown_time)
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        timedropdown.setAdapter(adapter)
    }
    private fun setDietCheckbox()
    {
        adapter= CheckboxAdapter(this,DBManagement.recipeDietList)
        list_checkbox.adapter= adapter
        dietList=adapter.getDietList()
        adapter.setDietList(dietList)
        list_checkbox.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener {
            saveData()
            finish()
        }
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
                saveData()
                sendData(intent)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)

            }


        }
    }
    private fun saveData()
    {
        val editor = sharedPreferences.edit()
        val rationText = ration.text.toString()
        val timeText=timedropdown.text.toString()
        val cate=cateFoodDropdown.text.toString()
        if (rationText.isNotBlank()) {
            editor.putInt("ration", rationText.toInt())
            editor.apply()
        }
        if(timeText.isNotBlank())
        {
            editor.putString("time", timeText)
            editor.apply()
        }
        if(cate.isNotBlank())
        {
            editor.putString("cate", cate)
            editor.apply()
        }
        val gson = Gson()
        val json=gson.toJson(dietList)
        editor.putString("diet", json)
        editor.apply()
    }
    private fun restoreData()
    {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        if(sharedPreferences.contains("ration")) {
            val username = sharedPreferences.getInt("ration", 0)
            ration.setText(username.toString())
        }
        if(sharedPreferences.contains("time")) {
            val time= sharedPreferences.getString("time", "")
            timedropdown.setText(time.toString())
        }
        if(sharedPreferences.contains("cate"))
        {
            val cate=sharedPreferences.getString("cate","")
            cateFoodDropdown.setText(cate.toString())
        }
        if(sharedPreferences.contains("diet")) {
            val diet = sharedPreferences.getString("diet", null)
            val list: ArrayList<Long>? = Gson().fromJson(diet, object : TypeToken<ArrayList<Long>>() {}.type)

            if (list != null) {
                dietList?.clear()
                dietList?.addAll(list)
                adapter.notifyDataSetChanged()
            }

        }
    }
    private fun deleteAllSharePreference()
    {
        val editor=sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun sendData(intent: Intent)
    {
        // nhận data từ màn hình 1

        //thêm data vào foodRecipe
        foodRecipe.ration=ration.text.toString().toInt()
        foodRecipe.recipeDiets=dietList
        foodRecipe.cookTime=timedropdown.text.toString()
        Log.d("Diets", foodRecipe.recipeDiets.toString())


        //gửi loại món ăn sang màn hình 3
        intent.putExtra("cate",cateFoodDropdown.text.toString())

        //gửi đối tượng FoodRecipe
        intent.putExtra("foodRecipe",foodRecipe)
    }
    private fun recieveData()
    {
        foodRecipe=intent.getParcelableExtra<FoodRecipe>("foodRecipe") as FoodRecipe
        category=intent.getStringExtra("cate")
        if(foodRecipe.ration!=0)
        {
            ration.setText(foodRecipe.ration.toString())
        }
        if(!foodRecipe.cookTime.isNullOrEmpty())
        {
            timedropdown.setText(foodRecipe.cookTime)
        }


        if(!foodRecipe.recipeDiets.isNullOrEmpty())
        {
            dietList!!.clear()
            foodRecipe.recipeDiets?.let { dietList!!.addAll(it) }
            adapter.notifyDataSetChanged()
        }
        if(!category.equals("null"))
        {
            cateFoodDropdown.setText(category)
        }

    }

    private fun setCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    deleteAllSharePreference()
                    val intent = Intent(this, AddNewRecipe::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    finishAffinity()
                    true
                }
                else -> false
            }
        }
    }

}