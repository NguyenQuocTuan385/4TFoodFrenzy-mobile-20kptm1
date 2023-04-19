package com.example.a4tfoodfrenzy.Model

class RecipeIngredient (
    private var _ingreQuantity: Int,
    private var _ingreName: String,
    private var _ingreUnit: String
) {
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