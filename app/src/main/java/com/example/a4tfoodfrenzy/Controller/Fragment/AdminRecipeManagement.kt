package com.example.a4tfoodfrenzy.Controller.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.RecipeManagementAdapter
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.takusemba.multisnaprecyclerview.MultiSnapHelper
import com.takusemba.multisnaprecyclerview.SnapGravity


class AdminRecipeManagement : Fragment() {
    private lateinit var recipeList: ArrayList<FoodRecipe>
    private var adapter: RecipeManagementAdapter? = null
    private lateinit var recipeManagementRecyclerView: RecyclerView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_recipe_management, container, false)

        // layout view
        recipeManagementRecyclerView = view.findViewById(R.id.adminRecipeManagementRecyclerView)
        val optionAdapter: ImageView = view.findViewById(R.id.recipeManagementFilterImageView)
        val filterOptionTV: TextView = view.findViewById(R.id.recipeManagementFilterTextView)
//        val loadMoreButton: Button = view.findViewById(R.id.loadMoreRecipeButton)
        val searchEditText: EditText = view.findViewById(R.id.adminSearchRecipeEditText)

        recipeList = DBManagement.foodRecipeList.sortedByDescending { food -> food.date }
            .toList() as ArrayList<FoodRecipe>

        val optionList = arrayListOf("Mới nhất", "Nhiều lượt thích nhất")

        // assign recipe recycler view adapter
        adapter = RecipeManagementAdapter(requireContext(), recipeList)
        recipeManagementRecyclerView.adapter = adapter

        // remove item change animation
        recipeManagementRecyclerView.itemAnimator = null

        // assign snaphelper for carousel
        val snapHelper = MultiSnapHelper(SnapGravity.START, 4, 100.0F)
        snapHelper.attachToRecyclerView(recipeManagementRecyclerView)

        recipeManagementRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        // assign recipe filter pop-up
        optionAdapter.setOnClickListener {
            val popUp = PopupMenu(requireContext(), optionAdapter)

            // add option string to pop-up
            for (option in optionList)
                popUp.menu.add(option)

            popUp.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Mới nhất" -> {
                        // assign sort title
                        filterOptionTV.text = item.title

                        // sort the original list by date
                        val sortedTempList = recipeList.sortedByDescending { food -> food.date }

                        // clear and add sorted list to the original
                        recipeList.clear()
                        recipeList.addAll(sortedTempList)

                        // notify the adapter
                        adapter!!.notifyItemRangeChanged(0, recipeList.size)

                        true
                    }
                    "Nhiều lượt thích nhất" -> {
                        filterOptionTV.text = item.title

                        // sort the original list by num of likes
                        val sortedTempList =
                            recipeList.sortedByDescending { food -> food.numOfLikes }

                        // clear and add sorted list to the original
                        recipeList.clear()
                        recipeList.addAll(sortedTempList)

                        // notify the adapter
                        adapter!!.notifyItemRangeChanged(0, recipeList.size)

                        true
                    }
                    else -> false
                }
            }

            popUp.show()
        }

        // search feature
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchWord = searchEditText.text.toString()

                val searchedList =
                    DBManagement.foodRecipeList
                        .filter { food -> food.recipeName.contains(searchWord) }
                        .sortedByDescending { food -> food.date }

                if (searchedList.isNotEmpty()) {
                    recipeList.clear()
                    recipeList.addAll(searchedList)

                    filterOptionTV.text = "Mới nhất"
                }
            }
            true
        }

        // delete recipe feature
        adapter!!.onDeleteRecipeClick = { recipeFromRV ->
            val helperFunctionDB = HelperFunctionDB(requireContext())
            val recipeID = recipeFromRV.id

            helperFunctionDB.showWarningAlert(
                "Xóa món ăn",
                "Bạn có chắc là sẽ xóa món ăn này?"
            ) { isConfirm ->
                if (isConfirm) {
                    // remove recipe in my food of the author
                    db.collection("users")
                        .whereArrayContains("myFoodRecipes", recipeID)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.documents.isNotEmpty()) {
                                db.collection("users")
                                    .document(document.documents[0].id)
                                    .update(
                                        mapOf(
                                            "myFoodRecipes" to FieldValue.arrayRemove(recipeID)
                                        )
                                    )
                            }
                        }

                    // remove recipe from saved list of all the users that had saved the recipe
                    db.collection("users")
                        .whereArrayContains("foodRecipeSaved", recipeID)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("users")
                                    .document(document.id)
                                    .update(
                                        mapOf(
                                            "foodRecipeSaved" to FieldValue.arrayRemove(recipeID)
                                        )
                                    )
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }

                    // get comment list of the chosen recipe
                    val recipeCmts = recipeFromRV.recipeCmts

                    // remove comment from comment list of all user that had
                    // commented on this recipe
                    for (recipeCmt in recipeCmts) {
                        db.collection("users")
                            .whereArrayContains("recipeCmts", recipeCmt)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    db.collection("users").document(document.id).update(
                                        mapOf(
                                            "recipeCmts" to FieldValue.arrayRemove(recipeCmt)
                                        )
                                    )
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                            }
                    }

                    // remove comment from RecipeCmts table
                    for(recipeCmt in recipeCmts){
                        db.collection("RecipeCmts")
                            .whereEqualTo("id", recipeCmt)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    db.collection("RecipeCmts").document(document.id).delete()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                            }
                    }

                    // xóa trong danh sách foodRecipes của bảng RecipeCates
                    db.collection("RecipeCates")
                        .whereArrayContains("foodRecipes", recipeID)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("RecipeCates").document(document.id).update(mapOf(
                                    "foodRecipes" to FieldValue.arrayRemove(recipeID)
                                ))
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }

                    // xóa trong danh sách foodRecipes của bảng RecipeDiets
                    db.collection("RecipeDiets")
                        .whereArrayContains("foodRecipes", recipeID)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("RecipeDiets").document(document.id).update(mapOf(
                                    "foodRecipes" to FieldValue.arrayRemove(recipeID)
                                ))
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }

                    // xóa Món ăn
                    db.collection("RecipeFoods")
                        .whereEqualTo("id", recipeID)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                db.collection("RecipeFoods").document(document.id).delete()
                            }

                            for((i, recipe) in recipeList.withIndex()){
                                if(recipeID == recipe.id){
                                    recipeList.removeAt(i)
                                    adapter!!.notifyItemRemoved(i)
                                    break
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                        }
                }
            }
        }

        return view
    }
}