package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R

class AddStepAdapter(private val context: Context, private val list:ArrayList<RecipeCookStep>)
    : RecyclerView.Adapter<AddStepAdapter.ViewHolder>() {

    var onButtonClick:((View,RecipeCookStep)->Unit)?=null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val image: ImageView =listItemView.findViewById(R.id.imageStep)
        val des:TextView=listItemView.findViewById(R.id.desStep)
        val step:TextView=listItemView.findViewById(R.id.stepNumberText)
        val menu:ImageView=listItemView.findViewById(R.id.optionMenuStep)
        init{
            menu.setOnClickListener {
                onButtonClick?.invoke(it,list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_step, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.step.text="Bước ${position+1}"
        val uri= Uri.parse(item.image)
        holder.image.setImageURI(uri)
        holder.des.text=item.description
        holder.menu.setOnClickListener {
            onButtonClick?.invoke(it,item)
        }
    }

}
