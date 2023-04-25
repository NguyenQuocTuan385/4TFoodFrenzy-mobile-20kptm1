package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable

class RecipeCookStep(
    private var _description: String,
    private var _image: String?=null
) : Parcelable {
    constructor():this("",null)
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
    // Hàm đọc dữ liệu từ Parcel và khởi tạo đối tượng RecipeCookStep
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()
    )

    // Ghi dữ liệu của đối tượng RecipeCookStep vào Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_description)
        parcel.writeString(_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeCookStep> {
        override fun createFromParcel(parcel: Parcel): RecipeCookStep {
            return RecipeCookStep(parcel)
        }

        override fun newArray(size: Int): Array<RecipeCookStep?> {
            return arrayOfNulls(size)
        }
    }
}