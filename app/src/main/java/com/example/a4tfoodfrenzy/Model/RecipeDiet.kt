package com.example.a4tfoodfrenzy.Model

class RecipeDiet (
    private var _id: Long,
    private var _dietName: String,
    private var _foodRecipes: ArrayList<Int>
) {

    // Getter and Setter for _id
    var id: Long
        get() = _id
        set(value) { _id = value }

    var dietName: String
        get() = _dietName
        set(value) { _dietName = value }

    var foodRecipes: ArrayList<Int>
        get() = _foodRecipes
        set(value) { _foodRecipes = value }
}