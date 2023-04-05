package com.example.a4tfoodfrenzy

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView

class BinhLuan() : Parcelable {
    var name: String = ""
    var avt: String = ""
    var image: String = ""
    var content: String = ""
    var time: String = ""
    var like: String = ""

    constructor(name: String, avt: String, image: String, content: String, time: String, like: String) : this() {
        this.name = name
        this.avt = avt
        this.image = image
        this.content = content
        this.time = time
        this.like = like
    }

    constructor(parcel: android.os.Parcel) : this() {
        name = parcel.readString() ?: ""
        avt = parcel.readString() ?: ""
        image = parcel.readString() ?: ""
        content = parcel.readString() ?: ""
        time = parcel.readString() ?: ""
        like = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(avt)
        parcel.writeString(image)
        parcel.writeString(content)
        parcel.writeString(time)
        parcel.writeString(like)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BinhLuan> {
        override fun createFromParcel(parcel: android.os.Parcel): BinhLuan {
            return BinhLuan(parcel)
        }

        override fun newArray(size: Int): Array<BinhLuan?> {
            return arrayOfNulls(size)
        }
    }



}


