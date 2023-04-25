package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
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
        val mainScrollView: ScrollView =
            findViewById(R.id.showRecipeDetailsActivityScrollViewContainer)

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
        val currentUserCommented = isNotExistComment(currentFoodRecipe)

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

        // find user aka author of current recipe in user list
        for (user in DBManagement.userList) {
            if (user.myFoodRecipes.contains(currentFoodRecipe.id)) {
                recipeAuthor = user
                break
            }
        }

        // add img url to list for recycler view
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
        }?.addOnFailureListener {}

        // hide comments if there is no comment
        if (currentFoodRecipe.recipeCmts.size == 0)
            commentSection.visibility = View.GONE

        // hide write comment button if not logged in yet
        if (!isLoggedIn() || !currentUserCommented) {
            writeCommentButton.visibility = View.GONE
        }

        // set data for view
        foodNameTextView.text = currentFoodRecipe.recipeName
        authorBioTextView.text = if (recipeAuthor?.bio == null) "Không có bio" else recipeAuthor.bio
        authorNameTextView.text = recipeAuthor?.fullname
        authorTotalRecipeTextView.text =
            if (recipeAuthor?.myFoodRecipes == null) "0" else recipeAuthor.myFoodRecipes.size.toString()
        ingredientDetailsTextView.text = ingredientString
        stepsInstructionTextView.text = stepString
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
            mainScrollView.scrollTo(0, 0)

            myIntent.putExtra("stepFoodRecipe", currentFoodRecipe)

            startActivity(myIntent)
        }

        writeCommentButton.setOnClickListener {
//            val myIntent = Intent(this, WriteCommentActivity::class.java)
//            myIntent.putExtra("currentRecipeID", currentFoodRecipe.id)
//            showText(isNotExistComment(currentFoodRecipe).toString(), this)
//            startActivity(myIntent)

            showLikeDislikePopup()
        }

        // back to previous navigation icon on toolbar
        toolbarBackButton.setOnClickListener {
//            val myIntent = Intent(this, AfterSearchActivity::class.java)
//            startActivity(myIntent)
            finish()
        }

        //
        val toListComment = findViewById<LinearLayout>(R.id.list_commentLinearLayout)

        toListComment.setOnClickListener {
            val myIntent = Intent(this, CommentListActivity::class.java)
            myIntent.putExtra("foodComment", currentFoodRecipe)
            startActivity(myIntent)
        }
    }

    private fun isLoggedIn(): Boolean {
        if (DBManagement.user_current == null)
            return false
        return true
    }

    // check if current user commented on the current recipe or not
    // if commented -> false else true
    private fun isNotExistComment(currentFoodRecipe: FoodRecipe): Boolean {
        if (!isLoggedIn())
            return false

        val currentUserCmtList = DBManagement.user_current?.recipeCmts
        val currentFoodCmtList = currentFoodRecipe.recipeCmts

        if (currentUserCmtList?.size != 0) {
            if (currentUserCmtList != null) {
                if (currentFoodCmtList.any { it -> it in currentUserCmtList })
                    return false
            }
        }
        return true
    }

    private fun showLikeDislikePopup() {
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_like_dislike, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        // views (button, text view,...) in popup window
        val dislikeButton = popupView.findViewById<ImageButton>(R.id.firstDislikeButton)
        val likeButton = popupView.findViewById<ImageButton>(R.id.firstLikeButton)
        val cancelButton = popupView.findViewById<Button>(R.id.first_cancel_button)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(LinearLayout(this), Gravity.CENTER, 0, 0)

        // set listener for button inside popup window
        // dislike button clicked --> pop dislike popup
        dislikeButton.setOnClickListener {
            showNextPopup(false)
            popupWindow.dismiss()
        }

        // like button clicked --> pop like popup
        likeButton.setOnClickListener {
            showNextPopup(true)
            popupWindow.dismiss()
        }

        // Close current popup
        cancelButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun showNextPopup(layoutType: Boolean) {
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(if(layoutType) R.layout.popup_like else R.layout.popup_dislike, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        // views (button, text view,...) in popup window
        val cancelButton = popupView.findViewById<Button>(if(layoutType) R.id.secondCancelButton else R.id.thirdCancelButton)
        val yesButton = popupView.findViewById<Button>(if(layoutType) R.id.secondYesButton else R.id.thirdYesButton)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(LinearLayout(this), Gravity.CENTER, 0, 0)

        cancelButton.setOnClickListener {
            popupWindow.dismiss()
        }

        yesButton.setOnClickListener {

        }
    }

    private fun showText(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
