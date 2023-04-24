package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Adapter.FoodImageAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ShowRecipeDetailsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details)

        // layout view
        val rv = findViewById<RecyclerView>(R.id.foodImageRecyclerView)
        val mainIMG: ImageView = findViewById(R.id.mainFoodImageView)
        val showStepDetailsButton: Button = findViewById(R.id.moreDetailsButton)
        val writeCommentButton: Button = findViewById(R.id.writeCommentButton)
        val toolbarBackButton = findViewById<ImageView>(R.id.toolbarBackButton)
        val foodNameTextView : TextView = findViewById(R.id.foodNameTitleTextView)
        val authorNameTextView : TextView = findViewById(R.id.authorFullnameTextView)
        val authorAvatarImageView : ImageView = findViewById(R.id.authorAvatarImageView)
        val authorTotalRecipeTextView : TextView = findViewById(R.id.totalRecipeTextView)
        val authorBioTextView : TextView = findViewById(R.id.bioTextView)
        val ingredientDetailsTextView : TextView = findViewById(R.id.ingredientDetailsTextView)
        val stepsInstructionTextView : TextView = findViewById(R.id.stepsInstructionTextView)
        val likePercent : TextView = findViewById(R.id.percentRecookTextView)
        val totalComment : TextView = findViewById(R.id.totalCommentTextView)
        val commentSection : ConstraintLayout = findViewById(R.id.commentSectionConstraintLayout)
        val rationTextView : TextView = findViewById(R.id.foodRationTextView)
        val dietTextView : TextView = findViewById(R.id.foodDietTextView)
        val categoryTextView : TextView = findViewById(R.id.foodCategoryTextView)

        // other variables
        val storageRef = Firebase.storage
        val currentFoodRecipe: FoodRecipe? = intent.extras?.getParcelable("foodRecipe", FoodRecipe::class.java)!!
        val imagePathList = arrayListOf(currentFoodRecipe!!.recipeMainImage)
        var recipeAuthor : User? =  null
        var ingredientString = ""
        var stepString = ""
        var dietString = ""
        var cateString = ""

        // generate diet string list from DB
        for((i, diet) in DBManagement.recipeDietList.withIndex()){
            val temp = diet.foodRecipes.filter { it == currentFoodRecipe.id }

            if(temp.isNotEmpty())
                if(temp[0] == currentFoodRecipe.id)
                    dietString += "${diet.dietName}, "
        }
        dietString = dietString.substring(0, dietString.length - 2)

        // generate category string list from DB
        for((i, category) in DBManagement.recipeCateList.withIndex()){
            val temp = category.foodRecipes.filter { it == currentFoodRecipe.id }

            if(temp.isNotEmpty())
                if(temp[0] == currentFoodRecipe.id)
                    cateString += "${category.recipeCateName}, "
        }
        cateString = cateString.substring(0, cateString.length - 2)

        // generate ingredient text
        for((i, ingredient) in currentFoodRecipe.recipeIngres.withIndex())
            ingredientString += "${i + 1}. ${ingredient.ingreName}  <b>${ingredient.ingreQuantity} ${ingredient.ingreUnit}</b><br>"

        // generate step string
        for((i, step) in currentFoodRecipe.recipeSteps.withIndex()){
            stepString += "<b> Bước ${i + 1}: </b> <br>${step.description}<br><br>"
        }

        // find user in user list
        for(user in DBManagement.userList)
            if(user.myFoodRecipes.filter { it == currentFoodRecipe.id }[0] == currentFoodRecipe.id){
                recipeAuthor = user
                break
            }

        // assign img url to list
        for(step in currentFoodRecipe.recipeSteps)
            imagePathList.add(step.image)

        // assign user avatar image
        val authorImgRef = recipeAuthor?.avatar?.let { storageRef.getReference(it) }

        authorImgRef?.downloadUrl?.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(authorAvatarImageView)
        }?.addOnFailureListener {
            // Xử lý lỗi
        }

        // hide comment section if there is no comment
        if(currentFoodRecipe.recipeCmts.size == 0)
            commentSection.visibility = View.GONE

        // set data for view
        foodNameTextView.text = currentFoodRecipe.recipeName
        authorBioTextView.text = recipeAuthor?.bio
        authorNameTextView.text = recipeAuthor?.fullname
        authorTotalRecipeTextView.text = recipeAuthor?.myFoodRecipes?.size.toString()
        ingredientDetailsTextView.text = HtmlCompat.fromHtml(ingredientString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        stepsInstructionTextView.text = HtmlCompat.fromHtml(stepString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        rationTextView.text = currentFoodRecipe.ration.toString() + " người"
        totalComment.text = "(${currentFoodRecipe.recipeCmts.size})"
        dietTextView.text = dietString
        categoryTextView.text = cateString

        // food image horizontal recycler view
        val adapter = FoodImageAdapter(imagePathList, this)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false


        // set first main image
        val mainImgRef = currentFoodRecipe.recipeMainImage?.let { storageRef.getReference(it) }
        mainImgRef?.downloadUrl?.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(mainIMG)
        }?.addOnFailureListener {
            // Xử lý lỗi
        }

        // set on image recycler view item click listener
        adapter.onImageClick = { imgView, _ ->
            mainIMG.setImageDrawable(imgView)
        }

        // carousel showing step detail activity
        showStepDetailsButton.setOnClickListener {
            val myIntent = Intent(this, ShowRecipeDetailsDescriptionActivity::class.java)

            myIntent.putExtra("stepList", currentFoodRecipe.recipeSteps)

            startActivity(myIntent)
        }

        writeCommentButton.setOnClickListener {
            val myIntent = Intent(this, WriteCommentActivity::class.java)
            startActivity(myIntent)
        }
        toolbarBackButton.setOnClickListener {
            val myIntent = Intent(this, AfterSearchActivity::class.java)
            startActivity(myIntent)
        }

        val toListComment = findViewById<LinearLayout>(R.id.list_commentLinearLayout)
        toListComment.setOnClickListener {
            val myIntent = Intent(this, CommentListActivity::class.java)
            startActivity(myIntent)
        }
    }

//    fun calculateLike() : Double{
//        var res : Double = 0.0
//
//        return res
//    }
}
