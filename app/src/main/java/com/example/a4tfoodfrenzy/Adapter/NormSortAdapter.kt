package com.example.a4tfoodfrenzy.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.Controller.Activity.SortRecipeActivity
import com.example.a4tfoodfrenzy.Model.SortType

class NormSortAdapter(
    private val sortTypeList: List<SortType>,
    private val selectedCategoryId: ArrayList<Long>,
    private val selectedDietId: ArrayList<Long>,
    private val currentViewType: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onSortTypeClick: ((Long, ArrayList<Long>) -> Unit)? = null
    private var normalSelectedID: Long = -1

    inner class SquareSortViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val sortTypeName: TextView = listItemView.findViewById(R.id.itemSort)
        val sortIconImage: ImageView = listItemView.findViewById(R.id.sortItemIcon)
        val cardViewContainer: CardView = listItemView.findViewById(R.id.sortItemCardView)
    }

    inner class SingleChoiceSortViewHolder(listItemView: View) :
        RecyclerView.ViewHolder(listItemView) {
        val sortTypeName: TextView = listItemView.findViewById(R.id.itemSort)
        val sortIconImage: ImageView = listItemView.findViewById(R.id.sortItemIcon)
//        val containerLayout : ConstraintLayout = listItemView.findViewById(R.id.normalSortConstraintLayoutContainer)

        init {
            listItemView.setOnClickListener {
                // unchoose
                normalSelectedID =
                    if (SortRecipeActivity.selectedNormalID == bindingAdapterPosition.toLong())
                        (-1).toLong()
                    else
                        bindingAdapterPosition.toLong()

                onSortTypeClick?.invoke(
                    normalSelectedID,
                    selectedDietId
                )

                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        if (viewType == 1111)
            return SingleChoiceSortViewHolder(
                inflater.inflate(
                    R.layout.normal_sort_item,
                    parent,
                    false
                )
            )
        else if (viewType == 2222)
            return SquareSortViewHolder(inflater.inflate(R.layout.square_sort_item, parent, false))
        return SquareSortViewHolder(inflater.inflate(R.layout.square_sort_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1111) {
            // view
            val tempHolder: SingleChoiceSortViewHolder = holder as SingleChoiceSortViewHolder
            val sortType: SortType = sortTypeList[position]
            val sortTypeName = tempHolder.sortTypeName
            val sortIconImage = tempHolder.sortIconImage

            // bind data
            sortTypeName.text = sortType.type
            sortIconImage.setImageResource(sortType.normImage)

            // change state base on condition
            if (position.toLong() == SortRecipeActivity.selectedNormalID) {
                sortIconImage.setImageResource(sortType.selectedImage)
                sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
            } else {
                sortIconImage.setImageResource(sortType.normImage)
                sortTypeName.setTextColor(Color.BLACK)
            }
        } else if (holder.itemViewType == 2222 || holder.itemViewType == 3333) {
            val tempHolder: SquareSortViewHolder = holder as SquareSortViewHolder
            val sortType: SortType = sortTypeList[position]

            val sortTypeName = tempHolder.sortTypeName

            sortTypeName.text = sortType.type

            val sortIconImage = tempHolder.sortIconImage

            sortIconImage.setImageResource(sortType.normImage)

            holder.cardViewContainer.setOnClickListener {
                if (holder.itemViewType == 2222) {
                    // unchoose sort option
                    if (selectedCategoryId.contains(position.toLong())) {
                        selectedCategoryId.remove(position.toLong())
                    } else {// choose sort option, add to selectedID array
                        selectedCategoryId.add(position.toLong())
                    }

                    onSortTypeClick?.invoke(
                        -2,
                        selectedCategoryId
                    )

                    notifyItemChanged(position)
                } else if (holder.itemViewType == 3333) {
                    // unchoose sort option
                    if (selectedDietId.contains(position.toLong())) {
                        selectedDietId.remove(position.toLong())
                    } else {// choose sort option, add to selectedID array
                        selectedDietId.add(position.toLong())
                    }

                    onSortTypeClick?.invoke(
                        -2,
                        selectedDietId
                    )

                    notifyItemChanged(position)
                }

            }

            if (holder.itemViewType == 3333) {
                if (selectedDietId.contains(position.toLong())) {
                    // choose --> inside array
                    sortIconImage.setImageResource(sortType.selectedImage)
                    sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
                } else {// not choose --> black
                    sortIconImage.setImageResource(sortType.normImage)
                    sortTypeName.setTextColor(Color.BLACK)
                }
            }

            if (holder.itemViewType == 2222) {
                if (selectedCategoryId.contains(position.toLong())) {
                    // choose --> inside array
                    sortIconImage.setImageResource(sortType.selectedImage)
                    sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
                } else {
                    sortIconImage.setImageResource(sortType.normImage)
                    sortTypeName.setTextColor(Color.BLACK)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return sortTypeList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (currentViewType == 0)
            return 1111
        else if (currentViewType == 1)
            return 2222
        return 3333
    }
}