package com.example.a4tfoodfrenzy.model

class RecipeIngredient (
    private var _id: Int,
    private var _ingreQuantity: Int,
    private var _ingreName: String,
    private var _ingreUnit: String
) {
    private var _FoodRecipeId: String? = null

    // Getter and Setter for _id
    var id: Int
        get() = _id
        set(value) { _id = value }

    // Getter and Setter for _ingreQuantity
    var ingreQuantity: Int
        get() = _ingreQuantity
        set(value) { _ingreQuantity = value }

    // Getter and Setter for _ingreQuantity
    var ingreName: String
        get() = _ingreName
        set(value) { _ingreName = value }

    // Getter and Setter for _ingreQuantity
    var ingreUnit: String
        get() = _ingreUnit
        set(value) { _ingreUnit = value }
}