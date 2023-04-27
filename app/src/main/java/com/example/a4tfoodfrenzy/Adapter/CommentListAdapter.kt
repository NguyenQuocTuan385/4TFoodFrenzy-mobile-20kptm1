package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat

class CommentListAdapter(private var context:Context,
    private var recipeCommentUserList: HashMap<RecipeComment, User>, private var recipeCommentFoodList: HashMap<RecipeComment, FoodRecipe>,
    private var isCmtListUserView: Boolean,
    private var isCmtListAdminView: Boolean
) : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    companion object {
        private const val CMT_LIST_USER_VIEW = 1
        private const val CMT_LIST_ADMIN_VIEW = 2
    }
    val storageRef = FirebaseStorage.getInstance()

    var onItemClick: ((RecipeComment, User, FoodRecipe, Int) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val avatarIV = listItemView.findViewById<ImageView>(R.id.avatarIV)
        val nameTV = listItemView.findViewById<TextView>(R.id.nameTV)
        val foodTV = listItemView.findViewById<TextView>(R.id.foodTV)
        val foodIV = listItemView.findViewById<ImageView>(R.id.foodIV)
        val cmtDescripTV = listItemView.findViewById<TextView>(R.id.cmtDescripTV)
        val timeTV = listItemView.findViewById<TextView>(R.id.timeTV)
        init {
            listItemView.setOnClickListener {
                recipeCommentUserList.get(recipeCommentUserList.keys.elementAt(adapterPosition))
                    ?.let { it1 ->
                        recipeCommentFoodList.get(recipeCommentUserList.keys.elementAt(adapterPosition))
                            ?.let { it2 ->
                                onItemClick?.invoke(
                                    recipeCommentUserList.keys.elementAt(adapterPosition),
                                    it1,
                                    it2,
                                    adapterPosition
                                )
                            }
                    }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (isCmtListUserView) {
            return CMT_LIST_USER_VIEW
        }
        else {
            return CMT_LIST_ADMIN_VIEW
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == CMT_LIST_USER_VIEW) {
            R.layout.comment
        } else{
            R.layout.comment_management_item
        }

        val commentView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(commentView)
    }

    override fun getItemCount(): Int {
        return recipeCommentUserList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val cmtRender: RecipeComment = recipeCommentUserList.keys.elementAt(position)
        // Set item views based on your views and data model
        val nameTV = holder.nameTV
        nameTV.text = recipeCommentUserList[cmtRender]?.fullname
        val foodTV = holder.foodTV
        foodTV.text = if (recipeCommentFoodList[cmtRender]!!.recipeName!!.length > 10) {
            recipeCommentFoodList[cmtRender]!!.recipeName!!.substring(0, 10) + "..."
        } else {
            recipeCommentFoodList[cmtRender]!!.recipeName!!
        }
        val cmtDescripTV = holder.cmtDescripTV
        cmtDescripTV.text = cmtRender.description
        val timeTV = holder.timeTV
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        timeTV.text = formatter.format(cmtRender.date)

        val avatarIV = holder.avatarIV
        val imageRef = recipeCommentUserList[cmtRender]?.avatar?.let { storageRef.getReference(it) }
        if (imageRef != null) {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri)
                    .into(avatarIV)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }
        val foodIV = holder.foodIV
        if (cmtRender.image == null) {
            foodIV.visibility = View.GONE
        }
        else {
            foodIV.visibility = View.VISIBLE
            val imageRef = cmtRender.image?.let { storageRef.getReference(it) }
            if (imageRef != null) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(context)
                        .load(uri)
                        .into(foodIV)
                }.addOnFailureListener { exception ->
                    // Xử lý lỗi
                }
            }
        }
    }
}



