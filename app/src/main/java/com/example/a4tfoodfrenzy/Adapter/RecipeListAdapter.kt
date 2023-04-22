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
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage

class RecipeListAdapter(private var context:Context,
    private var recipeRenderArray: ArrayList<FoodRecipe>) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    val storageRef = FirebaseStorage.getInstance()
    var onItemClick: ((FoodRecipe, Int) -> Unit)? = null
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = R.layout.list_recipe_item

        val recipeView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(recipeView)
    }

    override fun getItemCount(): Int {
        return recipeRenderArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val foodRecipe: FoodRecipe = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val titleRecipeTV = holder.titleRecipeTV
        titleRecipeTV.text = foodRecipe.recipeName
        val recipeImg = holder.recipeIV
        val imageRef = foodRecipe.recipeMainImage?.let { storageRef.getReference(it) }
        if (imageRef != null) {
            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                recipeImg.setImageBitmap(bitmap)
            }.addOnFailureListener { exception ->
                // Xử lý ngoại lệ nếu có lỗi xảy ra
            }
        }
    }
}
