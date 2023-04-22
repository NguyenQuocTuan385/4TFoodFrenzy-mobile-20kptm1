package com.example.a4tfoodfrenzy.Model

import android.util.Log
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DBManagement {
    val db = Firebase.firestore
    var registrationUser: ListenerRegistration? = null
    var registrationFoodRecipe: ListenerRegistration? = null
    var registrationRecipeComment: ListenerRegistration? = null
    var registrationRecipeDiet: ListenerRegistration? = null
    var registrationRecipeCategory: ListenerRegistration? = null

    companion object {
        var userList = ArrayList<User>()
        var foodRecipeList = ArrayList<FoodRecipe>()
        var recipeCateList = ArrayList<RecipeCategory>()
        var recipeCommentList = ArrayList<RecipeComment>()
        var recipeDietList = ArrayList<RecipeDiet>()
        var isInitialized = false
    }
    fun addListenerChangeDataUser(callback: (ArrayList<User>) -> Unit) {
        val  userCollection = db.collection("users")
        registrationUser = userCollection
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w("TAG", "Listen failed.", error)
                    callback(arrayListOf())
                    return@addSnapshotListener
                }

                // Xử lý dữ liệu khi có thay đổi
                userList = ArrayList()
                for (doc in value!!.documents) {
                    val user = doc.toObject(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                callback(userList)
            }
    }
    fun addListenerChangeDataFoodRecipe(callback: (ArrayList<FoodRecipe>) -> Unit) {
        val  foodRecipeCollection = db.collection("RecipeFoods")
        registrationFoodRecipe = foodRecipeCollection
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w("TAG", "Listen failed.", error)
                    callback(arrayListOf())
                    return@addSnapshotListener
                }

                // Xử lý dữ liệu khi có thay đổi
                foodRecipeList = ArrayList()
                for (doc in value!!.documents) {
                    val foodRecipe = doc.toObject(FoodRecipe::class.java)
                    if (foodRecipe != null) {
                        foodRecipeList.add(foodRecipe)
                    }
                }
                callback(foodRecipeList)
            }
    }
    fun addListenerChangeDataRecipeComment(callback: (ArrayList<RecipeComment>) -> Unit) {
        val  recipeCmtCollection = db.collection("RecipeCmts")
        registrationRecipeComment = recipeCmtCollection
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w("TAG", "Listen failed.", error)
                    callback(arrayListOf())
                    return@addSnapshotListener
                }

                recipeCommentList = ArrayList()
                // Xử lý dữ liệu khi có thay đổi
                for (doc in value!!.documents) {
                    val recipeComment = doc.toObject(RecipeComment::class.java)
                    if (recipeComment != null) {
                        recipeCommentList.add(recipeComment)
                    }
                }
                callback(recipeCommentList)
            }
    }
    fun addListenerChangeDataRecipeDiets(callback: (ArrayList<RecipeDiet>) -> Unit) {
        val  recipeDietCollection = db.collection("RecipeDiets")
        registrationRecipeDiet = recipeDietCollection
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w("TAG", "Listen failed.", error)
                    callback(arrayListOf())
                    return@addSnapshotListener
                }

                recipeDietList = ArrayList()
                // Xử lý dữ liệu khi có thay đổi
                for (doc in value!!.documents) {
                    val recipeDiet = doc.toObject(RecipeDiet::class.java)
                    if (recipeDiet != null) {
                        recipeDietList.add(recipeDiet)
                    }
                }
                callback(recipeDietList)
            }
    }
    fun addListenerChangeDataRecipeCategories(callback: (ArrayList<RecipeCategory>) -> Unit) {
        val  recipeCateCollection = db.collection("RecipeCates")
        registrationRecipeCategory = recipeCateCollection
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w("TAG", "Listen failed.", error)
                    callback(arrayListOf())
                    return@addSnapshotListener
                }

                recipeCateList = ArrayList()
                // Xử lý dữ liệu khi có thay đổi
                for (doc in value!!.documents) {
                    val recipeCate = doc.toObject(RecipeCategory::class.java)
                    if (recipeCate != null) {
                        recipeCateList.add(recipeCate)
                    }
                }
                callback(recipeCateList)
            }
    }
//    fun fetchDataUser(callback: (ArrayList<User>) -> Unit) {
//        val usersCollection = db.collection("users")
//
//        usersCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val user = document.toObject(User::class.java)
//                    userList.add(user)
//                }
//                callback(userList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
//    fun fetchDataFoodRecipe(callback: (ArrayList<FoodRecipe>) -> Unit) {
//        val foodRecipesCollection = db.collection("RecipeFoods")
//
//        foodRecipesCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val foodRecipe = document.toObject(FoodRecipe::class.java)
//                    foodRecipeList.add(foodRecipe)
//                }
//                callback(foodRecipeList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
//    fun fetchDataRecipeCmt(callback: (ArrayList<RecipeComment>) -> Unit) {
//        val recipeCmtsCollection = db.collection("RecipeCmts")
//
//        recipeCmtsCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val recipeCmt = document.toObject(RecipeComment::class.java)
//                    recipeCommentList.add(recipeCmt)
//                }
//                callback(recipeCommentList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
//    fun fetchDataRecipeCate(callback: (ArrayList<RecipeCategory>) -> Unit) {
//        val recipeCatesCollection = db.collection("RecipeCates")
//
//        recipeCatesCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val recipeCate = document.toObject(RecipeCategory::class.java)
//                    recipeCateList.add(recipeCate)
//                }
//                callback(recipeCateList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
//    fun fetchDataRecipeDiet(callback: (ArrayList<RecipeDiet>) -> Unit) {
//        val recipeDietsCollection = db.collection("RecipeDiets")
//
//        recipeDietsCollection.get()
//            .addOnSuccessListener { querySnapshot ->
//                for (document in querySnapshot) {
//                    val recipeDiet = document.toObject(RecipeDiet::class.java)
//                    recipeDietList.add(recipeDiet)
//                }
//                callback(recipeDietList)
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//                callback(arrayListOf())
//            }
//    }
    fun destroyListener() {
        registrationUser?.remove()
        registrationFoodRecipe?.remove()
        registrationRecipeCategory?.remove()
        registrationRecipeDiet?.remove()
        registrationRecipeComment?.remove()
    }
}