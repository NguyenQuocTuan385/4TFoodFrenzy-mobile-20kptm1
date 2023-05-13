package com.example.a4tfoodfrenzy.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.util.Date

class RecipeComment(private var _id:Long, private var _isLike: Boolean,
                    private var _image: String?,
                    private var _description: String,
                    private var _date: Date) : Parcelable
{
    constructor():this(0,false,null,"",Date())


    var id: Long
        get() = _id
        set(value) {
            _id = value
        }

    var isLike: Boolean
    get() = _isLike
    set(value) {
        _isLike = value
    }

    var image: String?
    get() = _image
    set(value) {
        _image = value
    }

    var description: String
    get() = _description
    set(value) {
        _description = value
    }

    var date: Date
    get() = _date
    set(value) {
        _date = value
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString() ?: "",
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(_id)
        parcel.writeByte(if (_isLike) 1 else 0)
        parcel.writeString(_image)
        parcel.writeString(_description)
        parcel.writeLong(_date.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeComment> {
        override fun createFromParcel(parcel: Parcel): RecipeComment {
            return RecipeComment(parcel)
        }

        override fun newArray(size: Int): Array<RecipeComment?> {
            return arrayOfNulls(size)
        }
    }
}


