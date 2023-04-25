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
import com.example.a4tfoodfrenzy.Model.DBManagement
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
    private lateinit var amountServingEdit:EditText
    private lateinit var name:String
    private lateinit var mainImage:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)
        dietList= arrayListOf()
        amountServingEdit=findViewById(R.id.amountServingEdit)
        recieveData()
        initToolbar()
        setCateFoodDropdown()
        recieveData()
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

    private fun setupContinueButton() {
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            val intent=Intent(this, AddRecipeActivity3::class.java)
            sendData(intent)
            startActivity(intent)


        }
    }
    private fun sendData(intent: Intent)
    {
        intent.putExtra("name",name)
        intent.putExtra("mainImage",mainImage)
        intent.putExtra("amountServing",amountServingEdit.text.toString())
        intent.putExtra("diet",dietList.toLongArray())
        intent.putExtra("cate",cateFoodDropdown.text.toString())
        intent.putExtra("time",timedropdown.text.toString())
    }
    private fun recieveData()
    {
        name= intent.getStringExtra("name").toString()
        mainImage= intent.getStringExtra("mainImage").toString()
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