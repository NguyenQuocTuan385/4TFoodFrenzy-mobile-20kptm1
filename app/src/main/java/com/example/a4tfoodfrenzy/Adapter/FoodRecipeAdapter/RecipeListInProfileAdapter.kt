package com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage


class RecipeListInProfileAdapter(private var context: Context,
                                 private var recipeRenderArray: HashMap<FoodRecipe, User>,
                                 private var isRecipeSavedView: Boolean,
                                 private var isRecipeCreatedView: Boolean,
) : RecyclerView.Adapter<RecipeListInProfileAdapter.ViewHolder>() {
    companion object {
        private const val RECIPE_SAVED_VIEW = 1
        private const val RECIPE_CREATED_VIEW = 2
        private const val RECIPE_OTHER_VIEW = 3

    }

    var onButtonClick : ((View, FoodRecipe) -> Unit)? = null
    val storageRef = FirebaseStorage.getInstance()


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val recipeName : TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val recipeIMG = listItemView.findViewById<ImageView>(R.id.recipeManagementImageView)
        val numOfLike : TextView = listItemView.findViewById(R.id.numOfLikesTextView)
        val authorName : TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG : ImageView = listItemView.findViewById(R.id.avatarImageView)
        val uploadDate : TextView = listItemView.findViewById(R.id.uploadDateTextView)
        val isPublic : TextView = listItemView.findViewById(R.id.isPublicTextView)
        val optionItem : ImageView = listItemView.findViewById(R.id.option_saved_recipe)

        init {
            listItemView.setOnClickListener {
                onButtonClick?.invoke(
                    it,
                    recipeRenderArray.keys.toTypedArray()[adapterPosition]
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
        else
            return RECIPE_OTHER_VIEW
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = when(viewType){
            RECIPE_SAVED_VIEW -> R.layout.saved_item
            RECIPE_CREATED_VIEW -> R.layout.created_item
            else -> R.layout.recipe_profile
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
        val recipeRender = recipeRenderArray.keys.toTypedArray()[position]
        // Set item views based on your views and data model
        val recipeName = holder.recipeName
        recipeName.text = recipeRender.recipeName

        val recipeImg = holder.recipeIMG
        val imageRef = recipeRender.recipeMainImage?.let { storageRef.getReference(it) }
        if (imageRef != null) {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(recipeImg)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }

        val authorName = holder.authorName
        authorName.text = recipeRenderArray[recipeRender]?.fullname

        val authorAvatar = holder.authorAvatarIMG
        val imageRef2 = recipeRenderArray[recipeRender]?.avatar?.let { storageRef.getReference(it) }
        if (imageRef2 != null) {
            imageRef2.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(authorAvatar)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }

        val shareStatus = holder.isPublic
        shareStatus.text =  "Đã chia sẻ"
        if (recipeRender.isPublic && isRecipeCreatedView) {
            shareStatus.text = "Đã chia sẻ"

            shareStatus.setTextColor(Color.parseColor("#FFB200"))
        } else if(!recipeRender.isPublic && isRecipeCreatedView) {
            shareStatus.text = "Chưa chia sẻ"
            shareStatus.setTextColor(Color.parseColor("#000000"))
        } else {
            shareStatus.text = ""
        }

        val numOfLike = holder.numOfLike
        numOfLike.text = recipeRender.numOfLikes.toString()
        val uploadDate = holder.uploadDate
        uploadDate.text = recipeRender.date.toString()


        holder.optionItem.setOnClickListener{
            onButtonClick?.invoke(
                it,
                recipeRender
            )
        }


    }
    fun filterList(temp:HashMap<FoodRecipe,User>)
    {
        recipeRenderArray=temp
        notifyDataSetChanged()
    }
}

