package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R

class UserAdapter(context: Context, list: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listItem = list
    private val context = context

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name: TextView = listItemView.findViewById(R.id.userName)
        val image: ImageView = listItemView.findViewById(R.id.userAvt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_user, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.name.text = item.fullname
        val resources = context.getResources()
        val resourceId = resources.getIdentifier(item.avatar, "drawable", context.packageName)
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        holder.image.setImageBitmap(bitmap)
    }

}
