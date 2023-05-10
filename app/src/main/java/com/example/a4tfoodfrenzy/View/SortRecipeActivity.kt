package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.ExpandRecyclerViewAdapter
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R

class SortRecipeActivity : AppCompatActivity() {
    private val selectedCategoryId = arrayListOf<Long>()
    private val selectedDietId = arrayListOf<Long>()

    private var filteredFoodList = arrayListOf<FoodRecipe>()

    companion object {
        var selectedNormalID: Long = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_recipe)

        val sortRecyclerView = findViewById<RecyclerView>(R.id.sortRecipeRecyclerView)
        val applySortButton = findViewById<TextView>(R.id.applySortTextView)
        val normalNameTypeList = arrayListOf(
            "Số Likes",
            "Lượng calories",
            "Số lượng bình luận",
            "Thời gian nấu",
            "Ngày đăng"
        )

        val searchedList = intent?.extras?.get("SearchedList") as (HashMap<FoodRecipe, User>)

        // back button
        findViewById<ImageView>(R.id.toolbarBackButton).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val normalImageList = arrayListOf(
            R.drawable.thumb_up_icon,
            R.drawable.fire_calory_icon,
            R.drawable.comment_sort_icon,
            R.drawable.cook_time_icon,
            R.drawable.calendar_date_sort_icon
        )

        val selectedNormalImageList = arrayListOf(
            R.drawable.selected_norm_0,
            R.drawable.selected_norm_1,
            R.drawable.selected_norm_2,
            R.drawable.selected_norm_3,
            R.drawable.selected_norm_4,
        )

        val categoryImageList = arrayListOf(
            R.drawable.norm_category_0,
            R.drawable.norm_category_1,
            R.drawable.norm_category_2,
            R.drawable.norm_category_3,
            R.drawable.norm_category_4,
            R.drawable.norm_category_5
        )

        val selectedCategoryImageList = arrayListOf(
            R.drawable.selected_category_0,
            R.drawable.selected_category_1,
            R.drawable.selected_category_2,
            R.drawable.selected_category_3,
            R.drawable.selected_category_4,
            R.drawable.selected_category_5
        )

        val dietImageList = arrayListOf(
            R.drawable.no_gluten_icon,
            R.drawable.pure_veg_icon,
            R.drawable.no_sugar_icon,
            R.drawable.no_alcohol_icon,
            R.drawable.no_meat_icon,
            R.drawable.veg_icon,
        )

        val selectedDietImageList = arrayListOf(
            R.drawable.selected_diet_0,
            R.drawable.selected_diet_1,
            R.drawable.selected_diet_2,
            R.drawable.selected_diet_3,
            R.drawable.selected_diet_4,
            R.drawable.selected_diet_5
        )

        val normType = arrayListOf<SortType>()
        val categoryType = arrayListOf<SortType>()
        val dietType = arrayListOf<SortType>()

        for (i in 0 until normalNameTypeList.size)
            normType.add(
                SortType(
                    normalNameTypeList[i],
                    0,
                    normalImageList[i],
                    selectedNormalImageList[i]
                )
            )
        for ((i, category) in DBManagement.recipeCateList.withIndex())
            categoryType.add(
                SortType(
                    category.recipeCateName,
                    category.id,
                    categoryImageList[i],
                    selectedCategoryImageList[i]
                )
            )
        for ((i, diet) in DBManagement.recipeDietList.withIndex())
            dietType.add(
                SortType(
                    diet.dietName,
                    diet.id,
                    dietImageList[i],
                    selectedDietImageList[i]
                )
            )

        val normalSortList = SortList(normType, 0, "Lọc")
        val categorySortList = SortList(categoryType, 1, "Loại")
        val dietSortList = SortList(dietType, 2, "Chế độ ăn")

        val sortLists = arrayListOf<SortList>()

        // show dropdown recycler ui
        sortLists.add(normalSortList)
        sortLists.add(categorySortList)
        sortLists.add(dietSortList)

        val adapter = ExpandRecyclerViewAdapter(sortLists, selectedCategoryId, selectedDietId, this)
        sortRecyclerView.adapter = adapter
        sortRecyclerView.layoutManager = LinearLayoutManager(this)

        applySortButton.setOnClickListener {
            val dietFoodIdList =
                getFoodIdListByDiet() // contain id of food satisfy all category type conditions
            val categoryFoodIdList =
                getFoodIdListByCategory() // contain id of food satisfy all diet type conditions
            var filteredIdList =
                arrayListOf<Long>() // contain all filtered ID of food satisfy all filter conditions of all filter / sort type

//            // not selected any type of diet, category
//            if(dietFoodIdList == null && categoryFoodIdList == null)
//                return@setOnClickListener

            if (dietFoodIdList != null && categoryFoodIdList == null)
                filteredIdList = dietFoodIdList
            else if (dietFoodIdList == null && categoryFoodIdList != null)
                filteredIdList = categoryFoodIdList
            else if (dietFoodIdList != null && categoryFoodIdList != null)
                filteredIdList =
                    dietFoodIdList.filter { id -> categoryFoodIdList.contains(id) } as ArrayList<Long>

            // filter with filteredIdList to get FoodRecipe
            if (searchedList.isEmpty()) {
                if (dietFoodIdList == null && categoryFoodIdList == null)
                    filteredFoodList = DBManagement.foodRecipeList
                else
                    filteredFoodList =
                        DBManagement.foodRecipeList.filter { food -> filteredIdList.contains(food.id) } as ArrayList<FoodRecipe>
            } else {
                val tempFoodList = arrayListOf<FoodRecipe>()
                for (food in searchedList) {
                    tempFoodList.add(food.key)
                }

                if (dietFoodIdList == null && categoryFoodIdList == null)
                    filteredFoodList = tempFoodList
                else
                    filteredFoodList =
                        tempFoodList.filter { food -> filteredIdList.contains(food.id) } as ArrayList<FoodRecipe>
            }

            handleNormalSort()

            val applySortIntent = Intent(this, AfterSearchActivity::class.java)

            applySortIntent.putExtra("filterdFoodRecipe", filteredFoodList)
            setResult(1111, applySortIntent)

            finish()
        }

        // change normal sort type
        adapter.onNormalSortTypeClick = { normalTypeID ->
            selectedNormalID = normalTypeID
        }
    }

    private fun getFoodIdListByDiet(): ArrayList<Long>? {
        if (selectedDietId.size == 0)
            return null

        var resList = DBManagement.recipeDietList[selectedDietId[0].toInt()].foodRecipes

        for (i in 1 until selectedDietId.size) {
            resList =
                DBManagement.recipeDietList[selectedDietId[i].toInt()].foodRecipes.filter { id ->
                    resList.contains(id)
                } as ArrayList<Long>
        }

        return resList
    }

    private fun getFoodIdListByCategory(): ArrayList<Long>? {
        if (selectedCategoryId.size == 0)
            return null

        var resList = DBManagement.recipeCateList[selectedCategoryId[0].toInt()].foodRecipes

        for (i in 1 until selectedCategoryId.size) {
            resList =
                DBManagement.recipeCateList[selectedCategoryId[i].toInt()].foodRecipes.filter { id ->
                    resList.contains(id)
                } as ArrayList<Long>
        }

        return resList
    }

    private fun handleNormalSort() {
        if (selectedNormalID == (0).toLong() && filteredFoodList.isNotEmpty()) { // sort by like
            filteredFoodList = filteredFoodList.sortedByDescending { it.numOfLikes }.toList() as java.util.ArrayList<FoodRecipe>
        }
        else if(selectedNormalID == (1).toLong() && filteredFoodList.isNotEmpty()){ // sort by calory
            filteredFoodList = filteredFoodList.sortedByDescending { food -> food.recipeIngres.sumByDouble { it.ingreCalo!! } }.toList() as java.util.ArrayList<FoodRecipe>
        }
        else if(selectedNormalID == (2).toLong() && filteredFoodList.isNotEmpty()){ // sort by comment
            filteredFoodList = filteredFoodList.sortedByDescending { it.recipeCmts.size }.toList() as java.util.ArrayList<FoodRecipe>
        }
        else if(selectedNormalID == (3).toLong() && filteredFoodList.isNotEmpty()){ // sort by cook time
            filteredFoodList = filteredFoodList.sortedByDescending {it.cookTime }.toList() as java.util.ArrayList<FoodRecipe>
        }
        else if(selectedNormalID == (4).toLong() && filteredFoodList.isNotEmpty()){ // sort by date
            filteredFoodList = filteredFoodList.sortedByDescending {it.date }.toList() as java.util.ArrayList<FoodRecipe>
        }
    }
}