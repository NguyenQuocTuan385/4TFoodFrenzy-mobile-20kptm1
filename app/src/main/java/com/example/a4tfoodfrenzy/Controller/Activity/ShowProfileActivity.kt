package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeListInProfileAdapter
import com.example.a4tfoodfrenzy.BroadcastReceiver.ConstantAction
import com.example.a4tfoodfrenzy.BroadcastReceiver.InternetConnectionBroadcast
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ShowProfileActivity : AppCompatActivity() {
    private lateinit var toolbarProfile:MaterialToolbar
    private lateinit var avatarProfile:ImageView
    private lateinit var name:TextView
    private lateinit var bio:TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeListInProfileAdapter
    private lateinit var recipeRenderArray: HashMap<FoodRecipe, User>
    private lateinit var filterRecipe:SearchView
    private lateinit var profile: User
    private val myBroadcastReceiverProfile = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(ConstantAction.ADD_CMT_RECIPE_ACTION) || intent?.action.equals(ConstantAction.ADD_SAVED_RECIPE_ACTION)) {
               getRecipe(profile)
            }
        }
    }
    private val internetBroadcast = InternetConnectionBroadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)

        initView()
        initAdapter()
        setView()
        setupRecipeFilter()
        backBtnToolbar()
        showRecipeDetail()

    }

    override fun onStart() {
        super.onStart()
        var intentFilter2 = IntentFilter(ConstantAction.ADD_SAVED_RECIPE_ACTION)
        var intentFilter3 = IntentFilter(ConstantAction.ADD_CMT_RECIPE_ACTION)

        registerReceiver(myBroadcastReceiverProfile, intentFilter2)
        registerReceiver(myBroadcastReceiverProfile, intentFilter3)

        internetBroadcast.registerInternetConnBroadcast(this@ShowProfileActivity)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Hủy đăng ký listener
        unregisterReceiver(myBroadcastReceiverProfile)

        internetBroadcast.unregisterInternetConnBroadcast(this@ShowProfileActivity)
    }
    private fun initView()
    {
        toolbarProfile=findViewById(R.id.toolbarProfile)
        avatarProfile=findViewById(R.id.avatarProfile)
        name=findViewById(R.id.name)
        bio=findViewById(R.id.bio)
        filterRecipe=findViewById(R.id.search)
        recyclerView=findViewById(R.id.recyclerView)
        recipeRenderArray= hashMapOf()
    }
    private fun backBtnToolbar()
    {
        toolbarProfile.setNavigationOnClickListener {
            finish()
        }
    }
    private fun setupRecipeFilter()
    {
        filterRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle submit event if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filterList= hashMapOf<FoodRecipe,User>()
                    for (food in recipeRenderArray) {
                        val recipe=food.key
                        if (recipe.recipeName.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)))
                        {
                            filterList[recipe] = food.value
                        }
                    }
                    adapter.filterList(filterList)
                }
                return true
            }
        })
    }

    private fun setView()
    {
        profile = intent.getParcelableExtra<User>("profile")!!

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
        recipeRenderArray.clear()
        for (foodRecipe in DBManagement.foodRecipeList) {
            if(recipe.contains(foodRecipe.id)) {
                recipeRenderArray[foodRecipe] = profile
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