package com.example.a4tfoodfrenzy.Controller.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.a4tfoodfrenzy.Api.Food
import com.example.a4tfoodfrenzy.Api.NinjasApiService
import com.example.a4tfoodfrenzy.Api.TranslateUtil
import com.example.a4tfoodfrenzy.BroadcastReceiver.ConstantAction
import com.example.a4tfoodfrenzy.BroadcastReceiver.InternetConnectionBroadcast
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.R.layout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import kotlin.reflect.KType

class WriteCommentActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private var imgView: ImageView? = null
    private var removeImageConstraintLayout: ConstraintLayout? = null
    private var removeImgButton: LinearLayout? = null
    private var haveChosenImage = false
    private var submitCommentButton: TextView? = null
    val db = Firebase.firestore
    private var commentEditText: TextView? = null
    val storageRef = Firebase.storage
    private lateinit var toShowDetailIntent: Intent
    private var currentRecipe: FoodRecipe? = null
    private var currentAuthor: User? = null
    private var loadingDialog: SweetAlertDialog? = null

    private val translateContent = TranslateUtil()
    private val internetBroadcast = InternetConnectionBroadcast()

    override fun onStart() {
        super.onStart()
        internetBroadcast.registerInternetConnBroadcast(this@WriteCommentActivity)
    }

    override fun onDestroy() {
        super.onDestroy()

        closeLoadingDialog()

        internetBroadcast.unregisterInternetConnBroadcast(this@WriteCommentActivity)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_write_comment)

        val numberID: Long = intent?.extras?.getLong("commentID") as Long
        val cancelButton: TextView = findViewById(R.id.cancelTextViewBtn)

        currentRecipe = intent?.extras?.getParcelable("foodRecipe") as FoodRecipe?
        currentAuthor = intent?.extras?.getParcelable("user") as User?

        // assign to intent
        toShowDetailIntent = Intent(this, ShowRecipeDetailsActivity::class.java)

        // find view
        imgView = findViewById(R.id.commentFoodImageView)
        removeImageConstraintLayout = findViewById(R.id.removeCommentImageConstraintLayout)
        removeImgButton = findViewById(R.id.removeCommentImageLinearLayout)
        submitCommentButton = findViewById(R.id.submitTextViewBtn)
        commentEditText = findViewById(R.id.commentEditText)

        loadingDialog = SweetAlertDialog(this@WriteCommentActivity, SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog!!.titleText = "Xin vui lòng chờ ít giây"

        // handle submit comment (update on database)
        submitCommentButton?.setOnClickListener {
            if (!HelperFunctionDB.isConnectedToInternet(this@WriteCommentActivity)) {
                showDisconnDialog(this@WriteCommentActivity)

                return@setOnClickListener
            }
            val description = commentEditText?.text.toString()

            GlobalScope.launch {
                withContext(Dispatchers.Main){
                    loadingDialog!!.show()
                }

                // check if content is null
                if (description == "") {
                    MainScope().launch {
                        Toast.makeText(
                            this@WriteCommentActivity,
                            "Nội dung không được để trống",
                            Toast.LENGTH_LONG
                        ).show()

                        closeLoadingDialog()
                    }
                } else {
                    val response = translateComment(description)

                    if (response.body != null) {
                        val responseString = response.body?.string()
                        val json = JSONArray(responseString!!)

                        // checkProfanity return false --> no profanity
                        if (!checkProfanity(getTranslatedText(json))){
                            Log.i("writeComment", "success")
                            updateCommentOnDB(numberID, description)
                        }
                        else{
                            Log.i("writeComment", "failed")

                            withContext(Dispatchers.Main){
                                closeLoadingDialog()

                                val profanityAlertDialog = SweetAlertDialog(this@WriteCommentActivity, SweetAlertDialog.WARNING_TYPE)

                                profanityAlertDialog.titleText = "Nội dung bình luận chứa từ ngữ không phù hợp, vui lòng kiểm tra lại"
                                profanityAlertDialog.setCancelable(false)
                                profanityAlertDialog.show()

                                profanityAlertDialog.setConfirmClickListener {
                                    profanityAlertDialog.dismiss()
                                }
                            }
                        }
                    }
                }
            }

        }

        // handle click on add image section, get image from local phone storage
        imgView?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

        // remove added image
        removeImgButton?.setOnClickListener {
            // set image to default find image icon
            imgView?.setImageResource(R.drawable.find_image_icon)

            // hide remove image section
            removeImageConstraintLayout?.visibility = View.INVISIBLE
            imgView?.scaleType = ImageView.ScaleType.FIT_CENTER

            imageUri = null
            haveChosenImage = false
        }

        // cancel writing comment -> return to recipe details
        cancelButton.setOnClickListener {
            toShowDetailIntent.putExtra("foodRecipe", currentRecipe)
            startActivity(toShowDetailIntent)

            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data?.data
            imgView?.setImageURI(imageUri)
            imgView?.scaleType = ImageView.ScaleType.CENTER_CROP

            haveChosenImage = true
            removeImageConstraintLayout?.visibility = View.VISIBLE
        }
    }

    // update comment on firestore
    private fun updateCommentOnDB(numberID: Long, description: String) {
        // find document ID by using numberID
        db.collection("RecipeCmts").whereEqualTo("id", numberID)
            .get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    // get doc ID
                    val documentID = document.elementAt(0).id

                    val imageURL = if (imageUri == null) null else "comments/${numberID}"

                    currentRecipe?.recipeCmts?.add(numberID)

                    // write comment desciption to DB
                    db.collection("RecipeCmts").document(documentID)
                        .update("description", description, "date", Date(), "image", imageURL)
                        .addOnSuccessListener {
                            if (imageURL != null)
                                uploadImage(numberID)
                            else {
                                loadingDialog!!.dismiss()
                                toShowDetailIntent.putExtra("foodRecipe", currentRecipe)
                                toShowDetailIntent.putExtra("user", currentAuthor)

                                startActivity(toShowDetailIntent)
                                finish()
                            }
                        }
                }
            }
    }

    private fun uploadImage(commentNumberID: Long) {
        if (imageUri == null)
            return

        val imageRef = storageRef.reference.child("comments/${commentNumberID}")
        val uploadTask = imageRef.putFile(imageUri.toString().toUri())

        uploadTask.addOnSuccessListener {
            loadingDialog!!.dismiss()
            toShowDetailIntent.putExtra("foodRecipe", currentRecipe)
            toShowDetailIntent.putExtra("user", currentAuthor)

            startActivity(toShowDetailIntent)
            val intent1 = Intent(ConstantAction.ADD_CMT_RECIPE_ACTION)
            sendBroadcast(intent1)
            finish()
        }
            .addOnFailureListener {}
    }

    private fun closeLoadingDialog(){
        if (loadingDialog != null && loadingDialog!!.isShowing)
            loadingDialog!!.dismiss()
    }
    private fun translateComment(commentText: String): okhttp3.Response {
        val request = NinjasApiService.createTranslateRequest(commentText)
        val client = OkHttpClient()

        return client.newCall(request).execute()
    }

    private fun showDisconnDialog(context: Context) {
        val mainScope = CoroutineScope(Job() + Dispatchers.Main)

        // disconnected alert dialog for user
        mainScope.launch {
            val disconnDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

            disconnDialog.titleText = "Vui lòng kiểm tra lại đường truyền mạng của bạn"
            disconnDialog.setCancelable(false) // hide cancel button

            disconnDialog.show()

            disconnDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).visibility =
                View.GONE // hide confirm button

            // dialog show for 1.2s then disappear
            delay(1200)

            disconnDialog.dismiss()
        }

    }

    private fun getTranslatedText(jArray: JSONArray): String {
        return jArray.getJSONObject(0)
            .getJSONArray("translations")
            .getJSONObject(0)
            .getString("text")
    }

    private suspend fun checkProfanity(content: String): Boolean {
        val checkProfanityScope = CoroutineScope(Job() + Dispatchers.IO)

        val job = checkProfanityScope.async {
            val response = NinjasApiService.createProfanityCheck().getProfanityResult(content)

            if (response.body() != null) {
                val result = response.body()

                Log.i("ResultProfanity", result.toString())
                result
            } else
                throw Exception("Failed to get Profanity API")
        }
        return job.await() as Boolean
    }
}