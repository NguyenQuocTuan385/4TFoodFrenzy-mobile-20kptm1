package com.example.a4tfoodfrenzy.Adapter.AddRecipeAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeDiet
import com.example.a4tfoodfrenzy.R

class CheckboxAdapter(private var context: Context, private var list:ArrayList<RecipeDiet>?)
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
        val item = list?.get(position)
        if (item != null) {
            holder.checkbox.text=item.dietName
        }
        if (item != null) {
            holder.checkbox.isChecked = dietList.contains(item.id)
        }
        holder.checkbox.setOnClickListener {
            if(holder.checkbox.isChecked)
            {
                if (item != null) {
                    dietList.add(item.id)
                }
            }
            else
            {
                if (item != null) {
                    dietList.remove(item.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0

    }
    fun getDietList():ArrayList<Long>
    {
        return dietList
    }
    fun setDietList(dietList:ArrayList<Long>?)
    {
        if (dietList != null) {
            this.dietList=dietList
        }
        notifyDataSetChanged()
    }
}