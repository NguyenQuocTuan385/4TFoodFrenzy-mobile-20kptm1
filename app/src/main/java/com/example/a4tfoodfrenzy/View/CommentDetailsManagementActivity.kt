package com.example.a4tfoodfrenzy.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class CommentDetailsManagementActivity : AppCompatActivity() {
    val storageRef = FirebaseStorage.getInstance()
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_details_management)

        var recipeName = findViewById<TextView>(R.id.recipeName)
        var timeUpload = findViewById<TextView>(R.id.timeUpload)
        var authorName = findViewById<TextView>(R.id.authorName)
        var authorAvatar = findViewById<ImageView>(R.id.authorAvatar)
        var comment_content = findViewById<TextView>(R.id.comment_content)
        var comment_img = findViewById<ImageView>(R.id.comment_img)
        val tvImage = findViewById<TextView>(R.id.textView22)
        val backTV = findViewById<TextView>(R.id.backTV)
        val deleteTV = findViewById<TextView>(R.id.deleteTV)

        val foodRecipe: FoodRecipe? = intent.extras?.getParcelable("foodRecipe")
        val recipeCmt: RecipeComment? = intent.extras?.getParcelable("recipeCmt")
        val user: User? = intent.extras?.getParcelable("user")
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)

        recipeName.text = foodRecipe!!.recipeName
        timeUpload.text = sdf.format(recipeCmt!!.date)
        authorName.text = user!!.fullname
        comment_content.text = recipeCmt.description

        val imageRef = user.avatar?.let { storageRef.getReference(it) }
        if (imageRef != null) {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(authorAvatar)
            }.addOnFailureListener { exception ->
                // Xử lý lỗi
            }
        }

        if (recipeCmt.image == null) {
            comment_img.visibility = View.GONE
            tvImage.visibility = View.GONE
        }
        else {
            comment_img.visibility = View.VISIBLE
            val imageRef = recipeCmt.image?.let { storageRef.getReference(it) }
            if (imageRef != null) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(this)
                        .load(uri)
                        .into(comment_img)
                }.addOnFailureListener { exception ->
                    // Xử lý lỗi
                }
            }
        }

        backTV.setOnClickListener {
            val replyIntent = Intent()
            setResult(2222, replyIntent)
            finish()
        }
        deleteTV.setOnClickListener {
            val collectionRecipeCmt = db.collection("RecipeCmts").whereEqualTo("id",recipeCmt.id)
            val collectionFoodRecipe = db.collection("RecipeFoods").whereEqualTo("id",foodRecipe.id)
            val collectionUser = db.collection("users").whereEqualTo("id",user.id)

            collectionRecipeCmt.get().addOnSuccessListener {documents ->
                for (document in documents) {
                    document.reference.delete()
                }
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error deleting documents", exception)
            }

            foodRecipe.recipeCmts.remove(recipeCmt.id)
            user.recipeCmts.remove(recipeCmt.id)

            collectionFoodRecipe.get().addOnSuccessListener {documents ->
                for (document in documents) {
                    document.reference.update("recipeCmts", foodRecipe.recipeCmts)
                }
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error deleting documents", exception)
            }

            collectionUser.get().addOnSuccessListener {documents ->
                for (document in documents) {
                    document.reference.update("recipeCmts",user.recipeCmts )
                }
            }.addOnFailureListener { exception ->
                Log.e("TAG", "Error deleting documents", exception)
            }
            val replyIntent = Intent()
            replyIntent.putExtra("recipeCmt",recipeCmt)
            setResult(1111, replyIntent)
            finish()
        }
    }
}