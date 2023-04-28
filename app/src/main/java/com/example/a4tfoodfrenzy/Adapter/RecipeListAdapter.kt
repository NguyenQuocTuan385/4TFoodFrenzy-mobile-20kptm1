package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage
import java.util.TreeMap

class RecipeListAdapter(private var context:Context,
    private var recipeRenderArray: LinkedHashMap<FoodRecipe, User>) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    val storageRef = FirebaseStorage.getInstance()
    var onItemClick: ((FoodRecipe, User, Int) -> Unit)? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val recipeName : TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val recipeIMG = listItemView.findViewById<ImageView>(R.id.recipeManagementImageView)
        val authorName : TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG : ImageView = listItemView.findViewById(R.id.avatarImageView)
        val option_saved_recipe :ImageView = listItemView.findViewById(R.id.option_saved_recipe)
        init {
            listItemView.setOnClickListener {
                recipeRenderArray.get(recipeRenderArray.keys.elementAt(adapterPosition))?.let { it1 ->
                    onItemClick?.invoke(
                        recipeRenderArray.keys.elementAt(adapterPosition), it1, adapterPosition
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = R.layout.saved_item

        val recipeView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(recipeView)
    }

    override fun getItemCount(): Int {
        return recipeRenderArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val foodRecipe: FoodRecipe = recipeRenderArray.keys.elementAt(position)
        // Set item views based on your views and data model
        val titleRecipeTV = holder.recipeName
        titleRecipeTV.text = foodRecipe.recipeName
        val authorName = holder.authorName
        authorName.text = recipeRenderArray[foodRecipe]?.fullname
        val option_saved_recipe = holder.option_saved_recipe
        option_saved_recipe.visibility = View.GONE
        val authorAvatar = holder.authorAvatarIMG
        val imageUserRef = recipeRenderArray[foodRecipe]?.avatar?.let { storageRef.getReference(it) }
        if (imageUserRef != null) {
            imageUserRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(authorAvatar)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }
        val recipeImg = holder.recipeIMG
        val imageFoodRef = foodRecipe.recipeMainImage?.let { storageRef.getReference(it) }
        if (imageFoodRef != null) {
            imageFoodRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(recipeImg)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }
    }
}
