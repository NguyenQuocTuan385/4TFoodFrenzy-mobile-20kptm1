package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView



class MonAnDaTaoAdapter(context: Context, students: ArrayList<MonAn>) :  ArrayAdapter<MonAn>(context, 0, students)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_mon_an_da_tao,
            parent,
            false
        )
        val monAn = getItem(position)

        val image = view.findViewById<ImageView>(R.id.imageView6)
        image.setImageResource(R.drawable.bo_nuong)

        return view
    }

}