package com.example.a4tfoodfrenzy.Adapter.RecipeCateAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class RecipeCateListAdapter(private var context: Context,
                            private var recipeRenderArray: ArrayList<RecipeCategory>,
                            private var isCateRecipeHomeView: Boolean, private var isCateRecipeSearchView: Boolean
) : RecyclerView.Adapter<RecipeCateListAdapter.ViewHolder>() {
    val storageRef = FirebaseStorage.getInstance()
    companion object {
        private const val CATE_RECIPE_HOME_VIEW = 1
        private const val CATE_RECIPE_SEARCH_VIEW = 2
    }

    var onItemClick: ((RecipeCategory, Int) -> Unit)? = null
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
        val recipeCate: RecipeCategory = recipeRenderArray.get(position)
        // Set item views based on your views and data model
        val titleRecipeTV = holder.titleRecipeTV
        titleRecipeTV.text = recipeCate.recipeCateName
        val recipeImg = holder.recipeIV
        var imageUserRef:StorageReference
        if (isCateRecipeHomeView) {
            imageUserRef = recipeCate.recipeCateImgHome.let { storageRef.getReference(it) }
        } else {
            imageUserRef = recipeCate.recipeCateImgSearch.let { storageRef.getReference(it) }
        }
        if (imageUserRef != null) {
            imageUserRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(recipeImg)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }
    }
}
