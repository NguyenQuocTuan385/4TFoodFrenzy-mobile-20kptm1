package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Adapter.*
import com.example.a4tfoodfrenzy.BroadcastReceiver.ConstantAction
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap


class ProfileActivity : AppCompatActivity() {
    private lateinit var name_profile: TextView
    private lateinit var avatar_profile: ImageView
    private lateinit var email_profile: TextView
    private lateinit var tabAdapter: TabProfileAdapter
    private lateinit var view_pager: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var option_adapter: ImageView
    private lateinit var list_option: List<String>
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var menu: Menu
    private lateinit var progressDialog: ProgressDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val storageRef = FirebaseStorage.getInstance()
    private lateinit var tabMyFoodRecipe: TabMyFoodRecipe
    private lateinit var tabFoodRecipeSaved: TabFoodRecipeSaved
    val dbManagement = DBManagement()
    var user_current = DBManagement.user_current
    private val myBroadcastReceiverProfile = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
             if (intent?.action.equals(ConstantAction.ADD_SAVED_RECIPE_ACTION)) {
                tabFoodRecipeSaved.generateRecipeSaved()
            }
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        viewProfile()
        tabProfile()
        optionProfile()
        bottomNavigation()
    }

    private fun viewProfile() {
        var user = auth.currentUser
        if(user == null)
        {
            val intent= Intent(this,LogoutActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            user_current = DBManagement.user_current
            name_profile = findViewById(R.id.name_profile)
            avatar_profile = findViewById(R.id.creatorImage)
            email_profile = findViewById(R.id.email_profile)

            name_profile.text = user_current?.fullname ?: ""
            email_profile.text = user_current?.email ?: ""

            val imageRef = user_current?.avatar?.let { storageRef.getReference(it) }
            if (imageRef != null) {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Glide.with(this)
                        .load(uri)
                        .into(avatar_profile)
                }.addOnFailureListener { exception ->
                    // Xử lý lỗi
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var intentFilter2 = IntentFilter(ConstantAction.DELETE_MY_RECIPE_ACTION)
        var intentFilter4 = IntentFilter(ConstantAction.ADD_SAVED_RECIPE_ACTION)
        registerReceiver(myBroadcastReceiverProfile, intentFilter2)
        registerReceiver(myBroadcastReceiverProfile, intentFilter4)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Hủy đăng ký listener
        unregisterReceiver(myBroadcastReceiverProfile)
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.profile).isChecked = true
    }
    private fun tabProfile() {
        tabAdapter = TabProfileAdapter(this, supportFragmentManager)
        tabFoodRecipeSaved = TabFoodRecipeSaved(this, HashMap())
        setPopupSavedRecipeFoodMenu(tabFoodRecipeSaved)
        tabAdapter.addFragment(tabFoodRecipeSaved, "Món đã lưu")
        tabMyFoodRecipe = TabMyFoodRecipe(this, HashMap())
        setPopupMyRecipeFoodMenu(tabMyFoodRecipe)
        tabAdapter.addFragment(tabMyFoodRecipe, "Món của tôi")
        view_pager = findViewById(R.id.view_pager)
        view_pager.adapter = tabAdapter
        tabs = findViewById(R.id.tab_layout)
        tabs.setupWithViewPager(view_pager)
    }
    private fun optionProfile() {
        option_adapter = findViewById(R.id.option_profile)
        list_option = listOf("Chỉnh sửa thông tin", "Đăng xuất")
        option_adapter.setOnClickListener {
            val popup = PopupMenu(this, option_adapter)
            for (i in list_option) {
                popup.menu.add(i)
            }
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Chỉnh sửa thông tin" -> {
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivityForResult(intent, 1)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                        true
                    }
                    "Đăng xuất" -> {
                        auth.signOut()
                        DBManagement.user_current = null

                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
    private fun bottomNavigation() {
        bottomNavigationView = findViewById(R.id.botNavbar)
        menu = bottomNavigationView.menu
        menu.findItem(R.id.profile).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                    true
                }
                R.id.search -> {
                    if (DBManagement.existAfterSearch == false) {
                        val intent = Intent(this, SearchScreen::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                    } else {
                        val intent = Intent(this, AfterSearchActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                    }
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right )
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
    private fun setPopupSavedRecipeFoodMenu(tabUserSavedFoodRecipe: TabFoodRecipeSaved) {
        val list_option = listOf("Xem chi tiết", "Xóa khỏi danh sách")
        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        val user_id = auth.currentUser?.uid

        tabUserSavedFoodRecipe.adapter?.onButtonClick = { view, foodRecipe ->
            val popup = PopupMenu(this, view)
            list_option.forEach {
                popup.menu.add(it)
            }
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Xem chi tiết" -> {
                        val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
                        intent.putExtra("foodRecipe", foodRecipe)
                        startActivity(intent)
                        true
                    }
                    "Xóa khỏi danh sách" -> {
                        var helperFunctionDB= HelperFunctionDB(this)
                        helperFunctionDB.showWarningAlert("Xóa món ăn",
                            "Bạn có chắc là sẽ xóa món ăn này?")
                        {confirm ->
                            if(confirm)
                            {
                                // xóa khỏi danh sách foodRecipeSaved của user
                                val mapUpdate = mapOf(
                                    "foodRecipeSaved" to FieldValue.arrayRemove(foodRecipe.id)
                                )
                                db.collection("users").document(user_id.toString()).update(mapUpdate)

                                // xóa khỏi danh sách userSavedRecipes của bảng món ăn
                                val mapUpdate2 = mapOf(
                                    "userSavedRecipes" to FieldValue.arrayRemove(user_id.toString())
                                )
                                db.collection("foodRecipes")
                                    .whereEqualTo("id", foodRecipe.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("foodRecipes").document(document.id).update(mapUpdate2)
                                        }
                                    }
                                    .addOnFailureListener{ exception ->
                                        Log.w("TAG", "Error getting documents: ", exception)
                                    }

                                tabUserSavedFoodRecipe.monAn.remove(foodRecipe)
                                tabUserSavedFoodRecipe.adapter?.notifyDataSetChanged()
                            }
                        }

                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
    private fun setPopupMyRecipeFoodMenu(tabUserFoodRecipe: TabMyFoodRecipe) {
        val list_option1 = listOf("Cập nhật", "Xóa", "Chia sẻ")
        val list_option2 = listOf("Cập nhật", "Xóa", "Hủy chia sẻ")
        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        val user_id = auth.currentUser?.uid

        tabUserFoodRecipe.adapter?.onButtonClick = { view, foodRecipe ->
            val popup = PopupMenu(this, view)
            if(foodRecipe.isPublic) {
                list_option2.forEach {
                    popup.menu.add(it)
                }
            } else {
                list_option1.forEach {
                    popup.menu.add(it)
                }
            }
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Cập nhật" -> {
                        val intent = Intent(this, AddRecipeActivity1::class.java)
                        intent.putExtra("foodRecipe", foodRecipe)
                        for(k in DBManagement.recipeCateList)
                        {
                            for(t in k.foodRecipes)
                            {
                                if(t == foodRecipe.id)
                                {
                                    intent.putExtra("cate",k.recipeCateName)
                                    break
                                }
                            }
                        }
                        startActivity(intent)
                        true
                    }
                    "Xóa" -> {
                        var helperFunctionDB = HelperFunctionDB(this)
                        helperFunctionDB.showWarningAlert("Xóa món ăn",
                            "Bạn có chắc là sẽ xóa món ăn này?")
                        {confirm ->
                            if(confirm)
                            {
                                // cập nhật lại danh sách món ăn của user
                                val mapUpdate = mapOf(
                                    "myFoodRecipes" to FieldValue.arrayRemove(foodRecipe.id)
                                )
                                db.collection("users").document(user_id.toString()).update(mapUpdate)

                                // xóa trong danh sách foodRecipeSaved của user khác
                                db.collection("users")
                                    .whereArrayContains("foodRecipeSaved", foodRecipe.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val mapUpdate = mapOf(
                                                "foodRecipeSaved" to FieldValue.arrayRemove(foodRecipe.id)
                                            )
                                            db.collection("users").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                // lấy danh sách comment của món ăn
                                val recipeCmts = foodRecipe.recipeCmts
                                // xóa comment trong danh sách comment của user khác
                                for (recipeCmt in recipeCmts) {
                                    db.collection("users")
                                        .whereArrayContains("recipeCmts", recipeCmt)
                                        .get()
                                        .addOnSuccessListener { documents ->
                                            for (document in documents) {
                                                val mapUpdate = mapOf(
                                                    "recipeCmts" to FieldValue.arrayRemove(recipeCmt)
                                                )
                                                db.collection("users").document(document.id).update(mapUpdate)
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                        }
                                }

                                // xóa comment trong bảng comment
                                for(recipeCmt in recipeCmts){
                                    db.collection("RecipeCmts")
                                        .whereEqualTo("id", recipeCmt)
                                        .get()
                                        .addOnSuccessListener { documents ->
                                            for (document in documents) {
                                                db.collection("RecipeCmts").document(document.id).delete()
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                        }
                                }

                                // xóa trong danh sách foodRecipes của bảng RecipeCates
                                db.collection("RecipeCates")
                                    .whereArrayContains("foodRecipes", foodRecipe.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val mapUpdate = mapOf(
                                                "foodRecipes" to FieldValue.arrayRemove(foodRecipe.id)
                                            )
                                            db.collection("RecipeCates").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                // xóa trong danh sách foodRecipes của bảng RecipeDiets
                                db.collection("RecipeDiets")
                                    .whereArrayContains("foodRecipes", foodRecipe.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val mapUpdate = mapOf(
                                                "foodRecipes" to FieldValue.arrayRemove(foodRecipe.id)
                                            )
                                            db.collection("RecipeDiets").document(document.id).update(mapUpdate)
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                // xóa Món ăn
                                db.collection("RecipeFoods")
                                    .whereEqualTo("id", foodRecipe.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("RecipeFoods").document(document.id).delete()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                                    }

                                tabUserFoodRecipe.monAn.remove(foodRecipe)
                                tabUserFoodRecipe.adapter?.notifyDataSetChanged()
                                val intent1 = Intent(ConstantAction.DELETE_MY_RECIPE_ACTION)
                                sendBroadcast(intent1)
                            }
                        }

                        true
                    }
                    "Chia sẻ" -> {
                        val mapUpdate = mapOf(
                            "public" to true
                        )
                        db.collection("RecipeFoods")
                            .whereEqualTo("id", foodRecipe.id)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    db.collection("RecipeFoods").document(document.id).update(mapUpdate)
                                }
                                val intent1 = Intent(ConstantAction.SHARE_RECIPE_ACTION)
                                sendBroadcast(intent1)
                            }
                            .addOnFailureListener{ exception ->
                                Log.w("TAG", "Error getting documents: ", exception)
                            }
                        foodRecipe.isPublic = true
                        tabUserFoodRecipe.adapter?.notifyDataSetChanged()
                        true
                    }
                    "Hủy chia sẻ" -> {
                        val mapUpdate = mapOf(
                            "public" to false
                        )
                        db.collection("RecipeFoods")
                            .whereEqualTo("id", foodRecipe.id)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    db.collection("RecipeFoods").document(document.id).update(mapUpdate)
                                }
                                val intent1 = Intent(ConstantAction.UNSHARED_RECIPE_ACTION)
                                sendBroadcast(intent1)
                            }
                            .addOnFailureListener{ exception ->
                                Log.w("TAG", "Error getting documents: ", exception)
                            }
                        foodRecipe.isPublic = false
                        tabUserFoodRecipe.adapter?.notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == 1) {
                val imageUri = data?.getStringExtra("urlAvt")
                if (imageUri != "") {
                    Glide.with(this)
                        .load(imageUri)
                        .into(avatar_profile)
                }
            }
        }
    }
}
