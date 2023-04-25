package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity1 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var imageRecipe:ImageView
    private lateinit var nameRecipeEdit:EditText
    private var imagePath:Uri?=null

    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe1)
        initToolbar()
        pickImage()
        setBackToolbar()
        setupContinueButton()
        setupCloseToolbar()
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }
    }

    private fun setupContinueButton() {
        nameRecipeEdit=findViewById(R.id.nameRecipeEdit)
        continueBtn = findViewById(R.id.continueBtn)
        continueBtn.setOnClickListener {
            if(nameRecipeEdit.text.isNullOrBlank()) {
                HelperFunctionDB(this).showRemindAlert("Bạn chưa điền tên món")
                return@setOnClickListener
            }
            if(imagePath==null)
            {
                HelperFunctionDB(this).showRemindAlert("Bạn chưa thêm hình ảnh")
                return@setOnClickListener
            }

            val intent=Intent(this, AddRecipeActivity2::class.java)
            sendData(intent)
            startActivity(intent)
        }
    }
    private fun sendData(intent: Intent)
    {
        intent.putExtra("name",nameRecipeEdit.text.toString())
        intent.putExtra("mainImage",imagePath.toString())
    }
    private fun setupCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    startActivity(Intent(this, AddNewRecipe::class.java))
                    true
                }
                else -> false
            }
        }
    }
    private fun pickImageGallery()
    {
        val intent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    private fun pickImage()
    {
        imageRecipe=findViewById(R.id.imageRecipe)

        imageRecipe.setOnClickListener {
            pickImageGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== IMAGE_REQUEST_CODE&&resultCode== RESULT_OK)
        {
            imageRecipe.setImageURI(data?.data)
            imagePath=data?.data as Uri
        }
    }


}