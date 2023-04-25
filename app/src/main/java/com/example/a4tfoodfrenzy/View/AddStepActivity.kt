package com.example.a4tfoodfrenzy.View

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import java.io.InputStream

class AddStepActivity : AppCompatActivity() {
    private lateinit var toolbarAddStep: MaterialToolbar
    private lateinit var imageRecipe: ImageView
    private lateinit var descriptionStepEdit:EditText
    private var uri: Uri?=null

    private var index:Int=-1
    private val IMAGE_REQUEST_CODE=100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_step)
        initToolbar()
        imageRecipe=findViewById(R.id.imageRecipe)
        descriptionStepEdit=findViewById(R.id.descriptionStepEdit)

        imageRecipe.setOnClickListener {
            pickImageGallery()
        }
        val mode = intent.getStringExtra("mode")
        val numberStep=intent.getStringExtra("stepNumber")
        toolbarAddStep.setTitle("Bước ${numberStep}")
        if(mode.equals("edit")) {
            val step= intent.getParcelableExtra<RecipeCookStep>("step") as RecipeCookStep
            index=intent.getIntExtra("index",-1)
            if(step.image.isNullOrEmpty())
            {
                imageRecipe.setImageResource(R.drawable.upload)
            }
             else
            {

                uri= Uri.parse(step.image)
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageRecipe.setImageBitmap(bitmap)
            }

            descriptionStepEdit.setText(step.description)
        }
        setBackToobar()
        setCloseToobar()
    }
    private fun initToolbar()
    {
        toolbarAddStep=findViewById(R.id.toolbarAddStep)
    }
    private fun setBackToobar()
    {
        toolbarAddStep.setNavigationOnClickListener {
            finish()
        }
    }
    private fun setCloseToobar()
    {
        toolbarAddStep.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    saveStep()
                    // Xử lý khi người dùng chọn Save
                    true
                }
                else -> false
            }
        }
    }
    private fun saveStep()
    {
        val des=descriptionStepEdit.text.toString()
        val recipeCookStep=RecipeCookStep(des,uri?.toString())
        val intent=Intent()
        intent.putExtra("step",recipeCookStep)
        intent.putExtra("index",index)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== AddRecipeActivity1.IMAGE_REQUEST_CODE &&resultCode== RESULT_OK)
        {
            imageRecipe.setImageURI(data?.data)
            uri= data?.data!!
        }
    }




}
