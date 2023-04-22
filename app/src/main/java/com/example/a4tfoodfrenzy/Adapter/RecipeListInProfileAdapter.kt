package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.AddRecipeActivity1
import com.example.a4tfoodfrenzy.View.AddStepActivity
import com.example.a4tfoodfrenzy.View.ShowRecipeDetailsActivity
import com.google.firebase.firestore.FirebaseFirestore


class RecipeListInProfileAdapter(private var context: Context,
    private var recipeRenderArray: ArrayList<FoodRecipe>,
    private var isRecipeSavedView: Boolean,
    private var isRecipeCreatedView: Boolean,
) : RecyclerView.Adapter<RecipeListInProfileAdapter.ViewHolder>() {
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
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == RECIPE_SAVED_VIEW) {
            R.layout.saved_item
        } else {
            R.layout.created_item
        }
        val recipeProfileView = inflater.inflate(layoutResult, parent, false)
        val option_saved_recipe = recipeProfileView.findViewById<ImageView>(R.id.option_saved_recipe)
        val option_created_recipe = recipeProfileView.findViewById<ImageView>(R.id.option_created_recipe)

        val list_option = listOf<String>("Xóa", "Xem chi tiết")
        val list_option_created = listOf<String>("Xóa", "Cập nhật", "Chia sẻ")

        if (viewType == RECIPE_SAVED_VIEW) {
            option_saved_recipe.setOnClickListener {
                val popupMenu = PopupMenu(context, option_saved_recipe)
                for (i in list_option.indices) {
                    popupMenu.menu.add(Menu.NONE, i, i, list_option[i])
                }
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        0 -> {
//                            recipeRenderArray.removeAt()
                        }
                        1 -> {
                            // chuyển đến trang xem chi tiết
                            val intent = Intent(context, ShowRecipeDetailsActivity::class.java)
//                            intent.putExtra("recipeID", recipeRenderArray.get(adapterPosition).recipeID)
                            context.startActivity(intent)
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
        else {
            option_created_recipe.setOnClickListener {
                val popupMenu = PopupMenu(context, option_created_recipe)
                for (i in list_option_created.indices) {
                    popupMenu.menu.add(Menu.NONE, i, i, list_option_created[i])
                }
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        0 -> {
//                            recipeRenderArray.removeAt()
                        }
                        1 -> {
                            // chuyển đến trang cập nhật
                            val intent = Intent(context, AddRecipeActivity1::class.java)
//                            intent.putExtra("recipeID", recipeRenderArray.get(adapterPosition).recipeID)
                            context.startActivity(intent)
                        }
                        2 -> {
                            // chia sẻ
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }

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
        val resources = context.getResources()
        val resourceId = resources.getIdentifier(recipeRender.recipeMainImage, "drawable", context.packageName)
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        recipeIMG.setImageBitmap(bitmap)
        val recipeName = holder.recipeName
        recipeName.text = recipeRender.recipeName
        val numOfLike = holder.numOfLike
        numOfLike.text = recipeRender.numOfLikes.toString()
        val authorName = holder.authorName
        authorName.text = recipeRender.authorName
        val authorAvatarIMG = holder.authorAvatarIMG
        recipeRender.authorAvatar?.let { authorAvatarIMG.setImageResource(it) }
        val uploadDate = holder.uploadDate
        uploadDate.text = recipeRender.date.toString()
    }
}