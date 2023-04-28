package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeDiet
import com.example.a4tfoodfrenzy.R

class CheckboxAdapter(private var context: Context, private var list:ArrayList<RecipeDiet>)
    :RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {
    private var dietList = ArrayList<Long>()
    // Phương thức khởi tạo
    init {
        dietList = ArrayList()
    }

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val checkbox:CheckBox=listItemView.findViewById(R.id.checkBox)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_checkbox, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.checkbox.text=item.dietName
        holder.checkbox.isChecked = dietList.contains(item.id)
        holder.checkbox.setOnClickListener {
            if(holder.checkbox.isChecked)
            {
                dietList.add(item.id)
            }
            else
            {
                dietList.remove(item.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size

    }
    fun getDietList():ArrayList<Long>
    {
        return dietList
    }
    fun setDietList(dietList:ArrayList<Long>)
    {
        this.dietList=dietList
        notifyDataSetChanged()
    }
}