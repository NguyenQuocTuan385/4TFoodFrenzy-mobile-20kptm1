package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CommentListAdapter(context: Context, dsBinhLuan: ArrayList<Comment>):
    ArrayAdapter<Comment>(context, 0, dsBinhLuan)  {
override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.comment,
            parent,
            false
        )
        val binhLuan = getItem(position)

        val avt = view.findViewById<ImageView>(R.id.imageView)
        val pathAvt = binhLuan?.avt ?: ""
        if (pathAvt != "") {
            val id = context.resources.getIdentifier(pathAvt, "drawable", context.packageName)
            avt.setImageResource(id)
        }

        val name = view.findViewById<TextView>(R.id.textView12)
        name.text = binhLuan?.name ?: ""

        val img = view.findViewById<ImageView>(R.id.anh_mon_an)
        val pathImg = binhLuan?.image ?: ""
        if (pathImg != "") {
            val id = context.resources.getIdentifier(pathImg, "drawable", context.packageName)
            img.setImageResource(id)
        }
        else{
            img.visibility = View.GONE
        }

        val content = view.findViewById<TextView>(R.id.ten_mon_an)
        content.text = binhLuan?.content ?: " "

        val time = view.findViewById<TextView>(R.id.textView14)
        time.text = binhLuan?.time ?: "1 giờ trước"

        val like = view.findViewById<TextView>(R.id.textView15)
        like.text = binhLuan?.like ?: "0"

        return view
    }

}



