package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.CookingStep
import com.example.a4tfoodfrenzy.R

class AddStepAdapter(context: Context, list:ArrayList<CookingStep>)
    : RecyclerView.Adapter<AddStepAdapter.ViewHolder>() {
    private var listItem=list
    private val context=context
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val image: ImageView =listItemView.findViewById(R.id.imageStep)
        val des:TextView=listItemView.findViewById(R.id.desStep)
        val step:TextView=listItemView.findViewById(R.id.stepNumberText)
        val menu:ImageView=listItemView.findViewById(R.id.optionMenuStep)
        init{
            menu.setOnClickListener {
                popMenus(it)
            }
        }

    }
    private fun popMenus(v:View)
    {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.menu_ingredient, popupMenu.menu)
        popupMenu.show()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_step, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=listItem[position]
        holder.step.text="Bước ${position+1}"
        holder.image.setImageResource(item.imageResource)
        holder.des.text=item.description
    }

}
