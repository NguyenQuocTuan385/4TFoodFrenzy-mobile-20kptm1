package com.example.a4tfoodfrenzy.Adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            left = spacing / 2
            right = spacing / 2
            top = spacing / 2
            bottom = spacing / 2
        }
    }
}