package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import kotlinx.coroutines.NonDisposableHandle.parent


class DraftRecipeAdapter(context: Context,list:ArrayList<RecipeFood>)
    : RecyclerView.Adapter<DraftRecipeAdapter.ViewHolder>() {
    private var listItem=list
    private val context=context
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name:TextView=listItemView.findViewById(R.id.name)
        val image:ImageView=listItemView.findViewById(R.id.image1)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DraftRecipeAdapter.ViewHolder {
       val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.activity_list_recipe, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: DraftRecipeAdapter.ViewHolder, position: Int) {
        val item=listItem[position]
        holder.name.text=item.name
        holder.image.setImageResource(item.image)
    }

}
