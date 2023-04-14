package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

data class User(val name:String, val image:Int)
class UserAdapter(context: Context,list:ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listItem=list
    private val context=context
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name:TextView=listItemView.findViewById(R.id.userName)
        val image:ImageView=listItemView.findViewById(R.id.userAvt)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
       val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_user, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val item=listItem[position]
        holder.name.text=item.name
        holder.image.setImageResource(item.image)
    }

}
