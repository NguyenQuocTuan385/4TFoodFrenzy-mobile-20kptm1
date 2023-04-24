package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable

class RecipeIngredient(
    private var _ingreQuantity: Double,
    private var _ingreName: String,
    private var _ingreUnit: String,
    private var _ingreCalo: Double?
) : Parcelable{
    constructor() : this(0.0, "", "",0.0)


    var ingreQuantity: Double
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

    var ingreCalo: Double?
        get() = _ingreCalo
        set(value) {
            _ingreCalo = value
        }

    // Hàm đọc dữ liệu từ Parcel và khởi tạo đối tượng RecipeIngredient
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    )

    // Ghi dữ liệu của đối tượng RecipeIngredient vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(_ingreQuantity)
        parcel.writeString(_ingreName)
        parcel.writeString(_ingreUnit)
        parcel.writeDouble(ingreCalo!!)
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