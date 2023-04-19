package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R

class ListIngredientAdapter(context: Context, list:ArrayList<RecipeIngredient>)
    : RecyclerView.Adapter<ListIngredientAdapter.ViewHolder>() {
    private var listItem=list
    private val context=context
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name: TextView =listItemView.findViewById(R.id.name)
        val amount: TextView =listItemView.findViewById(R.id.amount)
        val menu:ImageView=listItemView.findViewById(R.id.optionMenu)

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
        val contactView = inflater.inflate(R.layout.list_ingredient, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=listItem[position]
        holder.name.text=item.ingreName
        holder.amount.text="${item.ingreQuantity} g"
    }

}
