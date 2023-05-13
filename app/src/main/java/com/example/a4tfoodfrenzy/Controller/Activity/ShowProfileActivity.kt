package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListInProfileAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ShowProfileActivity : AppCompatActivity() {
    private lateinit var toolbarProfile:MaterialToolbar
    private lateinit var avatarProfile:ImageView
    private lateinit var name:TextView
    private lateinit var bio:TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeListInProfileAdapter
    private lateinit var recipeRenderArray: HashMap<FoodRecipe, User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)

        initView()
        initAdapter()
        setView()
        backBtnToolbar()
        showRecipeDetail()

    }
    private fun initView()
    {
        toolbarProfile=findViewById(R.id.toolbarProfile)
        avatarProfile=findViewById(R.id.avatarProfile)
        name=findViewById(R.id.name)
        bio=findViewById(R.id.bio)
        recyclerView=findViewById(R.id.recyclerView)
        recipeRenderArray= hashMapOf()
    }
    private fun backBtnToolbar()
    {
        toolbarProfile.setNavigationOnClickListener {
            finish()
        }
    }
    private fun setView()
    {
        val profile=intent.getParcelableExtra<User>("profile")!!

        name.setText(profile.fullname)
        bio.setText(profile.bio)
        toolbarProfile.setTitle(profile.fullname)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val pathReference = storageRef.child(profile.avatar)
        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri.toString())
                .into(avatarProfile)
        }
        getRecipe(profile)
    }
    private fun getRecipe(profile:User) {
        val recipe = profile.myFoodRecipes
        DBManagement.foodRecipeList.forEach {
            if(recipe.contains(it.id)) {
                recipeRenderArray[it] = profile
            }
        }
        adapter.notifyDataSetChanged()

    }
    private fun initAdapter()
    {
        recipeRenderArray= hashMapOf()
        adapter=RecipeListInProfileAdapter(this, recipeRenderArray,false,false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager=GridLayoutManager(this,2)
    }
    private fun showRecipeDetail()
    {
        adapter.onButtonClick={it,foodRecipe->
            val intent= Intent(this,ShowRecipeDetailsActivity::class.java)
            intent.putExtra("foodRecipe",foodRecipe)
            startActivityForResult(intent,1)
        }
    }
}