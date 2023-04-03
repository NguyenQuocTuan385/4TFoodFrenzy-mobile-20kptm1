package com.example.a4tfoodfrenzy

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeRender {
    var titleRecipe: String? = null
    var recipeImage: Int? = null
}

class RecipeListAdapter(
    private var recipeRenderArray: ArrayList<RecipeRender>, private var isRecipeHomeView: Boolean,
    private var isTypeRecipeHomeView: Boolean, private var isTypeRecipeSearchView: Boolean
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    companion object {
        private const val RECIPE_HOME_VIEW = 1
        private const val TYPE_RECIPE_HOME_VIEW = 2
        private const val TYPE_RECIPE_SEARCH_VIEW = 3
    }

    var onItemClick: ((RecipeRender, Int) -> Unit)? = null
    fun setRecipeHomeView(isRecipeHomeView: Boolean) {
        this.isRecipeHomeView = isRecipeHomeView
        notifyDataSetChanged()
    }

    fun setTypeRecipeHomeView(isTypeRecipeHomeView: Boolean) {
        this.isTypeRecipeHomeView = isTypeRecipeHomeView
        notifyDataSetChanged()
    }

    fun setTypeRecipeSearchView(isTypeRecipeSearchView: Boolean) {
        this.isTypeRecipeSearchView = isTypeRecipeSearchView
        notifyDataSetChanged()
    }
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
        if (isRecipeHomeView) {
            return RECIPE_HOME_VIEW
        } else if (isTypeRecipeHomeView) {
            return TYPE_RECIPE_HOME_VIEW
        }
        else {
            return TYPE_RECIPE_SEARCH_VIEW
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == TYPE_RECIPE_SEARCH_VIEW) {
            R.layout.search_recipe_view
        } else if (viewType == TYPE_RECIPE_HOME_VIEW) {
            R.layout.homepage_type_recipe_item
        }
        else {
            R.layout.list_recipe_item
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
        val recipeRender: RecipeRender = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val titleRecipeTV = holder.titleRecipeTV
        titleRecipeTV.text = recipeRender.titleRecipe
        val recipeImg = holder.recipeIV
        recipeRender.recipeImage?.let { recipeImg.setImageResource(it) }
    }
}

class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            left = spacing / 2
            right = spacing / 2
            top = spacing / 2
            bottom = spacing / 2
        }
    }
}