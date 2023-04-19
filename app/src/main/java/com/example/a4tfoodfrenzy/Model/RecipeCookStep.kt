package com.example.a4tfoodfrenzy.Model

class RecipeCookStep(
    private var _description: String
) {
    private var _imageResource: Int = -1
    private var _imageURL: String = ""

    var description: String
        get() = _description
        set(value) {
            _description = value
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

    constructor(_description: String, _imageResource:Int):this(_description)
    {
        this._imageResource = _imageResource
    }


}