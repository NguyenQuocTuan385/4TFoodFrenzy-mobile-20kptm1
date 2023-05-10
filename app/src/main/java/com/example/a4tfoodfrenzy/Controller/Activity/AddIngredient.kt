package com.example.a4tfoodfrenzy.Controller.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddIngredient : AppCompatActivity() {
    private lateinit var toolbarAddIngredient: MaterialToolbar
    private lateinit var unitIngredientEdit: EditText
    private lateinit var amount:EditText
    private lateinit var ingredientName:EditText
    private var index:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)

        initViews()
        initListeners()
        val mode = intent.getStringExtra("mode")
        if(mode.equals("edit")) {
            val ingredient: RecipeIngredient? = intent.getParcelableExtra("ingredient")
            index=intent.getIntExtra("index",-1)
            ingredientName.setText(ingredient?.ingreName)
            amount.setText(ingredient?.ingreQuantity.toString())
            unitIngredientEdit.setText(ingredient?.ingreUnit)
        }

    }

    private fun initViews() {
        toolbarAddIngredient = findViewById(R.id.toolbarAddIngredient)
        unitIngredientEdit = findViewById(R.id.unitIngredientEdit)
        ingredientName=findViewById(R.id.ingredientName)
        amount=findViewById(R.id.amount)
    }

    private fun initListeners() {
        setBackToolbar()
        setCloseToolbar()
        //setUnitPickerListener()
    }

    private fun setBackToolbar() {
        toolbarAddIngredient.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setCloseToolbar() {
        toolbarAddIngredient.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    addIngredient()
                    // Xử lý khi người dùng chọn Save
                    true
                }
                else -> false
            }
        }
    }

    private fun addIngredient()
    {
        val name=ingredientName.text.toString()
        val amount=(amount.text).toString().toDouble()
        val unit=unitIngredientEdit.text.toString()
        val recipeIngredient=RecipeIngredient(amount,name,unit,0.0)
        val intent=Intent()
        intent.putExtra("ingredient",recipeIngredient)
        intent.putExtra("index",index)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
