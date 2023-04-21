package com.example.a4tfoodfrenzy.Model

class RecipeCookStep(
    private var _description: String,
    private var _image: String?
) {
    var description: String
        get() = _description
        set(value) {
            _description = value
        }

    var image: String?
        get() = _image
        set(value) {
            _image = value
        }
}