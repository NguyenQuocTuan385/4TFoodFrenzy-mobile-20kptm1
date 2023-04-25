package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
        val foodNameTextView: TextView = findViewById(R.id.foodNameTitleTextView)
        val authorNameTextView: TextView = findViewById(R.id.authorFullnameTextView)
        val authorAvatarImageView: ImageView = findViewById(R.id.authorAvatarImageView)
        val authorTotalRecipeTextView: TextView = findViewById(R.id.totalRecipeTextView)
        val authorBioTextView: TextView = findViewById(R.id.bioTextView)
        val ingredientDetailsTextView: TextView = findViewById(R.id.ingredientDetailsTextView)
        val stepsInstructionTextView: TextView = findViewById(R.id.stepsInstructionTextView)
        val likePercent: TextView = findViewById(R.id.percentRecookTextView)
        val totalComment: TextView = findViewById(R.id.totalCommentTextView)
        val commentSection: ConstraintLayout = findViewById(R.id.commentSectionConstraintLayout)
        val rationTextView: TextView = findViewById(R.id.foodRationTextView)
        val dietTextView: TextView = findViewById(R.id.foodDietTextView)
        val categoryTextView: TextView = findViewById(R.id.foodCategoryTextView)

        // other variables
        val storageRef = Firebase.storage
        val currentFoodRecipe: FoodRecipe? =
            intent.extras?.getParcelable("foodRecipe")!!
        val imagePathList = arrayListOf(currentFoodRecipe!!.recipeMainImage)
        var recipeAuthor: User? = null
        var ingredientString: CharSequence = ""
        var stepString: CharSequence = ""
        var dietString = ""
        var cateString = ""

        // generate diet string list from DB
        for (diet in DBManagement.recipeDietList) {
            if (diet.foodRecipes.contains(currentFoodRecipe.id))
                dietString += "${diet.dietName}, "
        }
        if (dietString.length >= 2)
            dietString = dietString.substring(0, dietString.length - 2)

        // generate category string list from DB
        for (category in DBManagement.recipeCateList) {
            if (category.foodRecipes.contains(currentFoodRecipe.id)) {
                cateString += "${category.recipeCateName}, "
            }
        }
        if (cateString.length >= 2)
            cateString = cateString.substring(0, cateString.length - 2)

        // generate ingredient text
        for ((i, ingredient) in currentFoodRecipe.recipeIngres.withIndex()) {
            val ingredientQuantityAndUnitString =
                SpannableString("${ingredient.ingreQuantity.toInt()} ${ingredient.ingreUnit}")

            ingredientQuantityAndUnitString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                ingredientQuantityAndUnitString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            ingredientString = TextUtils.concat(
                ingredientString,
                "${i + 1}. ${ingredient.ingreName} ",
                ingredientQuantityAndUnitString,
                "\n\n"
            )
        }

        // generate step string
        for ((i, step) in currentFoodRecipe.recipeSteps.withIndex()) {
            // sub text is step title number
            val subtext = SpannableString("Bước ${i + 1}:")

            // setSpan to make step title number bold from 0 (start) to length(end)
            subtext.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                subtext.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // concat step title number with step description then concat those with the main step string
            stepString = TextUtils.concat(stepString, subtext, "\n${step.description}\n\n")
        }

        // find user in user list
        for (user in DBManagement.userList) {
            if (user.myFoodRecipes.contains(currentFoodRecipe.id)) {
                recipeAuthor = user
                break
            }
        }

        // assign img url to list
        for (step in currentFoodRecipe.recipeSteps) {
            // check null image
            if (step.image != null)
                imagePathList.add(step.image)
        }

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
        if (currentFoodRecipe.recipeCmts.size == 0)
            commentSection.visibility = View.GONE

        // set data for view
        foodNameTextView.text = currentFoodRecipe.recipeName
        authorBioTextView.text = recipeAuthor?.bio
        authorNameTextView.text = recipeAuthor?.fullname
        authorTotalRecipeTextView.text = recipeAuthor?.myFoodRecipes?.size.toString()
        ingredientDetailsTextView.text = ingredientString
//            HtmlCompat.fromHtml(ingredientString, HtmlCompat.FROM_HTML_MODE_LEGACY)
        stepsInstructionTextView.text = stepString
//            HtmlCompat.fromHtml(stepString, HtmlCompat.FROM_HTML_MODE_LEGACY)
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

            myIntent.putExtra("stepFoodRecipe", currentFoodRecipe)

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

    fun showText(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
//    fun calculateLike() : Double{
//        var res : Double = 0.0
//
//        return res
//    }
}
