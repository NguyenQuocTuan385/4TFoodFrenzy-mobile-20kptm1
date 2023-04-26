package com.example.a4tfoodfrenzy.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCategory
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar

class AddRecipeActivity1 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var imageRecipe:ImageView
    private lateinit var nameRecipeEdit:EditText
    private var imagePath:Uri?=null
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123

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
        val foodRecipe=FoodRecipe()
        foodRecipe.recipeName=nameRecipeEdit.text.toString()
        foodRecipe.recipeMainImage=imagePath.toString()
        intent.putExtra("foodRecipe",foodRecipe)
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
    private fun pickImageGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Quyền truy cập bộ nhớ ngoài chưa được cấp, yêu cầu cấp quyền
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
            return
        }
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }



}