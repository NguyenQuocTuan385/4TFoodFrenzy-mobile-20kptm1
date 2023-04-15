package com.example.a4tfoodfrenzy.model

import java.util.Date

class FoodRecipe(private var _id : Int,
                 private var _recipeName : String,
                 private var _recipeMainImage: Int,
                 private var _ration : Int,
                 private var _cookTime : Int,
                 private var _categoryId : Int) {
    private var _authorName : String? = null
    private var _authorAvatar : Int? = null
    private var _numOfLikes : Int? = null
    private var _uploadDate : Date? = null

    // constructor if there is author, likes, date
    constructor(id : Int, recipeName : String, recipeImage : Int, ration : Int, cookTime : Int, categoryId : Int, name : String, avatar : Int, likes : Int, date : Date) : this(id, recipeName, recipeImage, ration, cookTime, categoryId){
        _authorName = name
        _numOfLikes = likes
        _uploadDate = date
        _authorAvatar = avatar
    }

    fun getId() : Int{return _id}
    fun getRecipeName() : String {return _recipeName}
    fun getRecipeIMG() : Int { return  _recipeMainImage}
    fun getRation() : Int { return _ration}
    fun getCookTime() : Int {return _cookTime}
    fun getCategoryId() : Int{return _categoryId}
    fun getAuthorName() : String{return _authorName!!}
    fun getAuthorAvatar() : Int{return _authorAvatar!!}
    fun getLikes() : Int {return _numOfLikes!!}
    fun getUploadDate() : Date{return _uploadDate!!}
}