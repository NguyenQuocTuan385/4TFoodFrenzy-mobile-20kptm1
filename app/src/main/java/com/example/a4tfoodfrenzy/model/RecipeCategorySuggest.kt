package com.example.a4tfoodfrenzy.model

class RecipeCategorySuggest {
    private var _recipeCateTitle: String? = null
    private var _recipeCateImg: Int? = null

    constructor(_titleRecipeCate: String?, _recipeCateImg: Int?) {
        this._recipeCateTitle = _titleRecipeCate
        this._recipeCateImg = _recipeCateImg
    }

    var recipeCateTitle: String?
        get() = _recipeCateTitle
        set(value) {
            _recipeCateTitle = value
        }

    var recipeCateImg: Int?
        get() = _recipeCateImg
        set(value) {
            _recipeCateImg = value
        }
}