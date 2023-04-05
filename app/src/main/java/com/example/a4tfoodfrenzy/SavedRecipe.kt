package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView


class MonAn {
    var title: String = ""
    var image: String = ""
}

class MonAnDaLuuAdapter(context: Context, students: ArrayList<MonAn>) :  ArrayAdapter<MonAn>(context, 0, students)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.saved_item,
            parent,
            false
        )
        val monAn = getItem(position)
        val image = view.findViewById<ImageView>(R.id.anh_mon_an)
        image.setImageResource(R.drawable.bo_nuong)

        return view
    }

}