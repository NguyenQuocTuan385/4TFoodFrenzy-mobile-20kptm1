package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.R.layout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.reflect.Field
import java.util.*

class WriteCommentActivity : AppCompatActivity() {

    var imageUri: Uri? = null
    var imgView: ImageView? = null
    var removeImageConstraintLayout: ConstraintLayout? = null
    var removeImgButton: LinearLayout? = null
    var haveChosenImage = false
    var submitCommentButton: TextView? = null
    val db = Firebase.firestore
    var commentEditText : TextView? = null
    val storageRef = Firebase.storage

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_write_comment)

        val numberID : Long = intent?.extras?.getLong("commentID") as Long
        val currentRecipe : FoodRecipe = intent?.extras?.get("foodRecipe") as FoodRecipe
        val cancelButton: TextView = findViewById(R.id.cancelTextViewBtn)

        // intent
        val intent = Intent(this, ShowRecipeDetailsActivity::class.java)

        intent.putExtra("foodRecipe", currentRecipe)

        // find view
        imgView = findViewById(R.id.commentFoodImageView)
        removeImageConstraintLayout = findViewById(R.id.removeCommentImageConstraintLayout)
        removeImgButton = findViewById(R.id.removeCommentImageLinearLayout)
        submitCommentButton = findViewById(R.id.submitTextViewBtn)
        commentEditText = findViewById(R.id.commentEditText)

        // handle submit comment (update on database)
        submitCommentButton?.setOnClickListener {
            val description = commentEditText?.text

            // check if content is null
            if(description == null || description == ""){
                Toast.makeText(this, "Nội dung không được để trống", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            uploadImage(numberID)
            updateCommentOnDB(numberID, description.toString())

            // end current write comment activity
            startActivity(intent)
            finish()
        }

        // handle click on add image section, get image from local phone storage
        imgView?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

        // remove added image
        removeImgButton?.setOnClickListener {
            // set image to default find image icon
            imgView?.setImageResource(R.drawable.find_image_icon)

            // hide remove image section
            removeImageConstraintLayout?.visibility = View.INVISIBLE
            imgView?.scaleType = ImageView.ScaleType.FIT_CENTER

            imageUri = null
            haveChosenImage = false
        }

        // cancel writing comment -> return to recipe details
        cancelButton.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data?.data
            imgView?.setImageURI(imageUri)
            imgView?.scaleType = ImageView.ScaleType.CENTER_CROP

            haveChosenImage = true
            removeImageConstraintLayout?.visibility = View.VISIBLE
        }
    }

    // update comment on firestore
    private fun updateCommentOnDB(numberID: Long, description: String) {
        // find document ID by using numberID
        db.collection("RecipeCmts").whereEqualTo("id", numberID)
            .get()
            .addOnSuccessListener {document ->
                if(!document.isEmpty){
                    // get doc ID
                    val documentID = document.elementAt(0).id

                    var imageURL = if(imageUri == null) null else "comments/${numberID}"

                    // write comment desciption to DB
                    db.collection("RecipeCmts").document(documentID)
                        .update("description", description, "date", Date(), "image", imageURL)
                }
            }
    }

    private fun uploadImage(commentNumberID : Long){
        if(imageUri == null)
            return

        val imageRef = storageRef.reference.child("comments/${commentNumberID}")
        val uploadTask = imageRef.putFile(imageUri.toString().toUri())
    }
}