package com.example.a4tfoodfrenzy.model

class Ingredient (
    private var _id: Int,
    private var _quantity: Int,
    private var _nameIngredient: String,
    private var _unitIngredient: String
) {
    private var _FoodRecipeId: String? = null

    // Getter and Setter for _id
    var id: Int
        get() = _id
        set(value) { _id = value }

    // Getter and Setter for _quantity
    var quantity: Int
        get() = _quantity
        set(value) { _quantity = value }

    // Getter and Setter for _quantity
    var nameIngredient: String
        get() = _nameIngredient
        set(value) { _nameIngredient = value }

    // Getter and Setter for _quantity
    var unitIngredient: String
        get() = _unitIngredient
        set(value) { _unitIngredient = value }

    // Getter and Setter for _FoodRecipeId
    var foodRecipeId: String?
        get() = _FoodRecipeId
        set(value) { _FoodRecipeId = value }
}