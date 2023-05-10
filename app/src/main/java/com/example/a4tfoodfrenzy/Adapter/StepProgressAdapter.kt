package com.example.a4tfoodfrenzy.Adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.R

class StepProgressAdapter(
    private val progressList: ArrayList<String>,
    private val currentPos: Int, // this is current position of carousel
    private val parentRvWidth: Int,
    private val mainRV: RecyclerView
) : RecyclerView.Adapter<StepProgressAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val progressTV: TextView = listItemView.findViewById(R.id.progressTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.step_progress_item, parent, false))
    }

    override fun getItemCount(): Int {
        return progressList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentPos == position) {
            holder.progressTV.setBackgroundResource(R.color.SecondaryColor)
            holder.progressTV.typeface = Typeface.DEFAULT_BOLD
        }

        holder.progressTV.text = progressList[position]
        holder.progressTV.width = parentRvWidth / progressList.size

        holder.itemView.setOnClickListener {
            mainRV.scrollToPosition(position)
        }
    }
}