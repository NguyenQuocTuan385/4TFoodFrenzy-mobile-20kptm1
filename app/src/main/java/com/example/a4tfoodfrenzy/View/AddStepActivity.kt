package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddStepActivity : AppCompatActivity() {
    private lateinit var toolbarAddStep: MaterialToolbar
    private lateinit var imageRecipe: ImageView

    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_step)
        initToolbar()
        imageRecipe=findViewById(R.id.imageRecipe)
        imageRecipe.setOnClickListener {
            pickImageGallery()
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
                    finish()
                    // Xử lý khi người dùng chọn Save
                    true
                }
                else -> false
            }
        }
    }

    private fun pickImageGallery()
    {
        val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type="image/*"
        startActivityForResult(intent, AddRecipeActivity1.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== AddRecipeActivity1.IMAGE_REQUEST_CODE &&resultCode== RESULT_OK)
        {
            imageRecipe.setImageURI(data?.data)
        }
    }

}