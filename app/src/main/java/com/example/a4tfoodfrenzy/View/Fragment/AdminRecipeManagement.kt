package com.example.a4tfoodfrenzy.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.RecipeManagementAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.R


class AdminRecipeManagement : Fragment() {

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
        val recipeManagementRecyclerView: RecyclerView =
            view.findViewById(R.id.adminRecipeManagementRecyclerView)
        val optionAdapter: ImageView = view.findViewById(R.id.recipeManagementFilterImageView)
        val filterOptionTV: TextView = view.findViewById(R.id.recipeManagementFilterTextView)
        val loadMoreButton: Button = view.findViewById(R.id.loadMoreRecipeButton)

        val recipeList = DBManagement.foodRecipeList
        var adapter: RecipeManagementAdapter? = null
        val optionList = arrayListOf("Mới nhất", "Phổ biến", "Nhiều lượt thích nhất")

        // assign recipe recycler view adapter
        adapter = RecipeManagementAdapter(requireContext(), recipeList)
        recipeManagementRecyclerView.adapter = adapter
        recipeManagementRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // remove item change animation
        recipeManagementRecyclerView.itemAnimator = null

        // view more food
        loadMoreButton.setOnClickListener {
            adapter.start += 6
            adapter.end += 6

            if (adapter.end >= recipeList.size) {
                adapter.start = 0
                adapter.end = 5
            }

            adapter.notifyItemRangeChanged(0, adapter.end + 1)
        }

        // assign recipe filter pop-up
        optionAdapter.setOnClickListener {
            val popUp = PopupMenu(requireContext(), optionAdapter)

            // add option string to pop-up
            for (option in optionList)
                popUp.menu.add(option)

            popUp.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Mới nhất" -> {
                        filterOptionTV.text = item.title
                        true
                    }
                    "Phổ biến" -> {
                        filterOptionTV.text = item.title
                        true
                    }
                    "Nhiều lượt thích nhất" -> {
                        filterOptionTV.text = item.title
                        true
                    }
                    else -> false
                }
            }

            popUp.show()
        }

        return view
    }


}