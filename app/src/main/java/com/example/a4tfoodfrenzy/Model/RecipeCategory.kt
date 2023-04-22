package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable

class RecipeCategory (
    private var _id: Long,
    private var _recipeCateName: String,
    private var _foodRecipes: ArrayList<Int>
): Parcelable {

    // Getter and Setter for _id
    var id: Long
        get() = _id
        set(value) { _id = value }

    var recipeCateName: String
        get() = _recipeCateName
        set(value) { _recipeCateName = value }

    var foodRecipes: ArrayList<Int>
        get() = _foodRecipes
        set(value) { _foodRecipes = value }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_recipeCateName)
        parcel.writeList(_foodRecipes)
    }
    override fun describeContents(): Int {
        return 0
    }
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        ArrayList<Int>().apply {
            parcel.readList(this, Int::class.java.classLoader)
        }
    )
    companion object CREATOR : Parcelable.Creator<RecipeCategory> {
        override fun createFromParcel(parcel: Parcel): RecipeCategory {
            return RecipeCategory(parcel)
        }

        override fun newArray(size: Int): Array<RecipeCategory?> {
            return arrayOfNulls(size)
        }
    }

}