package com.example.a4tfoodfrenzy.Adapter.UserAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.storage.FirebaseStorage

class UserAdapter(private var context: Context, private var listItem: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var onButtonClick: ((View, User) -> Unit)? = null
    var onItemClick: ((User) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name: TextView = listItemView.findViewById(R.id.userName)
        val image: ImageView = listItemView.findViewById(R.id.userAvt)
        val menu: ImageView=listItemView.findViewById(R.id.optionMenu)
        val email:TextView=listItemView.findViewById(R.id.userEmail)

        init{
            menu.setOnClickListener {
                onButtonClick?.invoke(it,listItem[adapterPosition])
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(listItem[adapterPosition])
            }

        }

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
        holder.email.text=item.email
            holder.name.text = item.fullname
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val pathReference = storageRef.child(item.avatar)
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(context)
                    .load(uri.toString())
                    .into(holder.image)
            }
            holder.menu.setOnClickListener {
                onButtonClick?.invoke(it, item)
            }
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
    }
    fun filterList(filteredList: ArrayList<User>) {
        listItem = filteredList
        notifyDataSetChanged()
    }


}
