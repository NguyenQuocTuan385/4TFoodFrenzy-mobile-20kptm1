package com.example.a4tfoodfrenzy.model

import java.util.*

class CookingStep(
    private var _id: Int, private var _numberStep: Int,
    private var _description: String
) {
    private var _imageResource: Int = -1
    private var _imageURL: String = ""
    private var _FoodRecipeId: Int? = null

    var id: Int
        get() = _id
        set(value) {
            _id = value
        }

    var numberStep: Int
        get() = _numberStep
        set(value) {
            _numberStep = value
        }

    var description: String
        get() = _description
        set(value) {
            _description = value
        }

    var foodRecipeId: Int?
        get() = _FoodRecipeId
        set(value) {
            _FoodRecipeId = value
        }

    var imageURL: String
        get() = _imageURL
        set(value) {
            _imageURL = value
        }

    var imageResource: Int
        get() = _imageResource
        set(value) {
            _imageResource = value
        }

    constructor(_id: Int, _numberStep: Int, _description: String, _imageResource:Int):this(_id, _numberStep, _description)
    {
        this._imageResource = _imageResource
    }


}