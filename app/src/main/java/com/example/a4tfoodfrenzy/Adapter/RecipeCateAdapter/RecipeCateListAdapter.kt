package com.example.a4tfoodfrenzy.Adapter.RecipeCateAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeCategorySuggest
import com.example.a4tfoodfrenzy.R

class RecipeCateListAdapter(
    private var recipeRenderArray: ArrayList<RecipeCategorySuggest>,
    private var isCateRecipeHomeView: Boolean, private var isCateRecipeSearchView: Boolean
) : RecyclerView.Adapter<RecipeCateListAdapter.ViewHolder>() {
    companion object {
        private const val CATE_RECIPE_HOME_VIEW = 1
        private const val CATE_RECIPE_SEARCH_VIEW = 2
    }

    var onItemClick: ((RecipeCategorySuggest, Int) -> Unit)? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val titleRecipeTV = listItemView.findViewById<TextView>(R.id.titleRecipe)
        val recipeIV = listItemView.findViewById<ImageView>(R.id.recipeIV)

        init {
            listItemView.setOnClickListener {
                onItemClick?.invoke(
                    recipeRenderArray.get(adapterPosition), adapterPosition
                )
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (isCateRecipeHomeView) {
            return CATE_RECIPE_HOME_VIEW
        } else {
            return CATE_RECIPE_SEARCH_VIEW
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == CATE_RECIPE_SEARCH_VIEW) {
            R.layout.search_recipe_view
        } else {
            R.layout.homepage_type_recipe_item
        }

        val studentView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(studentView)
    }

    override fun getItemCount(): Int {
        return recipeRenderArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val recipeCate: RecipeCategorySuggest = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val titleRecipeTV = holder.titleRecipeTV
        titleRecipeTV.text = recipeCate.recipeCateTitle
        val recipeImg = holder.recipeIV
        recipeCate.recipeCateImg?.let { recipeImg.setImageResource(it) }
    }
}
