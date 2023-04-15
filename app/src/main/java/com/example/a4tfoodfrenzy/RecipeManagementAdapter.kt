package com.example.a4tfoodfrenzy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.FoodRecipe
import de.hdodenhof.circleimageview.CircleImageView
import java.util.zip.Inflater

class RecipeManagementAdapter(private var recipeList : ArrayList<FoodRecipe>) : RecyclerView.Adapter<RecipeManagementAdapter.ViewHolder>(){
    inner class ViewHolder(listItemView : View) : RecyclerView.ViewHolder(listItemView){
        val recipeIMG : ImageView = listItemView.findViewById(R.id.recipeManagementImageView)
        val recipeName : TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val numOfLike : TextView = listItemView.findViewById(R.id.numOfLikesTextView)
        val authorName : TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG : CircleImageView = listItemView.findViewById(R.id.avatarImageView)
        val uploadDate : TextView = listItemView.findViewById(R.id.uploadDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeManagementAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.food_recipe_management_item, parent, false))
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        val uploadDateString = "${currentRecipe.getUploadDate().date}/${currentRecipe.getUploadDate().month}/${currentRecipe.getUploadDate().year}"

        holder.recipeIMG.setImageResource(currentRecipe.getRecipeIMG())
        holder.recipeName.text = currentRecipe.getRecipeName()
        holder.numOfLike.text = currentRecipe.getLikes().toString()
        holder.authorAvatarIMG.setImageResource(currentRecipe.getAuthorAvatar())
        holder.authorName.text = currentRecipe.getAuthorName()
        holder.uploadDate.text = uploadDateString
    }
}