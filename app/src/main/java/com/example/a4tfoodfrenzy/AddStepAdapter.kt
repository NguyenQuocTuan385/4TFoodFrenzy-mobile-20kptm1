package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class AddStep(val des:String,val image:Int)
class AddStepAdapter(context: Context, list:ArrayList<AddStep>)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddStepAdapter.ViewHolder {
        val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_step, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: AddStepAdapter.ViewHolder, position: Int) {
        val item=listItem[position]
        holder.step.text="Bước ${position+1}"
        holder.image.setImageResource(item.image)
        holder.des.text=item.des
    }

}
