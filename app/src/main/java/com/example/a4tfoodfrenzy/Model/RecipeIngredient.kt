package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable

class RecipeIngredient(
    private var _ingreQuantity: Int,
    private var _ingreName: String,
    private var _ingreUnit: String,
    private var _ingreCalo: Int?
) : Parcelable{
    constructor() : this(0, "", "",0)


    var ingreQuantity: Int
        get() = _ingreQuantity
        set(value) {
            _ingreQuantity = value
        }


    var ingreName: String
        get() = _ingreName
        set(value) {
            _ingreName = value
        }


    var ingreUnit: String
        get() = _ingreUnit
        set(value) {
            _ingreUnit = value
        }

    var ingreCalo: Int?
        get() = _ingreCalo
        set(value) {
            _ingreCalo = value
        }

    // Hàm đọc dữ liệu từ Parcel và khởi tạo đối tượng RecipeIngredient
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    // Ghi dữ liệu của đối tượng RecipeIngredient vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_ingreQuantity)
        parcel.writeString(_ingreName)
        parcel.writeString(_ingreUnit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeIngredient> {
        override fun createFromParcel(parcel: Parcel): RecipeIngredient {
            return RecipeIngredient(parcel)
        }

        override fun newArray(size: Int): Array<RecipeIngredient?> {
            return arrayOfNulls(size)
        }
    }
}