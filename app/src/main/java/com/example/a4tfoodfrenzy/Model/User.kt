package com.example.a4tfoodfrenzy.Model

import java.util.Date

class User(
    private var _id: Long,
    private var _email: String,
    private var _fullname: String,
    private var _birthday: Date?,
    private var _bio: String,
    private var _avatar: String,
    private var _recipeCmts: ArrayList<Int>,
    private var _myFoodRecipes: ArrayList<Int>,
    private var _foodRecipeSaved: ArrayList<Int>
) {
    constructor() : this(0, "", "", null, "", "", ArrayList(), ArrayList(), ArrayList())

    var id: Long
        get() = _id
        set(value) {
            _id = value
        }


    var fullname: String
        get() = _fullname
        set(value) {
            _fullname = value
        }

    var birthday: Date?
        get() = _birthday
        set(value) {
            _birthday = value
        }

    var email: String
        get() = _email
        set(value) {
            _email = value
        }

    var bio: String
        get() = _bio
        set(value) {
            _bio = value
        }

    var avatar: String
        get() = _avatar
        set(value) {
            _avatar = value
        }

    var myFoodRecipes: ArrayList<Int>
        get() = _myFoodRecipes
        set(value) {
            _myFoodRecipes = value
        }

    var foodRecipeSaved: ArrayList<Int>
        get() = _foodRecipeSaved
        set(value) {
            _foodRecipeSaved = value
        }

    var recipeCmts: ArrayList<Int>
        get() = _recipeCmts
        set(value) {
            _recipeCmts = value
        }
}