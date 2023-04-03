package com.example.a4tfoodfrenzy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class TypeRecipeHomepage (private var context: Context, private var items:
ArrayList<SearchRecipe>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var titleRecipeTV: TextView? = null
        var recipeIV: ImageView? = null

        init {
            titleRecipeTV = row?.findViewById<TextView>(R.id.titleRecipe)
            recipeIV = row?.findViewById<ImageView>(R.id.recipeIV)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            view = (inflater as LayoutInflater).inflate(R.layout.homepage_type_recipe_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var searchRecipe = items[position]
        viewHolder.titleRecipeTV?.text = searchRecipe.titleRecipe
        viewHolder.recipeIV?.setImageResource(searchRecipe.recipeImage!!)
        return view as View
    }

    override fun getItem(i: Int): SearchRecipe {
        return items[i]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}