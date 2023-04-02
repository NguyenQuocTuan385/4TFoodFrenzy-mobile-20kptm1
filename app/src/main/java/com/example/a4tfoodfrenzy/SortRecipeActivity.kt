package com.example.a4tfoodfrenzy

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SortRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_recipe)

        val sortRecyclerView = findViewById<RecyclerView>(R.id.sortRecipeRecyclerView)

        val normalNameTypeList = arrayListOf(
            "Số Likes",
            "Lượng calories",
            "Số lượng bình luận",
            "Thời gian nấu",
            "Ngày đăng"
        )
        val categoryNameTypeList = arrayListOf(
            "Khai vị",
            "Món chính",
            "Tráng miệng",
            "Ăn vặt",
            "Điểm tâm",
            "Thức uống"
        )
        val dietTypeNameList = arrayListOf(
            "Không thịt",
            "Không gluten",
            "Không đường",
            "Món chay",
            "Món thuần chay",
            "Thức uống"
        )
        val ingredientNameList = arrayListOf(
            "Rau củ",
            "Thịt heo",
            "Thịt bò",
            "Thịt gà",
            "Tinh bột",
            "Hải sản",
            "Trái cây"
        )

        val normalImageList = arrayListOf(
            R.drawable.thumb_up_icon,
            R.drawable.fire_calory_icon,
            R.drawable.comment_sort_icon,
            R.drawable.cook_time_icon,
            R.drawable.calendar_date_sort_icon
        )
        val categoryImageList = arrayListOf(
            R.drawable.appetizer_icon,
            R.drawable.main_dish_icon,
            R.drawable.desert_icon,
            R.drawable.snack_icon,
            R.drawable.breakfast_icon,
            R.drawable.drink_icon
        )
        val dietImageList = arrayListOf(
            R.drawable.no_meat_icon,
            R.drawable.no_gluten_icon,
            R.drawable.no_sugar_icon,
            R.drawable.veg_icon,
            R.drawable.pure_veg_icon,
            R.drawable.no_alcohol_icon,
        )

        val normType = arrayListOf<SortType>()
        val categoryType = arrayListOf<SortType>()
        val dietType = arrayListOf<SortType>()
        val ingredientType = arrayListOf<SortType>()

        for (i in 0 until normalNameTypeList.size)
            normType.add(SortType(normalNameTypeList[i], normalImageList[i]))
        for (i in 0 until categoryNameTypeList.size)
            categoryType.add(SortType(categoryNameTypeList[i], categoryImageList[i]))
        for (i in 0 until dietTypeNameList.size)
            dietType.add(SortType(dietTypeNameList[i], dietImageList[i]))
        for (type in ingredientNameList)
            ingredientType.add(SortType(type))

        val normalSortList = SortList(normType, 0, "Lọc")
        val categorySortList = SortList(categoryType, 1, "Loại")
        val dietSortList = SortList(dietType, 1, "Chế độ ăn")
        val ingredientSortList = SortList(ingredientType, 2, "Nguyên liệu chính")

        val sortLists = arrayListOf<SortList>()

        sortLists.add(normalSortList)
        sortLists.add(categorySortList)
        sortLists.add(dietSortList)
        sortLists.add(ingredientSortList)

        sortRecyclerView.adapter = ExpandRecyclerViewAdapter(sortLists, this)
        sortRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}



class NormSortAdapter(private val sortTypeList: List<SortType>, private val currentViewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onSortTypeClick: ((String) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val sortTypeName: TextView = listItemView.findViewById(R.id.itemSort)
        val sortIconImage: ImageView = listItemView.findViewById(R.id.itemIcon)

        init {
            listItemView.setOnClickListener {
                onSortTypeClick?.invoke(sortTypeList[bindingAdapterPosition].type)
            }
        }
    }

    inner class ViewHolderCheckbox(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val sortTypeName: TextView = listItemView.findViewById(R.id.itemSort)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        if (viewType == 1111)
            return ViewHolder(inflater.inflate(R.layout.normal_sort_item, parent, false))
        else if (viewType == 2222)
            return ViewHolder(inflater.inflate(R.layout.square_sort_item, parent, false))
        else
            return ViewHolderCheckbox(
                inflater.inflate(
                    R.layout.ingredient_sort_item,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1111 || holder.itemViewType == 2222) {
            val tempHolder: ViewHolder = holder as ViewHolder
            val sortType: SortType = sortTypeList[position]

            val sortTypeName = tempHolder.sortTypeName
            sortTypeName.text = sortType.type

            val sortIconImage = tempHolder.sortIconImage
            sortIconImage.setBackgroundResource(sortType.img)
        } else {
            val tempHolder: ViewHolderCheckbox = holder as ViewHolderCheckbox
            val sortType: SortType = sortTypeList[position]

            val sortTypeName = tempHolder.sortTypeName
            sortTypeName.text = sortType.type
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

class SortType(val type: String) {
    var img = -1

    constructor(type: String, img: Int) : this(type) {
        this.img = img
    }
}

class SortList(private val typeList: ArrayList<SortType>, private val viewType: Int, private val sortTitle: String) {
    var expand = false

    fun setExpanded(expandValue: Boolean) {
        expand = expandValue
    }

    fun isExpanded(): Boolean {
        return expand
    }

    fun getSortTitle(): String {
        return sortTitle
    }

    fun getExpandViewType(): Int {
        return viewType
    }

    fun getTypeList(): ArrayList<SortType> {
        return typeList
    }
}

class ExpandRecyclerViewAdapter(private val sortList: ArrayList<SortList>, private val mainContext: Context) : RecyclerView.Adapter<ExpandRecyclerViewAdapter.ViewHolder>() {
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
            holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp)
            holder.expandableRelativeLayout.visibility = View.VISIBLE
        } else {
            holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp)
            holder.expandableRelativeLayout.visibility = View.GONE
        }

        holder.childRecyclerView.setHasFixedSize(true)
        if (mainSortType.getExpandViewType() == 0 || mainSortType.getExpandViewType() == 2)
            holder.childRecyclerView.layoutManager = LinearLayoutManager(this.mainContext)
        else if (mainSortType.getExpandViewType() == 1)
            holder.childRecyclerView.layoutManager = GridLayoutManager(this.mainContext, 3)

        val tempAdapter =
            NormSortAdapter(mainSortType.getTypeList(), mainSortType.getExpandViewType())
        tempAdapter.onSortTypeClick = { type ->
            Toast.makeText(mainContext, type, Toast.LENGTH_SHORT).show()
        }

        holder.childRecyclerView.adapter = tempAdapter

        holder.linearLayout.setOnClickListener {
            if (!mainSortType.isExpanded())
                holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp)
            else
                holder.arrow.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp)

            mainSortType.setExpanded(!mainSortType.expand)
            notifyItemChanged(holder.absoluteAdapterPosition)
        }
    }
}




