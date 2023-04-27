package com.example.a4tfoodfrenzy.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
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

    private val ADD_REQUEST_CODE=1
    private val EDIT_REQUEST_CODE=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe4)
        initView()
        setupRecyclerView()
        restoreData()

        recieveData()

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
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
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

    }


    private fun setupContinueButton() {
        continueBtn= findViewById<Button>(R.id.continueBtn)
        continueBtn.setOnClickListener {
            deleteAllSharePreference()
            HelperFunctionDB(this).showLoadingAlert()
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
    private fun addFoodRecipeToCollection(foodRecipe: FoodRecipe, onSuccess: (String) -> Unit, onFailure:(HelperFunctionDB)-> Unit) {
        val db = Firebase.firestore
        db.collection("foodRecipes")
            .add(foodRecipe)
            .addOnSuccessListener { documentReference ->
                onSuccess(documentReference.id) // trả về id của document vừa được tạo
            }
            .addOnFailureListener {
                onFailure(HelperFunctionDB(this))
            }
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
    private fun addFoodRecipeToCategory(foodRecipeId: Long, onSuccess: () -> Unit, onFailure: (HelperFunctionDB) ->Unit) {
        val db = Firebase.firestore
        db.collection("RecipeCates")
            .whereEqualTo("recipeCateName", cate)
            .limit(1) // giới hạn kết quả trả về là 1 document
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val document = documents.first() // lấy document đầu tiên
                    db.collection("RecipeCates")
                        .document(document.id)
                        .update("foodRecipes", FieldValue.arrayUnion(foodRecipeId))
                        .addOnSuccessListener {
                            onSuccess() // gọi callback onSuccess nếu thêm thành công
                        }
                        .addOnFailureListener {
                            onFailure(HelperFunctionDB(this))
                        }
                } else {
                    onFailure(HelperFunctionDB(this))
                }
            }
            .addOnFailureListener {
                onFailure(HelperFunctionDB(this))
            }
    }
    private fun addFoodRecipe(foodRecipe: FoodRecipe,userId: Long, onSuccess: (HelperFunctionDB) -> Unit, onFailure: (HelperFunctionDB) -> Unit) {
        addFoodRecipeToCollection(foodRecipe,
            { documentId ->
               addFoodRecipeToCategory(foodRecipe.id,{
                   addFoodRecipeToUser(foodRecipe.id,userId,{
                       onSuccess(HelperFunctionDB(this))
                   },
                       {
                           onFailure(HelperFunctionDB(this))
                       }
                   )

               },
                   {
                       onFailure(HelperFunctionDB(this))
                   }
               )
            },
            {
                onFailure(HelperFunctionDB(this))
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
        val mainImage= foodRecipe.recipeMainImage?.let { uploadImageToCloudStorage(it) }
        uploadImageStepToCloudStorage()

        HelperFunctionDB(this).findSlotIdEmptyInCollection("RecipeFoods"){idSlot ->
            if (fullName != null) {
                foodRecipe.id=idSlot
                foodRecipe.authorName=fullName
                foodRecipe.recipeMainImage=mainImage
                foodRecipe.isPublic=true
                foodRecipe.date=Date()
                foodRecipe.recipeSteps=listStep
               addFoodRecipe(foodRecipe,user.id, {
                   HelperFunctionDB(this).showSuccessAlert(
                       "Thành công",
                       "Bạn đã thêm món ăn thành công"
                   ) { confirm ->
                       if (confirm) {
                           val intent = Intent(this, MainActivity::class.java)
                           startActivity(intent)
                       }
                   }
               },{ HelperFunctionDB(this).showErrorAlert(
                   "Thất bại",
                   "Bạn đã thêm món ăn thất bại"
               ) { confirm ->
                   if (confirm) {
                       val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)
                   }}})
            }
        }

    }
    private fun uploadImageStepToCloudStorage()
    {
        //upload lên db
        for(i in 0 until listStep.size) {
            if(listStep[i].image.isNullOrEmpty())
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

}