package com.example.a4tfoodfrenzy.View

import android.Manifest
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
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream

class AddStepActivity : AppCompatActivity() {
    private lateinit var toolbarAddStep: MaterialToolbar
    private lateinit var imageRecipe: ImageView
    private lateinit var descriptionStepEdit:EditText
    private var uri: Uri?=null

    private var index:Int=-1
    private val IMAGE_REQUEST_CODE=100
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123


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
                 // load từ database
                if (step.image!!.startsWith("foods/")) {
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.reference
                    val pathReference = storageRef.child(step.image.toString())
                    pathReference.downloadUrl.addOnSuccessListener { uri ->
                        Glide.with(this)
                            .load(uri.toString())
                            .into(imageRecipe)
                    }
                }
                //load từ điện thoại
                else {

                    uri = Uri.parse(step.image)
                    imageRecipe.setImageURI(uri)
                }
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




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== AddRecipeActivity1.IMAGE_REQUEST_CODE &&resultCode== RESULT_OK)
        {
            imageRecipe.setImageURI(data?.data)
            uri= data?.data!!
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
