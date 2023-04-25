package com.example.a4tfoodfrenzy.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeIngredient
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.AddIngredient
import com.example.a4tfoodfrenzy.View.AddRecipeActivity3
import kotlin.math.roundToInt

class ListIngredientAdapter(private var context: Context, private var list:ArrayList<RecipeIngredient>)
    : RecyclerView.Adapter<ListIngredientAdapter.ViewHolder>() {
    var onButtonClick: ((View,RecipeIngredient) -> Unit)? = null
    var onItemClick: ((RecipeIngredient) -> Unit)? = null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val name: TextView =listItemView.findViewById(R.id.name)
        val amount: TextView =listItemView.findViewById(R.id.amount)
        val menu:ImageView=listItemView.findViewById(R.id.optionMenu)
        init{
            menu.setOnClickListener {
                onButtonClick?.invoke(it,list[adapterPosition])
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_ingredient, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    private fun formatNum(num:Double?): Any?
    {
        if (num != null) {
            if(num%1==0.0)
            {
                return num.toInt()
            }
        }
        return num
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.name.text=item.ingreName
        holder.amount.text="${formatNum(item.ingreQuantity)} ${item.ingreUnit}"
        holder.menu.setOnClickListener {
            onButtonClick?.invoke(it,item)
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }

    }



}
