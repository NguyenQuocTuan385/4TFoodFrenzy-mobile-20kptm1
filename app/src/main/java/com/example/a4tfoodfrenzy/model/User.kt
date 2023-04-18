package com.example.a4tfoodfrenzy.model

import java.util.Date

class User(
    private var _id: Int, private var _username: String, private var _password: String,
    private var _fullname: String, private var _birthday: Date,
    private var _email: String, private var _bio: String,
    private val _avatar: Int
) {
    var id: Int
        get() = _id
        set(value) {
            _id = value
        }

    var username: String
        get() = _username
        set(value) {
            _username = value
        }

    var password: String
        get() = _password
        set(value) {
            _password = value
        }

    var fullname: String
        get() = _fullname
        set(value) {
            _fullname = value
        }

    var birthday: Date
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

    val avatar: Int
        get() = _avatar

}