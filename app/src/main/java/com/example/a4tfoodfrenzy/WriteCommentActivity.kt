package com.example.a4tfoodfrenzy

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.a4tfoodfrenzy.R.id
import com.example.a4tfoodfrenzy.R.layout

class WriteCommentActivity : AppCompatActivity() {

    var imageUri : Uri? = null
    var imgView : ImageView? = null
    var removeImageConstraintLayout : ConstraintLayout? = null
    var removeImgButton : LinearLayout? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_write_comment)

        imgView = findViewById(R.id.commentFoodImageView)
        removeImageConstraintLayout = findViewById(R.id.removeCommentImageConstraintLayout)
        removeImgButton = findViewById(R.id.removeCommentImageLinearLayout)

        imgView?.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

        // remove added image
        removeImgButton?.setOnClickListener{
            // set image to default find image icon
            imgView?.setImageResource(R.drawable.find_image_icon)

            // hide remove image section
            removeImageConstraintLayout?.visibility = View.INVISIBLE
            imgView?.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data?.data
            imgView?.setImageURI(imageUri)
            imgView?.scaleType = ImageView.ScaleType.CENTER_CROP

            removeImageConstraintLayout?.visibility = View.VISIBLE
        }
    }
}