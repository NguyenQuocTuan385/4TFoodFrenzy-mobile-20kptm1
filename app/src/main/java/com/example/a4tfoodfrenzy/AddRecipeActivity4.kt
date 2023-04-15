package com.example.a4tfoodfrenzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRecipeActivity4 : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: Toolbar
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

    private fun createStepList():ArrayList<AddStep>{
        val list= arrayListOf<AddStep>()
        for (i in 1..6) {
            list.add(AddStep("Lọc bớt mỡ và rửa sạch miếng thịt lợn sữa", R.drawable.monan1))
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
        toolbarAddRecipe = findViewById<Toolbar>(R.id.toolbarAddRecipe)

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
            val intent = Intent(this, AddRecipeActivity5::class.java)
            startActivity(intent)
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