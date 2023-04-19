package com.example.a4tfoodfrenzy.Model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi

class RecipeComment(private var _isLike: Boolean,
                    private var _image: Int,
                    private var _description: String,
                    private var _date: String) : Parcelable
{
    private var _username: String = ""
    private var _nameRecipe: String = ""
    private var _avatarUser: Int = 0

    constructor(
        _username: String,
        _nameRecipe: String,
        _avatarUser: Int,
        _isLike: Boolean,
        _image: Int,
        _description: String,
        _date: String
    ) : this(_isLike, _image, _description, _date) {
    this._username = _username
    this._nameRecipe = _nameRecipe
    this._avatarUser = _avatarUser
    this._isLike = _isLike
    this._image = _image
    this._description = _description
    this._date = _date
}

    var username: String
    get() = _username
    set(value) {
        _username = value
    }

    var nameRecipe: String
    get() = _nameRecipe
    set(value) {
        _nameRecipe = value
    }

    var avatarUser: Int
    get() = _avatarUser
    set(value) {
        _avatarUser = value
    }

    var isLike: Boolean
    get() = _isLike
    set(value) {
        _isLike = value
    }

    var image: Int
    get() = _image
    set(value) {
        _image = value
    }

    var description: String
    get() = _description
    set(value) {
        _description = value
    }

    var date: String
    get() = _date
    set(value) {
        _date = value
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: android.os.Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
        _username = parcel.readString() ?: ""
        _nameRecipe = parcel.readString() ?: ""
        _avatarUser = parcel.readInt() ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(_username)
        parcel.writeString(_nameRecipe)
        parcel.writeInt(_avatarUser)
        parcel.writeInt(_image)
        parcel.writeBoolean(_isLike)
        parcel.writeString(_description)
        parcel.writeString(_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeComment> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: android.os.Parcel): RecipeComment {
            return RecipeComment(parcel)
        }

        override fun newArray(size: Int): Array<RecipeComment?> {
            return arrayOfNulls(size)
        }
    }

}


