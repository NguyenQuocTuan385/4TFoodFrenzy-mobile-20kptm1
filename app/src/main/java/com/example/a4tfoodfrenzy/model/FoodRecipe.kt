package com.example.a4tfoodfrenzy.model

import java.util.Date

class FoodRecipe(
    private var _id: Int,
    private var _recipeName: String,
    private var _recipeMainImage: Int,
    private var _ration: Int,
    private var _cookTime: String,
    private var _date: Date,
    private var _isPublic: Boolean,
    private var _recipeImgs: ArrayList<String>,
    private var _recipeDiets: ArrayList<Int>,
    private var _recipeSteps: ArrayList<Int>,
    private var _recipeIngres: ArrayList<Int>,
    private var _recipeCmts: ArrayList<Int>,
    private var _userSavedRecipes: ArrayList<Int>
) {
    private var _authorName: String? = null
    private var _authorAvatar: Int? = null
    private var _numOfLikes: Int? = null
    private var _uploadDate: Date? = null

    // constructor if there is author, likes, date
    constructor(
        id: Int,
        recipeName: String,
        recipeImage: Int,
        ration: Int,
        cookTime: String,
        date: Date,
        isPublic: Boolean,
        _recipeImgs: ArrayList<String>,
        _recipeDiets: ArrayList<Int>,
        _recipeSteps: ArrayList<Int>,
        _recipeIngres: ArrayList<Int>,
        _recipeCmts: ArrayList<Int>,
        _userSavedRecipes: ArrayList<Int>,
        name: String,
        avatar: Int,
        likes: Int,
    ) : this(
        id,
        recipeName,
        recipeImage,
        ration,
        cookTime,
        date,
        isPublic,
        _recipeImgs,
        _recipeDiets,
        _recipeSteps,
        _recipeIngres,
        _recipeCmts,
        _userSavedRecipes
    ) {
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

    var cookTime: String
        get() = _cookTime
        set(value) {
            _cookTime = value
        }
    var date: Date
        get() = _date
        set(value) {
            _date = value
        }

    var isPublic: Boolean
        get() = _isPublic
        set(value) {
            _isPublic = value
        }

    var recipeImgs: ArrayList<String>
        get() = _recipeImgs
        set(value) {
            _recipeImgs = value
        }

    var recipeSteps: ArrayList<Int>
        get() = _recipeSteps
        set(value) {
            _recipeSteps = value
        }

    var recipeIngres: ArrayList<Int>
        get() = _recipeIngres
        set(value) {
            _recipeIngres = value
        }

    var recipeCmts: ArrayList<Int>
        get() = _recipeCmts
        set(value) {
            _recipeCmts = value
        }

    var recipeDiets: ArrayList<Int>
        get() = _recipeDiets
        set(value) {
            _recipeDiets = value
        }

    var userSavedRecipes: ArrayList<Int>
        get() = _userSavedRecipes
        set(value) {
            _userSavedRecipes = value
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