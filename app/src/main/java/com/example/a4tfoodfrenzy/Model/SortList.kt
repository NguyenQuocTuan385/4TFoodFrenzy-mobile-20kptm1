package com.example.a4tfoodfrenzy.Model

import com.example.a4tfoodfrenzy.Model.SortType

class SortList(
    private val typeList: ArrayList<SortType>,
    private val viewType: Int,
    private val sortTitle: String
) {
    var expand = false

    fun setExpanded(expandValue: Boolean) {
        expand = expandValue
    }

    fun isExpanded(): Boolean {
        return expand
    }

    fun getSortTitle(): String {
        return sortTitle
    }

    fun getExpandViewType(): Int {
        return viewType
    }

    fun getTypeList(): ArrayList<SortType> {
        return typeList
    }
}