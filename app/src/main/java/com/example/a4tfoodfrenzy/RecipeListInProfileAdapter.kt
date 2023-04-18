package com.example.a4tfoodfrenzy

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.FoodRecipe


class RecipeListInProfileAdapter1(
    private var recipeRenderArray: ArrayList<FoodRecipe>,
    private var isRecipeSavedView: Boolean,
    private var isRecipeCreatedView: Boolean,
) : RecyclerView.Adapter<RecipeListInProfileAdapter1.ViewHolder>() {
    companion object {
        private const val RECIPE_SAVED_VIEW = 1
        private const val RECIPE_CREATED_VIEW = 2
    }

    var onItemClick: ((FoodRecipe, Int) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val recipeName : TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val recipeIMG = listItemView.findViewById<ImageView>(R.id.recipeManagementImageView)
        val numOfLike : TextView = listItemView.findViewById(R.id.numOfLikesTextView)
        val authorName : TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG : ImageView = listItemView.findViewById(R.id.avatarImageView)
        val uploadDate : TextView = listItemView.findViewById(R.id.uploadDateTextView)

        init {
            listItemView.setOnClickListener {
                onItemClick?.invoke(
                    recipeRenderArray.get(adapterPosition), adapterPosition
                )
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (isRecipeSavedView) {
            return RECIPE_SAVED_VIEW
        } else if ( isRecipeCreatedView) {
            return RECIPE_CREATED_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListInProfileAdapter1.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == RECIPE_SAVED_VIEW) {
            R.layout.saved_item
        } else {
            R.layout.created_item
        }
        val recipeProfileView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(recipeProfileView)
    }

    override fun getItemCount(): Int {
        return recipeRenderArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val recipeRender: FoodRecipe = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val recipeIMG = holder.recipeIMG
        recipeRender.recipeMainImage?.let { recipeIMG.setImageResource(it) }
        val recipeName = holder.recipeName
        recipeName.text = recipeRender.recipeName
        val numOfLike = holder.numOfLike
        numOfLike.text = recipeRender.numOfLikes.toString()
        val authorName = holder.authorName
        authorName.text = recipeRender.authorName
        val authorAvatarIMG = holder.authorAvatarIMG
        recipeRender.authorAvatar?.let { authorAvatarIMG.setImageResource(it) }
        val uploadDate = holder.uploadDate
        uploadDate.text = recipeRender.uploadDate.toString()
    }
}