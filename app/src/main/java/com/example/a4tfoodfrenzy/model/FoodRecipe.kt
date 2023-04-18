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

    var id: Int
        get() = _id
        set(value) {
            _id = value
        }

    var recipeName: String
        get() = _recipeName
        set(value) {
            _recipeName = value
        }

    var recipeMainImage: Int
        get() = _recipeMainImage
        set(value) {
            _recipeMainImage = value
        }

    var ration: Int
        get() = _ration
        set(value) {
            _ration = value
        }

    var cookTime: Int
        get() = _cookTime
        set(value) {
            _cookTime = value
        }

    var categoryId: Int
        get() = _categoryId
        set(value) {
            _categoryId = value
        }

    var authorName: String?
        get() = _authorName
        set(value) {
            _authorName = value
        }

    var authorAvatar: Int?
        get() = _authorAvatar
        set(value) {
            _authorAvatar = value
        }

    var numOfLikes: Int?
        get() = _numOfLikes
        set(value) {
            _numOfLikes = value
        }

    var uploadDate: Date?
        get() = _uploadDate
        set(value) {
            _uploadDate = value
        }
}