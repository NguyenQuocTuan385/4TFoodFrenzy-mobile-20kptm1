package com.example.a4tfoodfrenzy.Controller.Activity

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
import android.util.Log
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
import com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter.FoodImageAdapter
import com.example.a4tfoodfrenzy.BroadcastReceiver.ConstantAction
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 */
class ShowRecipeDetailsActivity : AppCompatActivity() {
    val db: FirebaseFirestore = Firebase.firestore
    private var _commentID: Long = -1
    private lateinit var currentFoodRecipe: FoodRecipe
    private var recipeAuthor: User? = null
    private var cmtContent : String? = null
    private var cmtDate : Date? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
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
        val totalCalo : TextView = findViewById(R.id.totalCaloTextView)
        val totalCommentTextView: TextView = findViewById(R.id.totalCommentTextView)
        val commentSection: ConstraintLayout = findViewById(R.id.commentSectionConstraintLayout)
        val rationTextView: TextView = findViewById(R.id.foodRationTextView)
        val dietTextView: TextView = findViewById(R.id.foodDietTextView)
        val categoryTextView: TextView = findViewById(R.id.foodCategoryTextView)
        val mainScrollView: ScrollView =
            findViewById(R.id.showRecipeDetailsActivityScrollViewContainer)
        val saveRecipeButton: ImageButton = findViewById(R.id.saveRecipeButtonImageView)
        val topCommentTextView : TextView = findViewById(R.id.bestCommentCommentTextView)
        val topCommentFullNameTextView : TextView = findViewById(R.id.bestCommentFullname)
        val topCommentDateTextView : TextView = findViewById(R.id.bestCommentDateTextView)
        val topCommentAvt : ImageView = findViewById(R.id.bestCommentAvatarImageView)


        // other variables
        val storageRef = Firebase.storage
        currentFoodRecipe =
            intent.extras?.getParcelable("foodRecipe") ?: return
        val imagePathList = arrayListOf(currentFoodRecipe.recipeMainImage)
        var ingredientString: CharSequence =
            "" // string for assign to text (charsequence to use spannable)
        var stepString: CharSequence = ""
        var dietString = "" // string for assign to text
        var cateString = "" // string for assign to text
        var totalComment = 0 // count total comment that not nu;;
        var totalLike = 0 // count total comment that is like
        var isSavedFood = false // for save button onclicklistener check
        var isNotCurrentUser = false// check if author of the recipe is current user

        // count total comment (non empty description comment in comment list)
        // filter to get comment
        val filteredCommentList = DBManagement.recipeCommentList.filter { comment ->
            currentFoodRecipe.recipeCmts.contains(comment.id)
        }

        // get recipe author from DBmanagement list
        recipeAuthor = DBManagement.userList.filter { user -> user.myFoodRecipes.contains(currentFoodRecipe.id) }[0]

        isNotCurrentUser = recipeAuthor?.id != DBManagement.user_current?.id

        for (comment in filteredCommentList) {
            // comment content is null or empty --> haven't write comment --> no count
            if (comment.description != "")
                totalComment++

            // count positive like
            if (comment.isLike)
                totalLike++
        }

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
        var ingreIndex = 0
        for (ingredient in currentFoodRecipe.recipeIngres) {
            if(ingredient.ingreQuantity == 0.0 || ingredient.ingreUnit == "")
                continue

            val ingredientQuantityAndUnitString =
                SpannableString("${ingredient.ingreQuantity.toInt()} ${ingredient.ingreUnit} (${if(ingredient.ingreCalo?.rem(1) == 0.0) ingredient.ingreCalo?.toInt() else ingredient.ingreCalo} calo)")

            ingredientQuantityAndUnitString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                ingredientQuantityAndUnitString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            ingredientString = TextUtils.concat(
                ingredientString,
                "${ingreIndex + 1}. ${ingredient.ingreName} | ",
                ingredientQuantityAndUnitString,
                "\n\n"
            )

            ++ingreIndex
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

            // concat step title number withr step description then concat those with the main step string
            stepString = TextUtils.concat(stepString, subtext, "\n${step.description}\n\n")
        }

        // add img url to list for recycler view
        for (step in currentFoodRecipe.recipeSteps) {
            // check null image
            if (step.image != null)
                imagePathList.add(step.image)
        }

        // assign user avatar image
        val authorImgRef = recipeAuthor?.avatar?.let { storageRef.getReference(it) }

        loadImageFromStorageToImageView(authorImgRef, authorAvatarImageView)

        // hide comments of users if there is no comment
        if (totalComment == 0)
            commentSection.visibility = View.GONE

        // hide write comment button if current user have already
        // commented on this recipe
        if ((isLoggedIn() && isCommented()) || !isNotCurrentUser) {
            writeCommentButton.visibility = View.GONE

            topCommentTextView.text = cmtContent
            topCommentFullNameTextView.text = DBManagement.user_current?.fullname
            topCommentDateTextView.text = cmtDate?.let { dateFormat.format(it) }

            // load top cmt's author's avatar
            val cmtAuthorAvtRef = DBManagement.user_current?.avatar?.let { storageRef.getReference(it) }

            loadImageFromStorageToImageView(cmtAuthorAvtRef, topCommentAvt)
        }
        else{ // haven't comment --> show latest comment of this recipe
            if(totalComment != 0){
                val latestComment = DBManagement.recipeCommentList.sortedByDescending { comment -> comment.date}.filter { comment -> currentFoodRecipe.recipeCmts.contains(comment.id) && comment.description != ""}[0]

                // find user with the latest comment
                val userOfLatestComment = DBManagement.userList.find { user -> user.recipeCmts.contains(latestComment.id) }

                topCommentTextView.text = latestComment.description
                topCommentFullNameTextView.text = userOfLatestComment?.fullname
                topCommentDateTextView.text = dateFormat.format(latestComment.date)

                // load top cmt's author's avatar
                val cmtAuthorAvtRef = userOfLatestComment?.avatar?.let { storageRef.getReference(it) }

                loadImageFromStorageToImageView(cmtAuthorAvtRef, topCommentAvt)
            }
        }

        if(!isNotCurrentUser){ // currenst user is current recipe's author --> hide save button
            saveRecipeButton.visibility = View.INVISIBLE
        }

        // check if user have already saved this recipe to change button state
        // user saved recipe
        if(isNotCurrentUser){
            isSavedFood = if (currentFoodRecipe.userSavedRecipes.contains(DBManagement.user_current?.id)) {
                saveRecipeButton.setImageResource(R.drawable.saved_recipe_button)

                true
            } else {
                saveRecipeButton.setImageResource(R.drawable.unsave_recipe_button)
                false
            }
        }

        // set data for view
        foodNameTextView.text = currentFoodRecipe.recipeName
        authorBioTextView.text = if (recipeAuthor?.bio == null) "Không có bio" else recipeAuthor!!.bio
        authorNameTextView.text = recipeAuthor?.fullname
        authorTotalRecipeTextView.text =
            if (recipeAuthor?.myFoodRecipes == null) "0" else recipeAuthor!!.myFoodRecipes.size.toString()
        ingredientDetailsTextView.text = ingredientString
        stepsInstructionTextView.text = stepString
        rationTextView.text = currentFoodRecipe.ration.toString() + " người"
        totalCommentTextView.text = "(${totalComment})"
        dietTextView.text = dietString
        categoryTextView.text = cateString
        likePercent.text =
            if (currentFoodRecipe.recipeCmts.isNotEmpty()) "${((totalLike.toDouble() / currentFoodRecipe.recipeCmts.size.toDouble()) * 100).toInt()}% người dùng sẽ nấu lại món này" else "Chưa có người dùng nào thả like cho món ăn này"

        // calculate total calo by sum of all calo of ingredients
        val totalCaloNum = currentFoodRecipe.recipeIngres.sumOf { ingredient -> ingredient.ingreCalo!! }

        //
        totalCalo.text = if(totalCaloNum.rem(1) == 0.0) totalCaloNum.toInt().toString() else totalCalo.toString()

        // food image horizontal recycler view
        val adapter = FoodImageAdapter(imagePathList, this)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        // set first main image
        val mainImgRef = currentFoodRecipe.recipeMainImage?.let { storageRef.getReference(it) }

        loadImageFromStorageToImageView(mainImgRef, mainIMG)


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

        // save button listener
        if(isNotCurrentUser){
            saveRecipeButton.setOnClickListener {
                if (!isLoggedIn()) {
                    launchLoginActivity()
                    return@setOnClickListener
                }

                // current state is saved --> change to unsaved
                if (isSavedFood) {
                    // change image source to unsave
                    saveRecipeButton.setImageResource(R.drawable.unsave_recipe_button)

                    // remove save info from db
                    unsaveRecipeFromDB() {boolean ->
                        isSavedFood = false
                        val intent1 = Intent(ConstantAction.ADD_SAVED_RECIPE_ACTION)
                        sendBroadcast(intent1)
                    }

                } else { // current state is un-saved --> change to saved
                    // change image source to saved
                    saveRecipeButton.setImageResource(R.drawable.saved_recipe_button)

                    // save food into DB
                    saveRecipeIntoDB() {boolean ->
                        val intent1 = Intent(ConstantAction.ADD_SAVED_RECIPE_ACTION)
                        sendBroadcast(intent1)
                        isSavedFood = true
                    }
                }
            }
        }

        // write comment listener
        writeCommentButton.setOnClickListener {
            if (!isLoggedIn()) {
                launchLoginActivity()
                return@setOnClickListener
            }

            val tempIsCommented = isCommented()

            if (isNotExistComment() && !tempIsCommented) {
                // if haven't like / dislike
                // call show popup function
                showLikeDislikePopup()
            } else if (!tempIsCommented) { // comment exist in DB but description == ""
                _commentID = getCmtID()

                val intent = Intent(this, WriteCommentActivity::class.java)

                intent.putExtra("commentID", _commentID)
                intent.putExtra("foodRecipe", currentFoodRecipe)

                startActivity(intent)
                finish()
            }
        }

        // back to previous navigation icon on toolbar
        toolbarBackButton.setOnClickListener {
            finish()
        }

        //
        val toListComment = findViewById<LinearLayout>(R.id.list_commentLinearLayout)

        toListComment.setOnClickListener {
            val myIntent = Intent(this, CommentListActivity::class.java)
            myIntent.putExtra("foodComment", currentFoodRecipe)
            startActivity(myIntent)
        }
        showProfile()
    }
    private fun showProfile()
    {
        val authorInfoCardView:ConstraintLayout=findViewById(R.id.authorInfoCardView)

        authorInfoCardView.setOnClickListener {
            if(recipeAuthor?.id==DBManagement.user_current?.id)
            {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                startActivity(intent)
            }
            else {
                val intent = Intent(this, ShowProfileActivity::class.java)
                intent.putExtra("profile", recipeAuthor)
                startActivityForResult(intent, 1)
            }
        }
    }

    private fun isLoggedIn(): Boolean {
        if (DBManagement.user_current == null)
            return false
        return true
    }

    // check if current user commented on the current recipe or not
    // if commented -> false else true
    private fun isNotExistComment(): Boolean {

        if (!isLoggedIn())
            return false

        val currentUserCmtList = DBManagement.user_current?.recipeCmts
        val currentFoodCmtList = currentFoodRecipe.recipeCmts

        if (currentUserCmtList?.size != 0) {
            if (currentUserCmtList != null) {
                if (currentFoodCmtList.any { it in currentUserCmtList }) {
                    return false
                }
            }
        }
        return true
    }

    private fun getCmtID(): Long {
        for (cmt in DBManagement.user_current!!.recipeCmts) {
            if (currentFoodRecipe.recipeCmts.contains(cmt)) {
                return cmt
            }
        }
        return -1
    }

    private fun isCommented(): Boolean {
        val commentID: Long = getCmtID()

        if (commentID == (-1).toLong())
            return false

        for (cmt in DBManagement.recipeCommentList) {
            if (cmt.id == commentID) {
                if (cmt.description == "")
                    return false

                if(cmtContent == null){
                    cmtContent = cmt.description
                    cmtDate = cmt.date
                }
                break
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showLikeDislikePopup() {
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_like_dislike, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
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
            addCommentToDB(false) {boolean ->
                if (boolean) {
                    val intent = Intent(ConstantAction.ADD_CMT_RECIPE_ACTION)
                    sendBroadcast(intent)
                    DBManagement.fetchDataUserCurrent {  }
                }
            }
            showNextPopup(false)
            popupWindow.dismiss()
        }

        // like button clicked --> pop like popup
        likeButton.setOnClickListener {
            addCommentToDB(true) {boolean ->
                if (boolean) {
                    val intent = Intent(ConstantAction.ADD_CMT_RECIPE_ACTION)
                    sendBroadcast(intent)
                    DBManagement.fetchDataUserCurrent {  }
                }
            }
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
        val popupView =
            inflater.inflate(if (layoutType) R.layout.popup_like else R.layout.popup_dislike, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )

        // views (button, text view,...) in popup window
        val cancelButton =
            popupView.findViewById<Button>(if (layoutType) R.id.secondCancelButton else R.id.thirdCancelButton)
        val yesButton =
            popupView.findViewById<Button>(if (layoutType) R.id.secondYesButton else R.id.thirdYesButton)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(LinearLayout(this), Gravity.CENTER, 0, 0)

        cancelButton.setOnClickListener {
            // add new cmt id into current food
            for (food in DBManagement.foodRecipeList)
                if (food.id == currentFoodRecipe.id)
                    currentFoodRecipe = food

            popupWindow.dismiss()
        }

        yesButton.setOnClickListener {
            val intent = Intent(this, WriteCommentActivity::class.java)

            for (food in DBManagement.foodRecipeList)
                if (food.id == currentFoodRecipe.id)
                    currentFoodRecipe = food

            intent.putExtra("commentID", _commentID)
            intent.putExtra("foodRecipe", currentFoodRecipe)
            intent.putExtra("user", recipeAuthor)

            popupWindow.dismiss()

            startActivity(intent)
            recreate()
            finish()
        }
    }

    // new null (empty description) comment into DB
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun addCommentToDB(isLike: Boolean, callback: (Boolean) -> Unit) {
        HelperFunctionDB(this).findSlotIdEmptyInCollection("RecipeCmts") { newID ->
            val cmt = RecipeComment(newID, "", "", 0, isLike, null, "", Date())
            _commentID = newID

            db.collection("RecipeCmts")
                .add(cmt)
                .addOnSuccessListener {
                    Log.d("hihi", "DocumentSnapshot successfully written!")
                    // get current food document ID
                    db.collection("RecipeFoods")
                        .whereEqualTo("id", currentFoodRecipe.id)
                        .get()
                        .addOnSuccessListener { document ->
                            if (!document.isEmpty) {
                                // add new cmt ID into food recipe
                                // increment total like if isLike = true
                                db.collection("RecipeFoods")
                                    .document(document.elementAt(0).id)
                                    .update("recipeCmts", FieldValue.arrayUnion(newID),
                                        "numOfLikes", if(isLike) FieldValue.increment(1)
                                        else FieldValue.increment(0))
                                    .addOnSuccessListener {
                                        // find user by ID then add new cmt to that user
                                        db.collection("users")
                                            .whereEqualTo(
                                                "id", (DBManagement.user_current?.id)).get()
                                            .addOnSuccessListener { document ->
                                                if (!document.isEmpty) {
                                                    // add new cmt ID into food recipe
                                                    db.collection("users")
                                                        .document(document.elementAt(0).id)
                                                        .update("recipeCmts", FieldValue.arrayUnion(newID))
                                                        .addOnSuccessListener {
                                                            callback(true)
                                                        }
                                                        .addOnFailureListener {
                                                            callback(false)
                                                        }
                                                }
                                            }
                                    }
                            }
                        }
                }
                .addOnFailureListener { e ->
                    Log.w("hihi", "Error writing document", e)
                    callback(false)
                }
        }
    }

    private fun showText(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }


    // add or remove current user's ID into / from current selected recipe food userSavedRecipes field on firestore
    private fun handleUserToRecipeFoodsCollection(taskType: Boolean, callback: (Boolean) -> Unit) {
        // add user id into recipefood table
        db.collection("RecipeFoods")
            .whereEqualTo("id", currentFoodRecipe.id)
            .get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val foodDocID = document.elementAt(0).id

                    // add userID to RecipeFoods savedArray with document ID
                    db.collection("RecipeFoods")
                        .document(foodDocID)
                        .update(
                            "userSavedRecipes",
                            if (taskType)
                                FieldValue.arrayUnion(DBManagement.user_current?.id)
                            else FieldValue.arrayRemove(
                                DBManagement.user_current?.id
                            )
                        ).addOnSuccessListener {
                            callback(true)
                        }.addOnFailureListener {
                            callback(false)
                        }
                }
            }.addOnFailureListener {
                callback(false)
            }
    }

    // add or remove selected recipe food's ID into / from current users's foodRecipeSaved field on firestore
    private fun handleRecipeToUserCollection(taskType: Boolean, callback: (Boolean) -> Unit) {
        // add RecipeFood ID to user table
        db.collection("users")
            .whereEqualTo("id", DBManagement.user_current?.id)
            .get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val userDocID = document.elementAt(0).id

                    // add userID to RecipeFoods savedArray with document ID
                    db.collection("users")
                        .document(userDocID)
                        .update(
                            "foodRecipeSaved",
                            if (taskType)
                                FieldValue.arrayUnion(currentFoodRecipe.id)
                            else
                                FieldValue.arrayRemove(currentFoodRecipe.id)
                        ).addOnSuccessListener {
                            callback(true)
                        }.addOnFailureListener {
                            callback(false)
                        }
                }
            }.addOnFailureListener {
                callback(false)
            }
    }

    // remove (unsave) all info from firestore by calling functions
    private fun unsaveRecipeFromDB(callback: (Boolean) -> Unit) {
        handleUserToRecipeFoodsCollection(false) {boolean ->
            if (boolean) {
                handleRecipeToUserCollection(false) {booleann ->
                    if (boolean) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }
        }
    }

    // save all needed info into firestore by calling functions
    private fun saveRecipeIntoDB(callback: (Boolean) -> Unit) {
        handleUserToRecipeFoodsCollection(true) {boolean ->
            if (boolean) {
                handleRecipeToUserCollection(true) {booleann ->
                    if (boolean) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }
        }
    }

    private fun launchLoginActivity() {
        // login activity intent
        val loginIntent = Intent(this, LoginRegisterActivity::class.java)

        loginIntent.putExtra("currentFoodIdToLogin", currentFoodRecipe.id)
        startActivity(loginIntent)
        this.finish()
    }

    // load image from storage into imageview
    private fun loadImageFromStorageToImageView(ref : StorageReference?, imgView : ImageView){
        // set first main image
        ref?.downloadUrl?.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(imgView)
        }?.addOnFailureListener {
            // Xử lý lỗi
        }
    }
}
