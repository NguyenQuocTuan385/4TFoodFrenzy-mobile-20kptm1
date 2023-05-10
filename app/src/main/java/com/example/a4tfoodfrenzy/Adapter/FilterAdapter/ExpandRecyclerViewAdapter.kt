package com.example.a4tfoodfrenzy.Adapter.FilterAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.SortList
import com.example.a4tfoodfrenzy.R
import java.util.ArrayList

class ExpandRecyclerViewAdapter(
    private val sortList: ArrayList<SortList>,
    private var selectedCategoryId: ArrayList<Long>,
    private var selectedDietId: ArrayList<Long>,
    private val mainContext: Context
) : RecyclerView.Adapter<ExpandRecyclerViewAdapter.ViewHolder>() {
    var onNormalSortTypeClick: ((Long) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val linearLayout: LinearLayout = listItemView.findViewById(R.id.containerLinearLayout)
        val expandableRelativeLayout: RelativeLayout =
            listItemView.findViewById(R.id.expandableRelativeLayout)
        val title: TextView = listItemView.findViewById(R.id.titleTextView)
        val arrow: ImageView = listItemView.findViewById(R.id.expandArrowImageView)
        val childRecyclerView: RecyclerView = listItemView.findViewById(R.id.childRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.expand_sort_recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return sortList.size
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mainSortType = this.sortList[position]

        holder.title.text = mainSortType.getSortTitle()

        // set visibility when expand / collapse
        if (mainSortType.isExpanded()) {
            holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp)
            holder.expandableRelativeLayout.visibility = View.VISIBLE
        } else {
            holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp)
            holder.expandableRelativeLayout.visibility = View.GONE
        }

        holder.childRecyclerView.setHasFixedSize(true)
        if (mainSortType.getExpandViewType() == 0)
            holder.childRecyclerView.layoutManager = LinearLayoutManager(this.mainContext)
        else if (mainSortType.getExpandViewType() == 1 || mainSortType.getExpandViewType() == 2)
            holder.childRecyclerView.layoutManager = GridLayoutManager(this.mainContext, 3)

        val tempAdapter =
            NormSortAdapter(
                mainSortType.getTypeList(),
                selectedCategoryId,
                selectedDietId,
                mainSortType.getExpandViewType()
            )

        tempAdapter.onSortTypeClick = { id, newSelectedArrayOfOneTypeInSelectedIdList ->
            if (position == 0) {
                // unchoose normal sort option
                if (id != (-2).toLong()) {
                    onNormalSortTypeClick?.invoke(id)
                }
            }
            if (position == 1) {
                selectedCategoryId = newSelectedArrayOfOneTypeInSelectedIdList
            } else if (position == 2) {
                selectedDietId = newSelectedArrayOfOneTypeInSelectedIdList
            }

            notifyItemChanged(position)
        }

        holder.childRecyclerView.adapter = tempAdapter
        holder.childRecyclerView.itemAnimator = null

        holder.linearLayout.setOnClickListener {
            if (!mainSortType.isExpanded())
                holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp)
            else
                holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp)

            mainSortType.setExpanded(!mainSortType.expand)
            notifyItemChanged(holder.absoluteAdapterPosition)
        }
    }
}