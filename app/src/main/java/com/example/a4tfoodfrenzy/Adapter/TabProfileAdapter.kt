package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeListInProfileAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TabProfileAdapter(private var context: Context,fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }
}


class TabFoodRecipeSaved(private var context: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.saved_recipe_in_profile, container, false)
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.gridView1)

        val monAn = generateRecipeSaved()
        val adapter = context?.let { RecipeListInProfileAdapter(it,monAn, true, false) }
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView1!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recyclerView1!!.adapter = adapter
        recyclerView1!!.layoutManager = GridLayoutManager(inflater.context, 2)
        recyclerView1.adapter = adapter

        val autoComplete_search_Recipe = view.findViewById<AutoCompleteTextView>(R.id.searchRecipe)
       autoComplete_search_Recipe.setAdapter(ArrayAdapter(inflater.context, android.R.layout.simple_list_item_1, monAn.keys.toList()))
        autoComplete_search_Recipe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                recyclerView1!!.adapter = adapter

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == "") {
                    recyclerView1!!.adapter = adapter
                } else {
                    val result = HashMap<FoodRecipe, User>()
                    monAn.forEach {
                        if(it.key.recipeName.toLowerCase().contains(s.toString().toLowerCase())) {
                            result[it.key] = it.value
                        }
                    }
                    val adapter = context?.let { RecipeListInProfileAdapter(it,result, true, false) }
                    recyclerView1!!.adapter = adapter
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        return view
    }

    private fun generateRecipeSaved(): HashMap<FoodRecipe, User> {
        var HashMap = HashMap<FoodRecipe, User>()
        var result = ArrayList<FoodRecipe>()

        val recipeSaved = DBManagement.user_current?.foodRecipeSaved

        DBManagement.foodRecipeList.forEach {
            if (recipeSaved?.contains(it.id) == true) {
                result.add(it)
                DBManagement.userList.forEach { user ->
                    if (user.myFoodRecipes?.contains(it.id) == true) {
                        HashMap[it] = user
                    }
                }
            }
        }

        return HashMap
    }
}

class TabMyFoodRecipe(private var context: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_recipe_in_profile, container, false)
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.created_recipe_RV)
        val monAn = generateRecipeSaved()

        val adapter = context?.let { RecipeListInProfileAdapter(it,monAn, false, true) }
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView1!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recyclerView1!!.adapter = adapter
        recyclerView1!!.layoutManager = GridLayoutManager(inflater.context, 2)
        recyclerView1.adapter = adapter

        val autoComplete_search_Recipe = view.findViewById<AutoCompleteTextView>(R.id.searchRecipe)
        autoComplete_search_Recipe.setAdapter(ArrayAdapter(inflater.context, android.R.layout.simple_list_item_1, monAn.keys.toList()))
        autoComplete_search_Recipe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                recyclerView1!!.adapter = adapter

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == "") {
                    recyclerView1!!.adapter = adapter
                } else {
                    val result = HashMap<FoodRecipe, User>()
                    monAn.forEach {
                        if(it.key.recipeName.toLowerCase().contains(s.toString().toLowerCase())) {
                            result[it.key] = it.value
                        }
                    }
                    val adapter = context?.let { RecipeListInProfileAdapter(it,result, false, true) }
                    recyclerView1!!.adapter = adapter
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        return view
    }

    private fun generateRecipeSaved(): HashMap<FoodRecipe, User> {
        var HashMap = HashMap<FoodRecipe, User>()
        val recipeCreated = DBManagement.user_current?.myFoodRecipes

        DBManagement.foodRecipeList.forEach {
           if(recipeCreated?.contains(it.id) == true) {
               HashMap[it] = DBManagement.user_current!!
           }
        }

        return HashMap
    }
}
