package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.RecipeDiet

class CheckboxAdapter(private var context: Context, private var list:ArrayList<RecipeDiet>)
    :RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
       val checkbox:CheckBox=listItemView.findViewById(R.id.checkBox)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxAdapter.ViewHolder {
        context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_checkbox, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: CheckboxAdapter.ViewHolder, position: Int) {
        val item = list[position]
        holder.checkbox.text=item.dietName
    }

    override fun getItemCount(): Int {
        return list.size

    }
}