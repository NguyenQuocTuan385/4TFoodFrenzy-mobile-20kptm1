package com.example.a4tfoodfrenzy.Controller.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.storage.FirebaseStorage

class AddRecipeActivity1 : AppCompatActivity() {
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var continueBtn: Button
    private lateinit var imageRecipe:ImageView
    private lateinit var nameRecipeEdit:EditText
    private var imagePath:Uri?=null
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var foodRecipe:FoodRecipe
    private var category:String=""
    private var check=-1


    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe1)
        initView()
        recieveData()
        pickImage()
        setBackToolbar()
        setupContinueButton()
        setupCloseToolbar()
    }
    private fun initView()
    {
        toolbarAddRecipe = findViewById(R.id.toolbarAddRecipe)
        nameRecipeEdit=findViewById(R.id.nameRecipeEdit)
        imageRecipe=findViewById(R.id.imageRecipe)
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener {
            deleteAllSharePreference()
            finish() }
    }

    private fun setupContinueButton() {

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)

        }
    }
    private fun deleteAllSharePreference()
    {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    private fun recieveData()
    {
        val temp =intent.getParcelableExtra<FoodRecipe>("foodRecipe")
        category= intent.getStringExtra("cate").toString()
        if(temp!=null)
        {
            foodRecipe=temp
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            imagePath=Uri.parse(foodRecipe.recipeMainImage.toString())

            val pathReference = storageRef.child(foodRecipe.recipeMainImage.toString())
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                // Use Glide to load the image from the URL
                Glide.with(this)
                    .load(uri.toString())
                    .into(imageRecipe)
            }
            nameRecipeEdit.setText(foodRecipe.recipeName)
            check=1
        }
        else
        {
            foodRecipe= FoodRecipe()
        }
    }
    private fun sendData(intent: Intent)
    {
        foodRecipe.recipeName=nameRecipeEdit.text.toString()
        foodRecipe.recipeMainImage=imagePath.toString()
        intent.putExtra("foodRecipe",foodRecipe)
        intent.putExtra("cate",category)
        intent.putExtra("check",check)

    }
    private fun setupCloseToolbar() {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    deleteAllSharePreference()
                    finish()
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
        if(requestCode== IMAGE_REQUEST_CODE &&resultCode== RESULT_OK)
        {
            imageRecipe.setImageURI(data?.data)
            imagePath=data?.data
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