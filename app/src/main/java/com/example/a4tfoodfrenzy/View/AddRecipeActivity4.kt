package com.example.a4tfoodfrenzy.View

import android.content.Intent
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
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class AddRecipeActivity4 : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var addStepBtn:Button
    private lateinit var stepsAdapter:AddStepAdapter
    private lateinit var listStep:ArrayList<RecipeCookStep>

    private lateinit var name:String
    private lateinit var mainImage:String
    private lateinit var amountServing:String
    private lateinit var dietList:ArrayList<Long>
    private lateinit var cate:String
    private lateinit var time:String
    private lateinit var listIngredient: ArrayList<RecipeIngredient>

    private val ADD_REQUEST_CODE=1
    private val EDIT_REQUEST_CODE=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe4)
        setupRecyclerView()
        initToolbar()
        recieveData()
        setOnItemClick()
        setPopupMenu()
        setBackToolbar()
        setCloseToolbar()
        setupContinueButton()
        setupAddStepButton()
    }

    private fun setupRecyclerView() {
        val listStepRecyclerview = findViewById<RecyclerView>(R.id.listStep)
        listStep= arrayListOf<RecipeCookStep>()
        stepsAdapter = AddStepAdapter(this, listStep)
        listStepRecyclerview.adapter = stepsAdapter
        listStepRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private fun initToolbar()
    {
        toolbarAddRecipe = findViewById<MaterialToolbar>(R.id.toolbarAddRecipe)

    }

    private fun setBackToolbar() {
        toolbarAddRecipe.setNavigationOnClickListener { finish() }

    }
    private fun setCloseToolbar()
    {
        toolbarAddRecipe.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_close -> {
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
    private fun uploadToFirebase()
    {
        val user=DBManagement.user_current
        val category=DBManagement.recipeCateList

        val fullName= user?.fullname
        val avatar= user?.avatar
        val mainImage=uploadImageToCloudStorage(mainImage)
        uploadImageStepToCloudStorage()

        HelperFunctionDB(this).findSlotIdEmptyInCollection("RecipeFoods"){idSlot ->
            if (fullName != null) {
                val foodRecipe=FoodRecipe(idSlot,name,mainImage,amountServing.toInt(),time, Date(),true,dietList,listStep,listIngredient,
                    arrayListOf(), arrayListOf(),fullName,R.drawable.defaultavt,0)
                writeFoodRecipeToFirebase(foodRecipe)
                writeIdFoodToUser(user.id,idSlot)
               writeIdFoodToCategory(idSlot)
            }
        }

    }
    private fun uploadImageStepToCloudStorage()
    {
        if(listStep.isNullOrEmpty())
        {
            HelperFunctionDB(this).showRemindAlert("Bạn vui lòng thêm bước")
            return
        }
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
    private fun recieveData()
    {
        name= intent.getStringExtra("name").toString()
        mainImage= intent.getStringExtra("mainImage").toString()
        amountServing= intent.getStringExtra("amountServing").toString()
        val longArray= intent.getLongArrayExtra("diet")
        if (longArray != null) {
            dietList= ArrayList(longArray.toList())
        }
        time= intent.getStringExtra("time").toString()
        cate= intent.getStringExtra("cate").toString()
        listIngredient= intent.getParcelableArrayListExtra<RecipeIngredient>("listIngredient") as ArrayList<RecipeIngredient>

    }

    private fun deleteStep(step:RecipeCookStep)
    {
        listStep.remove(step)
        stepsAdapter.notifyDataSetChanged()
    }

    private fun setupContinueButton() {
        continueBtn= findViewById<Button>(R.id.continueBtn)
        continueBtn.setOnClickListener {
            val helperFunctionDB=HelperFunctionDB(this)
            helperFunctionDB.showLoadingAlert()
            uploadToFirebase()
            helperFunctionDB.stopLoadingAlert()
        }
    }
    private fun writeIdFoodToUser(userid: Long, id:Long)
    {
        val db=Firebase.firestore
        db.collection("users").get()
            .addOnSuccessListener {result ->
                for(document in result)
                {
                    if(document.data.get("id")==userid)
                    {
                        document.reference.update("myFoodRecipes",FieldValue.arrayUnion(id))
                    }
                }
            }
    }
    private fun writeIdFoodToCategory( id:Long)
    {
        val db=Firebase.firestore
        db.collection("RecipeCates").get()
            .addOnSuccessListener {result ->
                for(document in result)
                {
                    if(document.data.get("recipeCateName").toString().equals(cate))
                    {
                        document.reference.update("foodRecipes",FieldValue.arrayUnion(id))
                    }
                }
            }
    }

    private fun writeFoodRecipeToFirebase(foodRecipe: FoodRecipe) {
        val db = Firebase.firestore
        db.collection("RecipeFoods").document().set(foodRecipe)
            .addOnSuccessListener {
                HelperFunctionDB(this).showSuccessAlert("Thành công",
                "Bạn đã thêm món ăn thành công"){ confirm ->
                    if(confirm)
                    {
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { e ->
                HelperFunctionDB(this).showErrorAlert("Thất bại",
                    "Bạn đã thêm món ăn thất bại"){ confirm ->
                    if(confirm)
                    {
                        val intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            ADD_REQUEST_CODE ->handleAddStep(resultCode,data)
            EDIT_REQUEST_CODE ->handleUpdateStep(resultCode,data)
        }
    }

}