package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CreatedRecipe(context: Context, students: ArrayList<MonAn>) :  ArrayAdapter<MonAn>(context, 0, students)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.created_item,
            parent,
            false
        )
        val monAn = getItem(position)

        val image = view.findViewById<ImageView>(R.id.anh_mon_an)
        image.setImageResource(R.drawable.bo_nuong)

        val title = view.findViewById<TextView>(R.id.ten_mon_an)
        title.text = "Bò nướng mlem $position"

        return view
    }

}