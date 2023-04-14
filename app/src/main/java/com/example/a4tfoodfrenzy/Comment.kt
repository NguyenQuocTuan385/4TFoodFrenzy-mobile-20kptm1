package com.example.a4tfoodfrenzy

import android.os.Parcelable

class Comment() : Parcelable {
    var username: String = ""
    var foodname: String = ""
    var avt: Int = 0
    var image: Int = 0
    var content: String = ""
    var time: String = ""

    constructor(username: String, foodname: String,avt: Int, image: Int, content: String, time: String) : this() {
        this.username = username
        this.foodname = foodname
        this.avt = avt
        this.image = image
        this.content = content
        this.time = time
    }

    constructor(parcel: android.os.Parcel) : this() {
        username = parcel.readString() ?: ""
        foodname = parcel.readString() ?: ""
        avt = parcel.readInt() ?: 0
        image = parcel.readInt() ?: 0
        content = parcel.readString() ?: ""
        time = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(foodname)
        parcel.writeInt(avt)
        parcel.writeInt(image)
        parcel.writeString(content)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: android.os.Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }

}


