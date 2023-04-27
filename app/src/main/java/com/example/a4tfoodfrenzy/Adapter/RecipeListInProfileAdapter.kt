package com.example.a4tfoodfrenzy.Adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.AddRecipeActivity1
import com.example.a4tfoodfrenzy.View.AddStepActivity
import com.example.a4tfoodfrenzy.View.ShowRecipeDetailsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView


class RecipeListInProfileAdapter(private var context: Context,
                                 private var recipeRenderArray: HashMap<FoodRecipe, User>,
                                 private var isRecipeSavedView: Boolean,
                                 private var isRecipeCreatedView: Boolean,
) : RecyclerView.Adapter<RecipeListInProfileAdapter.ViewHolder>() {
    companion object {
        private const val RECIPE_SAVED_VIEW = 1
        private const val RECIPE_CREATED_VIEW = 2
    }

    var onItemClick: ((FoodRecipe, Int) -> Unit)? = null
    val storageRef = FirebaseStorage.getInstance()
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val recipeName : TextView = listItemView.findViewById(R.id.recipeNameManagementTextView)
        val recipeIMG = listItemView.findViewById<ImageView>(R.id.recipeManagementImageView)
        val numOfLike : TextView = listItemView.findViewById(R.id.numOfLikesTextView)
        val authorName : TextView = listItemView.findViewById(R.id.authorName)
        val authorAvatarIMG : ImageView = listItemView.findViewById(R.id.avatarImageView)
        val uploadDate : TextView = listItemView.findViewById(R.id.uploadDateTextView)
        val isPublic : TextView = listItemView.findViewById(R.id.isPublicTextView)
        val listItemView = listItemView

        init {
            listItemView.setOnClickListener {
                onItemClick?.invoke(
                    recipeRenderArray.keys.toTypedArray()[adapterPosition], adapterPosition
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

        if (recipeName.length() > 16 && isRecipeSavedView) {
            recipeName.text = recipeName.text.substring(0, 16) + "..."
        } else if(recipeName.length() > 13 && isRecipeCreatedView) {
            recipeName.text = recipeName.text.substring(0, 13) + "..."
        }
        else{
            recipeName.text = recipeName.text
        }


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

        // xử lý khi click vào item
        auth = FirebaseAuth.getInstance()
        val user_id = auth.currentUser?.uid

        val option_saved_recipe = holder.listItemView.findViewById<ImageView>(R.id.option_saved_recipe)
        val option_created_recipe = holder.listItemView.findViewById<ImageView>(R.id.option_created_recipe)
        val list_option_saved = listOf("Xóa", "Xem chi tiết")
        val list_option_created = listOf("Xóa", "Cập nhật", "Chia sẻ")
        val list_option_created1 = listOf("Xóa", "Cập nhật", "Hủy chia sẻ")

        if(isRecipeSavedView) {
            option_saved_recipe.setOnClickListener {
                val popupMenu = PopupMenu(context, option_saved_recipe)
                for (i in list_option_saved.indices) {
                    popupMenu.menu.add(Menu.NONE, i, i, list_option_saved[i])
                }
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        0 -> {
                            val mapUpdate = mapOf(
                                "foodRecipeSaved" to FieldValue.arrayRemove(recipeRender.id)
                            )
                            db.collection("users").document(user_id.toString()).update(mapUpdate)

                            recipeRenderArray.remove(recipeRender)
                            notifyDataSetChanged()
                        }
                        1 -> {
                            val current_foodRecipe = recipeRender
                            val intent = Intent(context, ShowRecipeDetailsActivity::class.java)
                            intent.putExtra("foodRecipe", current_foodRecipe)
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
                    if(recipeRender.isPublic) {
                        popupMenu.menu.add(Menu.NONE, i, i, list_option_created1[i])
                    }
                    else {
                        popupMenu.menu.add(Menu.NONE, i, i, list_option_created[i])
                    }
                }
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        0 -> {
                            // cập nhật lại danh sách món ăn của user
                            val mapUpdate = mapOf(
                                "myFoodRecipes" to FieldValue.arrayRemove(recipeRender.id)
                            )
                            db.collection("users").document(user_id.toString()).update(mapUpdate)

                            // xóa trong danh sách foodRecipeSaved của user khác
                            db.collection("users")
                                .whereArrayContains("foodRecipeSaved", recipeRender.id)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        val mapUpdate = mapOf(
                                            "foodRecipeSaved" to FieldValue.arrayRemove(recipeRender.id)
                                        )
                                        db.collection("users").document(document.id).update(mapUpdate)
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                }

                            // lấy danh sách comment của món ăn
                            val recipeCmts = recipeRender.recipeCmts
                            // xóa comment trong danh sách comment của user khác
                            for (recipeCmt in recipeCmts) {
                                db.collection("users")
                                    .whereArrayContains("recipeCmts", recipeCmt)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val mapUpdate = mapOf(
                                                "recipeCmts" to FieldValue.arrayRemove(recipeCmt)
                                            )
                                            db.collection("users").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }
                            }

                            // xóa comment trong bảng comment
                            for(recipeCmt in recipeCmts){
                                db.collection("RecipeCmts")
                                    .whereEqualTo("id", recipeCmt)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("RecipeCmts").document(document.id).delete()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }
                            }

                            // xóa trong danh sách foodRecipes của bảng RecipeCates
                            db.collection("RecipeCates")
                                .whereArrayContains("foodRecipes", recipeRender.id)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        val mapUpdate = mapOf(
                                            "foodRecipes" to FieldValue.arrayRemove(recipeRender.id)
                                        )
                                        db.collection("RecipeCates").document(document.id).update(mapUpdate)
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                }

                            // xóa trong danh sách foodRecipes của bảng RecipeDiets
                            db.collection("RecipeDiets")
                                .whereArrayContains("foodRecipes", recipeRender.id)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        val mapUpdate = mapOf(
                                            "foodRecipes" to FieldValue.arrayRemove(recipeRender.id)
                                        )
                                        db.collection("RecipeDiets").document(document.id).update(mapUpdate)
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                }

                            // xóa Món ăn
                            db.collection("RecipeFoods")
                                .whereEqualTo("id", recipeRender.id)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        db.collection("RecipeFoods").document(document.id).delete()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                }

                            recipeRenderArray.remove(recipeRender)
                            notifyDataSetChanged()
                        }
                        1 -> {
                            // chuyển đến trang cập nhật

                        }
                        2 -> {
                            // chia sẻ
                            if(recipeRender.isPublic) {
                                val mapUpdate = mapOf(
                                    "public" to false
                                )
                                db.collection("RecipeFoods")
                                    .whereEqualTo("id", recipeRender.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("RecipeFoods").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                recipeRender.isPublic = false
                                notifyDataSetChanged()
                            }
                            else {
                                val mapUpdate = mapOf(
                                    "public" to true
                                )
                                db.collection("RecipeFoods")
                                    .whereEqualTo("id", recipeRender.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("RecipeFoods").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                recipeRender.isPublic = true
                                notifyDataSetChanged()
                            }

                        }
                    }
                    true
                }
                popupMenu.show()
            }

        }

    }
}

