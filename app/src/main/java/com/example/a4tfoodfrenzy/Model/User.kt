package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class User(
    private var _id: Long,
    private var _email: String,
    private var _fullname: String,
    private var _birthday: Date?,
    private var _bio: String,
    private var _avatar: String,
    private var _recipeCmts: ArrayList<Long>,
    private var _myFoodRecipes: ArrayList<Long>,
    private var _foodRecipeSaved: ArrayList<Long>,
    private var _isAdmin:Boolean
) :Parcelable {
    constructor() : this(0, "", "", null, "", "", ArrayList(), ArrayList(), ArrayList(),false)

    var id: Long
        get() = _id
        set(value) {
            _id = value
        }

    var isAdmin: Boolean
        get() = _isAdmin
        set(value) {
            _isAdmin = value
        }

    var fullname: String
        get() = _fullname
        set(value) {
            _fullname = value
        }

    var birthday: Date?
        get() = _birthday
        set(value) {
            _birthday = value
        }

    var email: String
        get() = _email
        set(value) {
            _email = value
        }

    var bio: String
        get() = _bio
        set(value) {
            _bio = value
        }

    var avatar: String
        get() = _avatar
        set(value) {
            _avatar = value
        }

    var myFoodRecipes: ArrayList<Long>
        get() = _myFoodRecipes
        set(value) {
            _myFoodRecipes = value
        }

    var foodRecipeSaved: ArrayList<Long>
        get() = _foodRecipeSaved
        set(value) {
            _foodRecipeSaved = value
        }

    var recipeCmts: ArrayList<Long>
        get() = _recipeCmts
        set(value) {
            _recipeCmts = value
        }
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readSerializable() as? Date?,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readArrayList(Long::class.java.classLoader) as ArrayList<Long>,
        parcel.readArrayList(Long::class.java.classLoader) as ArrayList<Long>,
        parcel.readArrayList(Long::class.java.classLoader) as ArrayList<Long>,
        parcel.readByte() != 0.toByte()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_email)
        parcel.writeString(_fullname)
        parcel.writeSerializable(_birthday)
        parcel.writeString(_bio)
        parcel.writeString(_avatar)
        parcel.writeList(_recipeCmts)
        parcel.writeList(_myFoodRecipes)
        parcel.writeList(_foodRecipeSaved)
        parcel.writeByte(if (_isAdmin) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}