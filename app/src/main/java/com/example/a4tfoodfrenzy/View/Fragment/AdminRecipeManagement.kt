package com.example.a4tfoodfrenzy.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.RecipeManagementAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R


class AdminRecipeManagement : Fragment() {
    private lateinit var recipeList : ArrayList<FoodRecipe>
    private var adapter: RecipeManagementAdapter? = null
    private lateinit var recipeManagementRecyclerView: RecyclerView

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
        val loadMoreButton: Button = view.findViewById(R.id.loadMoreRecipeButton)
        val searchEditText: EditText = view.findViewById(R.id.adminSearchRecipeEditText)

        recipeList = DBManagement.foodRecipeList.sortedByDescending { food -> food.date }.toList() as ArrayList<FoodRecipe>

        val optionList = arrayListOf("Mới nhất", "Phổ biến", "Nhiều lượt thích nhất")

        // assign recipe recycler view adapter
        adapter = RecipeManagementAdapter(requireContext(), recipeList)
        recipeManagementRecyclerView.adapter = adapter
        recipeManagementRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // remove item change animation
        recipeManagementRecyclerView.itemAnimator = null

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
                        val sortedTempList = recipeList.sortedByDescending { food -> food.date }.toList() as ArrayList<FoodRecipe>

                        // clear and add sorted list to the original
                        recipeList.clear()
                        recipeList.addAll(sortedTempList)

                        // notify the adapter
                        adapter!!.notifyItemRangeChanged(0, recipeList.size)

                        true
                    }
                    "Phổ biến" -> {
                        filterOptionTV.text = item.title
                        true
                    }
                    "Nhiều lượt thích nhất" -> {
                        filterOptionTV.text = item.title

                        // sort the original list by num of likes
                        val sortedTempList = recipeList.sortedByDescending { food -> food.numOfLikes }.toList() as ArrayList<FoodRecipe>

                        // clear and add sorted list to the original
                        recipeList.clear()
                        recipeList.addAll(sortedTempList)

                        // notify the adapter
                        adapter!!.notifyItemRangeChanged(0, recipeList.size)

//                        adapter = RecipeManagementAdapter(requireContext(), recipeList)
//                        recipeManagementRecyclerView.adapter = adapter

                        true
                    }
                    else -> false
                }
            }

            popUp.show()
        }

        // search feature
        searchEditText.setOnEditorActionListener{_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val searchWord = searchEditText.text.toString()

                // empty
                if(searchWord == ""){
                    true
                }

                val searchedList = DBManagement.foodRecipeList.filter { food -> food.recipeName.contains(searchWord) }

                if(searchedList.isNotEmpty()){
                    recipeList.clear()
                    recipeList.addAll(searchedList.toList() as ArrayList<FoodRecipe>)
                }

                adapter!!.notifyItemRangeChanged(0, recipeList.size)
            }
            true
        }

        // view more food
        loadMoreButton.setOnClickListener {
            adapter!!.start += 6
            adapter!!.end += 6

            if (adapter!!.end >= recipeList.size) {
                adapter!!.start = 0
                adapter!!.end = 5

                // notify the whole source list
                adapter!!.notifyItemRangeChanged(0, recipeList.size)
            }
            else
                adapter!!.notifyItemRangeChanged(0, adapter!!.end + 1)
        }
        return view
    }


}