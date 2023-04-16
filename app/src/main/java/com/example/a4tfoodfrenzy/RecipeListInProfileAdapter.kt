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
    fun setRecipeSavedView(isRecipeSavedView: Boolean) {
        this.isRecipeSavedView = isRecipeSavedView
        notifyDataSetChanged()
    }

    fun setTypeRecipeCreatedView(isRecipeCreatedView: Boolean) {
        this.isRecipeCreatedView = isRecipeCreatedView
        notifyDataSetChanged()
    }

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
        val studentView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(studentView)
    }

    override fun getItemCount(): Int {
        return recipeRenderArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val recipeRender: FoodRecipe = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val recipeIMG = holder.recipeIMG
        recipeRender.getRecipeIMG()?.let { recipeIMG.setImageResource(it) }
        val recipeName = holder.recipeName
        recipeName.text = recipeRender.getRecipeName()
        val numOfLike = holder.numOfLike
        numOfLike.text = recipeRender.getLikes().toString()
        val authorName = holder.authorName
        authorName.text = recipeRender.getAuthorName()
        val authorAvatarIMG = holder.authorAvatarIMG
        recipeRender.getAuthorAvatar()?.let { authorAvatarIMG.setImageResource(it) }
        val uploadDate = holder.uploadDate
        uploadDate.text = recipeRender.getUploadDate().toString()
    }
}


class GridSpacingItemDecorationInProfile1(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            left = spacing / 2
            right = spacing / 2
            top = spacing / 2
            bottom = spacing / 2
        }
    }
}