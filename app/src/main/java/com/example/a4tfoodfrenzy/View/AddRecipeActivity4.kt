package com.example.a4tfoodfrenzy.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.AddStepAdapter
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R
import com.facebook.internal.Mutable
import com.google.android.material.appbar.MaterialToolbar
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class AddRecipeActivity4 : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var addStepBtn:Button
    private lateinit var stepsAdapter:AddStepAdapter
    private lateinit var listStep:ArrayList<RecipeCookStep>
    private lateinit var listStepRecyclerview:RecyclerView
    private lateinit var cate:String
    private lateinit var foodRecipe: FoodRecipe
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var helperFunctionDB: HelperFunctionDB

    private val ADD_REQUEST_CODE=1
    private val EDIT_REQUEST_CODE=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe4)
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        initView()
        setupRecyclerView()
        recieveData()

        restoreData()

        setOnItemClick()
        setPopupMenu()

        setBackToolbar()
        setCloseToolbar()
        setupContinueButton()
        setupAddStepButton()
    }

    private fun setupRecyclerView() {
        listStep= arrayListOf<RecipeCookStep>()
        stepsAdapter = AddStepAdapter(this, listStep)
        listStepRecyclerview.adapter = stepsAdapter
        listStepRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private fun initView()
    {
        toolbarAddRecipe = findViewById<MaterialToolbar>(R.id.toolbarAddRecipe)
        listStepRecyclerview = findViewById<RecyclerView>(R.id.listStep)


    }
    private fun saveData()
    {
        val editor=sharedPreferences.edit()
        val gson=Gson()
        val json=gson.toJson(listStep)
        editor.putString("listStep", json)
        editor.apply()
    }
    private fun restoreData()
    {
        if(sharedPreferences.contains("listStep"))
        {
            val step= sharedPreferences.getString("listStep", null)
            val list: ArrayList<RecipeCookStep>? = Gson().fromJson(step, object : TypeToken<ArrayList<RecipeCookStep>>() {}.type)
            if(list!=null)
            {
                listStep.clear()
                listStep.addAll(list)
                stepsAdapter.notifyDataSetChanged()
            }

        }
    }
    private fun deleteAllSharePreference()
    {
        val editor=sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener {
            saveData()
            finish()
        }

    }
    private fun setCloseToolbar()
    {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
                    deleteAllSharePreference()
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
    private fun setPopupMenu()
    {
        stepsAdapter.onButtonClick={view,step->
            val popUpMenu = PopupMenu(this, view)
            popUpMenu.menuInflater.inflate(R.menu.menu_ingredient, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.update -> {
                        sendDataToUpdate(step)
                        true
                    }
                    R.id.delete -> {
                        var helperFunctionDB= HelperFunctionDB(this)
                        helperFunctionDB.showWarningAlert("Xóa bước",
                            "Bạn có chắc là sẽ xóa bước này?")
                        {confirm ->
                            if(confirm)
                            {
                                deleteStep(step)
                            }
                        }
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }

    }
    private fun sendDataToUpdate(step: RecipeCookStep)
    {
        val intent = Intent(this, AddStepActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("index", listStep.indexOf(step))
        intent.putExtra("stepNumber", (listStep.indexOf(step)+1).toString())
        intent.putExtra("step", step)
        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }
    private fun setOnItemClick()
    {
        stepsAdapter.onItemClick={step ->
            sendDataToUpdate(step)
        }
    }
    private fun recieveData()
    {
        //nhận đối tượng FoodRecipe từ màn hình 2
        foodRecipe=intent.getParcelableExtra<FoodRecipe>("foodRecipe") as FoodRecipe

        //nhận dữ liệu loại món ăn
        cate= intent.getStringExtra("cate").toString()
        if(!foodRecipe.recipeSteps.isEmpty())
        {
            listStep.clear()
            listStep.addAll(foodRecipe.recipeSteps)
            stepsAdapter.notifyDataSetChanged()
        }

    }


    private fun setupContinueButton() {
        continueBtn= findViewById<Button>(R.id.continueBtn)
        continueBtn.setOnClickListener {
            deleteAllSharePreference()
            helperFunctionDB= HelperFunctionDB(this)
            helperFunctionDB.showLoadingAlert()
            uploadToFirebase()
        }
    }

    private fun setupAddStepButton() {
        addStepBtn = findViewById<Button>(R.id.addStepBtn)
        addStepBtn.setOnClickListener {
            val intent = Intent(this, AddStepActivity::class.java)
            intent.putExtra("stepNumber",(listStep.size+1).toString())
            intent.putExtra("mode","add")
            startActivityForResult(intent,ADD_REQUEST_CODE)
        }
    }
    private fun checkUpdateFoodRecipe(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        if (foodRecipe.id != 0L) {
            val db = Firebase.firestore
            val collection = db.collection("RecipeFoods")
            val query = collection.whereEqualTo("id", foodRecipe.id)
            query.get().addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents[0]
                    val documentReference = documentSnapshot.reference
                    val documentId = documentReference.id
                    onSuccess(documentId)
                } else {
                    onSuccess("")
                }
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
        } else {
            onSuccess("")
        }
    }

    private fun addFoodRecipeToCollection(foodRecipe: FoodRecipe, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        checkUpdateFoodRecipe(
            onSuccess = { documentId ->
                val db = Firebase.firestore
                if (documentId.isNotEmpty()) {
                    val docRef = db.collection("RecipeFoods").document(documentId)
                    docRef.get().addOnSuccessListener { document ->
                        if (document.exists()) {
                            docRef.set(foodRecipe, SetOptions.merge())
                                .addOnSuccessListener {
                                    onSuccess("Thành công")
                                }
                                .addOnFailureListener { exception ->
                                    onFailure(exception)
                                }
                        } else {
                            docRef.set(foodRecipe)
                                .addOnSuccessListener {
                                    onSuccess("Thành công")
                                }
                                .addOnFailureListener { exception ->
                                    onFailure(exception)
                                }
                        }
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                } else {
                    db.collection("RecipeFoods")
                        .add(foodRecipe)
                        .addOnSuccessListener { documentReference ->
                            onSuccess("Thành công")
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                }
            },
            onFailure = { exception ->
                onFailure(exception)
            }
        )
    }


    private fun addFoodRecipeToUser(foodRecipeId: Long, userId: Long, onSuccess: () -> Unit, onFailure: (HelperFunctionDB)-> Unit) {
        val db = Firebase.firestore
        db.collection("users")
            .whereEqualTo("id",userId)
            .limit(1)
            .get()
            .addOnSuccessListener {documents ->
               if(documents.size()>0)
               {
                   val document=documents.first()
                   db.collection("users")
                       .document(document.id)
                       .update("myFoodRecipes",FieldValue.arrayUnion(foodRecipeId))
                       .addOnSuccessListener {
                           onSuccess()
                       }
                       .addOnFailureListener {
                           onFailure(HelperFunctionDB(this))
                       }
               }
            }
            .addOnFailureListener {
                onFailure(HelperFunctionDB(this))
            }
    }
    private fun addFoodRecipeToDiet(
        foodRecipeId: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit // Thêm tham số Exception để báo lỗi
    ) {
        val db = Firebase.firestore
        db.collection("RecipeDiets")
            .whereIn("id", foodRecipe.recipeDiets)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.update("foodRecipes", FieldValue.arrayUnion(foodRecipeId))
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception) // Truyền mã lỗi đến phương thức onFailure
                        }
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception) // Truyền mã lỗi đến phương thức onFailure
            }
        db.collection("RecipeDiets")
            .whereNotIn("id", foodRecipe.recipeDiets)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val foodRecipes = document.get("foodRecipes") as? MutableList<Long>
                    if (foodRecipes != null) {
                        if(foodRecipes.remove(foodRecipeId)) {
                            document.reference.update("foodRecipes", foodRecipes)
                        }
                    }
                }
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception) // Truyền mã lỗi đến phương thức onFailure
            }
    }

    private fun addFoodRecipeToCategory(foodRecipeId: Long, onSuccess: () -> Unit, onFailure: (HelperFunctionDB) ->Unit) {
        val db = Firebase.firestore
        db.collection("RecipeCates")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents)
                {
                    val foodRecipes = document.get("foodRecipes") as? MutableList<Long>
                    if (foodRecipes != null) {
                        if(!document.get("recipeCateName")!!.equals(cate))
                        {
                            if(foodRecipes.remove(foodRecipeId)) {
                                document.reference.update("foodRecipes", foodRecipes)
                            }
                        }
                        else
                        {
                            document.reference.update("foodRecipes",FieldValue.arrayUnion(foodRecipeId))
                        }
                    }

                }
                onSuccess()

            }
            .addOnFailureListener {
                onFailure(HelperFunctionDB(this))
            }
    }

    private fun addFoodRecipe(foodRecipe: FoodRecipe,userId: Long, onSuccess: (HelperFunctionDB) -> Unit, onFailure: () -> Unit) {
        addFoodRecipeToCollection(foodRecipe,
            { documentId ->
               addFoodRecipeToCategory(foodRecipe.id,{
                   addFoodRecipeToDiet(foodRecipe.id,{
                       addFoodRecipeToUser(foodRecipe.id,userId,{
                           onSuccess(HelperFunctionDB(this))
                       },
                           {
                               onFailure()
                           }
                       )
                   },{
                       onFailure()
                   })
               },{
                   onFailure()
               })
            },
            {
                onFailure()
            }
        )
    }


    private fun uploadToFirebase()
    {
        val user=DBManagement.user_current
        val fullName= user?.fullname
        if(listStep.isNullOrEmpty())
        {
            HelperFunctionDB(this).showRemindAlert("Bạn vui lòng thêm bước")
            return
        }
        if(!foodRecipe.recipeMainImage!!.startsWith("foods/")) {
           foodRecipe.recipeMainImage = foodRecipe.recipeMainImage?.let { uploadImageToCloudStorage(it) }
        }
        uploadImageStepToCloudStorage()
        if(foodRecipe.id==0L) {

            HelperFunctionDB(this).findSlotIdEmptyInCollection("RecipeFoods") { idSlot ->
                if (fullName != null) {
                    foodRecipe.id = idSlot
                    foodRecipe.authorName = fullName
                    foodRecipe.isPublic = true
                    foodRecipe.date = Date()
                    foodRecipe.recipeSteps = listStep
                    addFoodRecipe(foodRecipe, user.id, {
                        HelperFunctionDB(this).showSuccessAlert(
                            "Thành công",
                            "Bạn đã thêm món ăn thành công"
                        ) { confirm ->
                            if (confirm) {
                                val intent = Intent(this, ProfileActivity::class.java)
                                intent.putExtra("newRecipe","newRecipe")
                                startActivity(intent)
                            }
                        }
                    }, {
                        HelperFunctionDB(this).showErrorAlert(
                            "Thất bại",
                            "Bạn đã thêm món ăn thất bại"
                        ) { confirm ->
                            if (confirm) {
                                val intent = Intent(this, ProfileActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    })
                }
            }
        }
        else
        {
            if (user != null) {
                foodRecipe.recipeSteps=listStep
                addFoodRecipe(foodRecipe, user.id, {
                    HelperFunctionDB(this).showSuccessAlert(
                        "Thành công",
                        "Bạn đã cập nhật món ăn thành công"
                    ) { confirm ->
                        if (confirm) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.putExtra("newRecipe","newRecipe")
                            startActivity(intent)
                            finish()
                        }
                    }
                }, {
                    HelperFunctionDB(this).showErrorAlert(
                        "Thất bại",
                        "Bạn đã thêm món ăn thất bại"
                    ) { confirm ->
                        if (confirm) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                })
            }
        }

    }
    private fun uploadImageStepToCloudStorage()
    {
        //upload lên db
        for(i in 0 until listStep.size) {
            if(listStep[i].image.isNullOrEmpty()|| listStep[i].image!!.startsWith("foods/") )
                continue
            else
                listStep[i].image= listStep[i].image?.let { uploadImageToCloudStorage(it) }
        }
    }
    private fun uploadImageToCloudStorage(image:String):String
    {
        val uri= Uri.parse(image)
        val imagePath=uri.lastPathSegment.toString()
        HelperFunctionDB(this).uploadImage(uri,imagePath,"foods")
        return "foods/${imagePath}.png"
    }
    private fun handleAddStep(resultCode: Int,data: Intent?)
    {
        if(resultCode== RESULT_OK)
        {
            val recipeCookStep=data?.getParcelableExtra<RecipeCookStep>("step") as RecipeCookStep
            listStep.add(recipeCookStep)
            stepsAdapter.notifyDataSetChanged()
        }
    }
    private fun handleUpdateStep(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val step =
                data?.getParcelableExtra<RecipeCookStep>("step") as RecipeCookStep
            val index = data?.getIntExtra("index", -1)
            if (index != null) {
                listStep.set(index, step)
            }
            stepsAdapter.notifyDataSetChanged()
        }

    }
    private fun deleteStep(step:RecipeCookStep)
    {
        listStep.remove(step)
        stepsAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            ADD_REQUEST_CODE ->handleAddStep(resultCode,data)
            EDIT_REQUEST_CODE ->handleUpdateStep(resultCode,data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        helperFunctionDB.stopLoadingAlert()
    }

}