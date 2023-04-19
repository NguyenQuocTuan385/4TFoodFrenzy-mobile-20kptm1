package com.example.a4tfoodfrenzy.model

class RecipeCategory (
    private var _id: Int,
    private var _recipeCateName: String,
    private var _foodRecipes: ArrayList<Int>
) {

    // Getter and Setter for _id
    var id: Int
        get() = _id
        set(value) { _id = value }

    var recipeCateName: String
        get() = _recipeCateName
        set(value) { _recipeCateName = value }

    var foodRecipes: ArrayList<Int>
        get() = _foodRecipes
        set(value) { _foodRecipes = value }
}