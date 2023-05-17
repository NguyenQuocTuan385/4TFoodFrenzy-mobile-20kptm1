package com.example.a4tfoodfrenzy.Controller.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.a4tfoodfrenzy.Adapter.AddRecipeAdapter.AddStepAdapter
import com.example.a4tfoodfrenzy.Api.Food
import com.example.a4tfoodfrenzy.Api.NinjasApiService
import com.example.a4tfoodfrenzy.Api.TranslateUtil
import com.example.a4tfoodfrenzy.BroadcastReceiver.ConstantAction
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.roundToInt

class AddRecipeActivity4 : AppCompatActivity() {
    private lateinit var continueBtn: Button
    private lateinit var toolbarAddRecipe: MaterialToolbar
    private lateinit var addStepBtn:Button
    private lateinit var stepsAdapter: AddStepAdapter
    private lateinit var listStep:ArrayList<RecipeCookStep>
    private lateinit var listStepRecyclerview:RecyclerView
    private lateinit var cate:String
    private lateinit var foodRecipe: FoodRecipe
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var helperFunctionDB: HelperFunctionDB
    private lateinit var mainImage:String
    private var check=-1


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
                    val intent = Intent(this,if(check==1) ProfileActivity::class.java else AddNewRecipe::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    finishAffinity()
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
                                Toast.makeText(this,"Bạn đã xóa thành công",Toast.LENGTH_SHORT).show()
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
        check=intent.getIntExtra("check",-1)

    }


    private fun setupContinueButton() {
        continueBtn= findViewById<Button>(R.id.continueBtn)
        continueBtn.setOnClickListener {
            if(listStep.isNullOrEmpty())
            {
                HelperFunctionDB(this).showRemindAlert("Bạn vui lòng thêm bước")
                return@setOnClickListener
            }
            helperFunctionDB= HelperFunctionDB(this)
            helperFunctionDB.showLoadingAlert()
            getCalo(getIngredientAsText())
            deleteAllSharePreference()
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
    private fun checkFoodRecipeExistence(
        foodRecipe: FoodRecipe,
        onSuccess: (String?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = Firebase.firestore
        val collection = db.collection("RecipeFoods")
        val query = collection.whereEqualTo("id", foodRecipe.id)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val documentId = documentSnapshot.id
                onSuccess(documentId)
            } else {
                onSuccess(null)
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    private fun addFoodRecipeToCollection(
        foodRecipe: FoodRecipe,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        checkFoodRecipeExistence(foodRecipe,
            onSuccess = { documentId ->
                val db = Firebase.firestore
                val docRef = if (documentId != null) {
                    db.collection("RecipeFoods").document(documentId)
                } else {
                    db.collection("RecipeFoods").document()
                }

                docRef.set(foodRecipe, SetOptions.merge())
                    .addOnSuccessListener {
                        Log.d("MOT", "checkid")
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            },
            onFailure = { exception ->
                onFailure(exception)
            }
        )
    }

    private fun addFoodRecipeToUser(
        foodRecipeId: Long,
        userId: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = Firebase.firestore
        db.collection("users")
            .whereEqualTo("id", userId)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val document = documents.first()
                    Log.d("MOT", foodRecipeId.toString())
                    document.reference.update("myFoodRecipes", FieldValue.arrayUnion(foodRecipeId))
                }
                Log.d("MOT", "Them Thanh cong nguoi dung")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    private fun addFoodRecipeToDiet(
        foodRecipeId: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = Firebase.firestore
        val recipeDiets = foodRecipe.recipeDiets ?: emptyList()

        db.collection("RecipeDiets")
            .whereIn("id", recipeDiets)
            .get()
            .addOnSuccessListener { documents ->
                val batch = db.batch()

                for (document in documents) {
                    val foodRecipes = document.get("foodRecipes") as? MutableList<Long>
                    if (foodRecipes != null) {
                        if (foodRecipes.remove(foodRecipeId)) {
                            batch.update(document.reference, "foodRecipes", foodRecipes)
                        }
                    }
                }

                batch.update(db.collection("RecipeDiets").document(), "foodRecipes", FieldValue.arrayUnion(foodRecipeId))

                db.collection("RecipeDiets")
                    .whereNotIn("id", recipeDiets)
                    .get()
                    .addOnSuccessListener { documents ->
                        val batch = db.batch()

                        for (document in documents) {
                            val foodRecipes = document.get("foodRecipes") as? MutableList<Long>
                            if (foodRecipes != null) {
                                if (foodRecipes.remove(foodRecipeId)) {
                                    batch.update(document.reference, "foodRecipes", foodRecipes)
                                }
                            }
                        }

                        batch.commit()
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { exception ->
                                onFailure(exception)
                            }
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    private fun addFoodRecipeToCategory(foodRecipeId: Long, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = Firebase.firestore
        db.collection("RecipeCates")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val foodRecipes = document.get("foodRecipes") as? MutableList<Long>
                    if (foodRecipes != null) {
                        if (!document.get("recipeCateName")!!.equals(cate)) {
                            if (foodRecipes.remove(foodRecipeId)) {
                                document.reference.update("foodRecipes", foodRecipes)
                            }
                        } else {
                            document.reference.update("foodRecipes", FieldValue.arrayUnion(foodRecipeId))
                        }
                    }

                }
                Log.d("MOT", "Them thanh cong loai mon an")
                onSuccess()

            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    private fun addFoodRecipe(
        foodRecipe: FoodRecipe,
        userId: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        addFoodRecipeToCollection(foodRecipe,
            onSuccess = {
                addFoodRecipeToCategory(foodRecipe.id,
                    onSuccess = {
                        addFoodRecipeToDiet(foodRecipe.id,
                            onSuccess = {
                                Log.d("MOT","THEM THANH CONG")
                                addFoodRecipeToUser(foodRecipe.id, userId, onSuccess, onFailure)
                                helperFunctionDB.stopLoadingAlert()
                            },
                            onFailure = onFailure
                        )
                    },
                    onFailure = onFailure
                )
            },
            onFailure = onFailure
        )
    }




    private fun uploadToFirebase()
    {
        val user=DBManagement.user_current
        val fullName= user?.fullname
        mainImage=foodRecipe.recipeMainImage.toString()
        if(!foodRecipe.recipeMainImage!!.startsWith("foods/")) {
            foodRecipe.recipeMainImage = foodRecipe.recipeMainImage?.let { uploadImageToCloudStorage(it) }
        }
        uploadImageStepToCloudStorage()
        if(foodRecipe.id==0L) {

            HelperFunctionDB(this).findSlotIdEmptyInCollection("RecipeFoods") { idSlot ->
                if (fullName != null) {
                    Log.d("MOT",idSlot.toString())
                    foodRecipe.id = idSlot
                    foodRecipe.isPublic = false
                    foodRecipe.date = Date()
                    foodRecipe.recipeSteps = listStep
                    addFoodRecipe(foodRecipe, user.id, {
                        showPopup()
                    }, {
                        helperFunctionDB.showErrorAlert(
                            "Thất bại",
                            "Bạn đã thêm món ăn thất bại"
                        ) { confirm ->
                            if (confirm) {
                                val intent = Intent(this, ProfileActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                finishAffinity()
                                intent.putExtra("selectedTab", 1) // chọn tab thứ hai
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
                    helperFunctionDB.showSuccessAlert(
                        "Thành công",
                        "Bạn đã cập nhật món ăn thành công"
                    ) { confirm ->
                        if (confirm) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            val intent1 = Intent(ConstantAction.UPDATE_MY_RECIPE_ACTION)
                            sendBroadcast(intent1)
                            finishAffinity()
                            intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                            startActivity(intent)
                        }
                    }
                }, {
                    helperFunctionDB.showErrorAlert(
                        "Thất bại",
                        "Bạn đã thêm món ăn thất bại"
                    ) { confirm ->
                        if (confirm) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            finishAffinity()
                            intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                            startActivity(intent)
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

    private fun getIngredientAsText():String
    {
        var l = ""
        for (k in foodRecipe.recipeIngres) {
            l += k.ingreName+","
        }
        return l
    }
    private fun getCalo(text:String)
    {
        val translateUtil= TranslateUtil()
        translateUtil.translate(text){translatedText ->
            getNutritionData(translatedText){food ->
                foodRecipe=food
                uploadToFirebase()
            }
        }
    }

    private fun getNutritionData(translatedText:String,callBack: (FoodRecipe)->Unit)
    {

        var temp=translatedText.trim().replace(" ", "").split(",")
        var hashMap= hashMapOf<String,String>()
        var string=""
        for(i in foodRecipe.recipeIngres.indices)
        {
            string+="${foodRecipe.recipeIngres[i].ingreQuantity.roundToInt()}${foodRecipe.recipeIngres[i].ingreUnit} ${temp[i]} "
            hashMap.put(temp[i].toLowerCase(Locale.ROOT),foodRecipe.recipeIngres[i].ingreName)
            Log.d("KK",temp[i].toLowerCase(Locale.ROOT))
        }
        val appKey = "gY+35sz1wCbF8TvCgO0oOA==UomLBEqmDOPJ2vlE"
        val call =
            NinjasApiService.create().getNutritionData(appKey, string)
        call.enqueue(object : Callback<List<Food>> {
            override fun onResponse(
                call: Call<List<Food>>,
                response: Response<List<Food>>
            ) {
                if (response.isSuccessful) {
                    val foods = response.body()
                    if (foods != null) {
                        if(foodRecipe.recipeIngres.size==foods.size)
                            callBack(foodRecipe)
                        for ((index,food) in foods.withIndex())
                        {
                            Log.d("HEHE",food.name+" "+food.calories)
                            if(food.name in hashMap)
                            {
                                val ingreName=hashMap[food.name]
                                for(k in foodRecipe.recipeIngres.indices)
                                {
                                    if(foodRecipe.recipeIngres[k].ingreName.equals(ingreName))
                                    {
                                        foodRecipe.recipeIngres[k].ingreCalo=food.calories
                                        break
                                    }
                                }

                            }
                        }
                    }
                    callBack(foodRecipe)
                } else {
                    println("Gọi api không thành công")
                    callBack(foodRecipe)
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                // API call failed, handle the error
                Log.e("TAG", "API call failed: ${t.message}")
            }


        })

    }
    private fun shareRecipe(onSuccess: () -> Unit,onFailure: (Exception) -> Unit) {
        val db = Firebase.firestore
        db.collection("RecipeFoods")
            .whereEqualTo("id", foodRecipe.id)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val document = documents.first()
                    document.reference.update("public", true)
                        .addOnSuccessListener {
                            // Update thành công, sau đó mới chuyển activity
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                }
            }
            .addOnFailureListener {exception ->
                onFailure(exception)
            }
    }

    private fun showPopup()
    {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.custompopup, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        val imagePopup=popupView.findViewById<ImageView>(R.id.imagePopup)
        val nameRecipePopup=popupView.findViewById<TextView>(R.id.nameRecipePopup)
        val shareRecipeBtn=popupView.findViewById<Button>(R.id.shareRecipeBtn)
        val laterBtn=popupView.findViewById<Button>(R.id.laterBtn)
        val anim=popupView.findViewById<LottieAnimationView>(R.id.animationView)
        popupWindow.showAtLocation(LinearLayout(this), Gravity.CENTER, 0, 0)
        nameRecipePopup.setText(foodRecipe.recipeName)
        val uri=Uri.parse(mainImage)
        imagePopup.setImageURI(uri)
        anim.playAnimation()
        shareRecipeBtn.setOnClickListener {
            shareRecipe (onSuccess = {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                val intent1 = Intent(ConstantAction.UPDATE_MY_RECIPE_ACTION)
                sendBroadcast(intent1)
                finishAffinity()
                popupWindow.dismiss()
                startActivity(intent)
            }, onFailure = {
                helperFunctionDB.showErrorAlert("Chia sẻ thất bại","Vui lòng chia sẻ lại"){
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("selectedTab", 1) // chọn tab thứ hai
                    val intent1 = Intent(ConstantAction.UPDATE_MY_RECIPE_ACTION)
                    sendBroadcast(intent1)
                    finishAffinity()
                    popupWindow.dismiss()
                    startActivity(intent)
                }
            })
        }
        laterBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("selectedTab", 1) // chọn tab thứ hai
            val intent1 = Intent(ConstantAction.UPDATE_MY_RECIPE_ACTION)
            sendBroadcast(intent1)
            finishAffinity()
            popupWindow.dismiss()
            startActivity(intent)
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
    override fun onDestroy() {
        super.onDestroy()
        if(HelperFunctionDB.sweetAlertDialog!=null&& HelperFunctionDB.sweetAlertDialog!!.isShowing)
        {
            HelperFunctionDB.sweetAlertDialog!!.dismiss()
        }
    }

}