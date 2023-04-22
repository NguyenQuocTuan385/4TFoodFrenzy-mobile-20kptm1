package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable

class RecipeDiet (
    private var _id: Long,
    private var _dietName: String,
    private var _foodRecipes: ArrayList<Int>
) :Parcelable{

    // Getter and Setter for _id
    var id: Long
        get() = _id
        set(value) { _id = value }

    var dietName: String
        get() = _dietName
        set(value) { _dietName = value }

    var foodRecipes: ArrayList<Int>
        get() = _foodRecipes
        set(value) { _foodRecipes = value }

    // Hàm đọc dữ liệu từ Parcel và khởi tạo đối tượng RecipeDiet
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readArrayList(ClassLoader.getSystemClassLoader()) as ArrayList<Int>
    )

    // Ghi dữ liệu của đối tượng RecipeDiet vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeString(_dietName)
        parcel.writeList(_foodRecipes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeDiet> {
        override fun createFromParcel(parcel: Parcel): RecipeDiet {
            return RecipeDiet(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDiet?> {
            return arrayOfNulls(size)
        }
    }
}