package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.AddStepAdapter
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity4 : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var addStepBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe4)
        setupRecyclerView()
        initToolbar()
        setBackToolbar()
        setCloseToolbar()
        setupContinueButton()
        setupAddStepButton()
    }

    private fun createStepList():ArrayList<RecipeCookStep>{
        val list= arrayListOf<RecipeCookStep>()
        for (i in 1..6) {
            list.add(RecipeCookStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa", R.drawable.monan1))
        }
        return list
    }
    private fun setupRecyclerView() {
        val listStep = findViewById<RecyclerView>(R.id.listStep)
        val list = createStepList()
        val adapter = AddStepAdapter(this, list)
        listStep.adapter = adapter
        listStep.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById<MaterialToolbar>(R.id.toolbarAddRecipe)

    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }

    }
    private fun setCloseToolbar()
    {
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

    private fun setupContinueButton() {
        continueBtn= findViewById<Button>(R.id.continueBtn)
        continueBtn.setOnClickListener {

        }
    }

    private fun setupAddStepButton() {
        addStepBtn = findViewById<Button>(R.id.addStepBtn)
        addStepBtn.setOnClickListener {
            val intent = Intent(this, AddStepActivity::class.java)
            startActivity(intent)
        }
    }

}