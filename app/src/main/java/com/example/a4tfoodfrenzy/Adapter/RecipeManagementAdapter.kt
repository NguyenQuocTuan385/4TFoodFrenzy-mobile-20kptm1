package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView

class RecipeManagementAdapter(
    private var context: Context,
    private var recipeList: ArrayList<FoodRecipe>
) : RecyclerView.Adapter<RecipeManagementAdapter.ViewHolder>() {
    val storageRef = Firebase.storage
    var start = 0
    var end = 5

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val recipeIMG: ImageView = listItemView.findViewById(R.id.recipeManagementImageView)
        val recipeName: TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val numOfLike: TextView = listItemView.findViewById(R.id.numOfLikesTextView)
        val authorName: TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG: CircleImageView = listItemView.findViewById(R.id.avatarImageView)
        val uploadDate: TextView = listItemView.findViewById(R.id.uploadDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.food_recipe_management_item, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // not in pagination item range --> hide
        if (position < start || position > end) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
            return
        }

        // visible when in pagination range
        holder.itemView.visibility = View.VISIBLE
        holder.itemView.layoutParams =
            RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        val currentRecipe = recipeList[position]
        val uploadDateString =
            "${currentRecipe.date.date}/${currentRecipe.date.month}/${currentRecipe.date.year}"

        // assign main food image
        val foodImageRef = currentRecipe.recipeMainImage?.let { storageRef.getReference(it) }

        foodImageRef?.downloadUrl?.addOnSuccessListener { uri ->
            Glide.with(context)
                .load(uri)
                .into(holder.recipeIMG)
        }?.addOnFailureListener {}

        // find author
        for (user in DBManagement.userList) {
            if (user.myFoodRecipes.contains(currentRecipe.id)) {
                val authorAvtRef = user.avatar.let { storageRef.getReference(it) }

                // assign author avatar
                authorAvtRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(context)
                        .load(uri)
                        .into(holder.authorAvatarIMG)
                }.addOnFailureListener {}
                break
            }
        }

        holder.recipeName.text = currentRecipe.recipeName
        holder.numOfLike.text =
            if (currentRecipe.numOfLikes != null) currentRecipe.numOfLikes.toString() else "0"
        holder.authorName.text = currentRecipe.authorName
        holder.uploadDate.text = uploadDateString
    }
}