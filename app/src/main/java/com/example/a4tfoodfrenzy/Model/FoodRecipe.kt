package com.example.a4tfoodfrenzy.Model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class FoodRecipe(
    private var _id: Long,
    private var _recipeName: String,
    private var _recipeMainImage: String?,
    private var _ration: Int,
    private var _cookTime: String,
    private var _date: Date,
    private var _isPublic: Boolean,
    private var _recipeDiets: ArrayList<Long>,
    private var _recipeSteps: ArrayList<RecipeCookStep>,
    private var _recipeIngres: ArrayList<RecipeIngredient>,
    private var _recipeCmts: ArrayList<Long>,
    private var _userSavedRecipes: ArrayList<Long>,
) : Parcelable{
    private var _authorName: String? = null
    private var _authorAvatar: Int? = null
    private var _numOfLikes: Int? = null

    constructor():this(0,"",null,0,"",Date(),false, arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf(), arrayListOf())
    // constructor if there is author, likes, date
    constructor(
        id: Long,
        recipeName: String,
        recipeImage: String?,
        ration: Int,
        cookTime: String,
        date: Date,
        isPublic: Boolean,
        _recipeDiets: ArrayList<Long>,
        _recipeSteps: ArrayList<RecipeCookStep>,
        _recipeIngres: ArrayList<RecipeIngredient>,
        _recipeCmts: ArrayList<Long>,
        _userSavedRecipes: ArrayList<Long>,
        name: String,
        avatar: Int,
        likes: Int,
    ) : this(
        id,
        recipeName,
        recipeImage,
        ration,
        cookTime,
        date,
        isPublic,
        _recipeDiets,
        _recipeSteps,
        _recipeIngres,
        _recipeCmts,
        _userSavedRecipes
    ) {
        _authorName = name
        _numOfLikes = likes
        _authorAvatar = avatar
    }

    var id: Long
        get() = _id
        set(value) {
            _id = value
        }

    var recipeName: String
        get() = _recipeName
        set(value) {
            _recipeName = value
        }

    var recipeMainImage: String?
        get() = _recipeMainImage
        set(value) {
            _recipeMainImage = value
        }

    var ration: Int
        get() = _ration
        set(value) {
            _ration = value
        }

    var cookTime: String
        get() = _cookTime
        set(value) {
            _cookTime = value
        }
    var date: Date
        get() = _date
        set(value) {
            _date = value
        }

    var isPublic: Boolean
        get() = _isPublic
        set(value) {
            _isPublic = value
        }

    var recipeSteps: ArrayList<RecipeCookStep>
        get() = _recipeSteps
        set(value) {
            _recipeSteps = value
        }

    var recipeIngres: ArrayList<RecipeIngredient>
        get() = _recipeIngres
        set(value) {
            _recipeIngres = value
        }

    var recipeCmts: ArrayList<Long>
        get() = _recipeCmts
        set(value) {
            _recipeCmts = value
        }

    var recipeDiets: ArrayList<Long>
        get() = _recipeDiets
        set(value) {
            _recipeDiets = value
        }

    var userSavedRecipes: ArrayList<Long>
        get() = _userSavedRecipes
        set(value) {
            _userSavedRecipes = value
        }

    var authorName: String?
        get() = _authorName
        set(value) {
            _authorName = value
        }

    var authorAvatar: Int?
        get() = _authorAvatar
        set(value) {
            _authorAvatar = value
        }

    var numOfLikes: Int?
        get() = _numOfLikes
        set(value) {
            _numOfLikes = value
        }

    // implement methods of Parcelable
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(_id)
        dest.writeString(_recipeName)
        dest.writeString(_recipeMainImage)
        dest.writeInt(_ration)
        dest.writeString(_cookTime)
        dest.writeLong(_date.time)
        dest.writeByte(if (_isPublic) 1 else 0)
        dest.writeList(_recipeDiets)
        dest.writeList(_recipeSteps)
        dest.writeList(_recipeIngres)
        dest.writeList(_recipeCmts)
        dest.writeList(_userSavedRecipes)
        dest.writeString(_authorName)
        dest.writeValue(_authorAvatar)
        dest.writeValue(_numOfLikes)
    }
    private constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readString() ?: "",
        Date(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
        ArrayList<Long>().apply { parcel.readList(this, Long::class.java.classLoader) },
        ArrayList<RecipeCookStep>().apply { parcel.readList(this, RecipeCookStep::class.java.classLoader) },
        ArrayList<RecipeIngredient>().apply { parcel.readList(this, RecipeIngredient::class.java.classLoader) },
        ArrayList<Long>().apply { parcel.readList(this, Long::class.java.classLoader) },
        ArrayList<Long>().apply { parcel.readList(this, Long::class.java.classLoader) },
    ) {
        _authorName = parcel.readString()
        _authorAvatar = parcel.readValue(Int::class.java.classLoader) as? Int
        _numOfLikes = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<FoodRecipe> {
            override fun createFromParcel(parcel: Parcel): FoodRecipe {
                return FoodRecipe(parcel)
            }

            override fun newArray(size: Int): Array<FoodRecipe?> {
                return arrayOfNulls(size)
            }
        }
    }
}