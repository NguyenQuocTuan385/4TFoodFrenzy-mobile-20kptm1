package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.R

class SortRecipeActivity : AppCompatActivity() {
    private val selectedCategoryId = arrayListOf<Long>()
    private val selectedDietId = arrayListOf<Long>()

    companion object{
        var selectedNormalID: Long = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_recipe)

        val sortRecyclerView = findViewById<RecyclerView>(R.id.sortRecipeRecyclerView)
        val applySortButton = findViewById<TextView>(R.id.applySortTextView)
        val normalNameTypeList = arrayListOf(
            "Số Likes",
            "Lượng calories",
            "Số lượng bình luận",
            "Thời gian nấu",
            "Ngày đăng"
        )

        // back button
        findViewById<ImageView>(R.id.toolbarBackButton).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        // apply sort
        findViewById<TextView>(R.id.applySortTextView).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val normalImageList = arrayListOf(
            R.drawable.thumb_up_icon,
            R.drawable.fire_calory_icon,
            R.drawable.comment_sort_icon,
            R.drawable.cook_time_icon,
            R.drawable.calendar_date_sort_icon
        )

        val selectedNormalImageList = arrayListOf(
            R.drawable.selected_norm_0,
            R.drawable.selected_norm_1,
            R.drawable.selected_norm_2,
            R.drawable.selected_norm_3,
            R.drawable.selected_norm_4,
        )

        val categoryImageList = arrayListOf(
            R.drawable.norm_category_0,
            R.drawable.norm_category_1,
            R.drawable.norm_category_2,
            R.drawable.norm_category_3,
            R.drawable.norm_category_4,
            R.drawable.norm_category_5
        )

        val selectedCategoryImageList = arrayListOf(
            R.drawable.selected_category_0,
            R.drawable.selected_category_1,
            R.drawable.selected_category_2,
            R.drawable.selected_category_3,
            R.drawable.selected_category_4,
            R.drawable.selected_category_5
        )

        val dietImageList = arrayListOf(
            R.drawable.no_gluten_icon,
            R.drawable.pure_veg_icon,
            R.drawable.no_sugar_icon,
            R.drawable.no_alcohol_icon,
            R.drawable.no_meat_icon,
            R.drawable.veg_icon,
        )

        val selectedDietImageList = arrayListOf(
            R.drawable.selected_diet_0,
            R.drawable.selected_diet_1,
            R.drawable.selected_diet_2,
            R.drawable.selected_diet_3,
            R.drawable.selected_diet_4,
            R.drawable.selected_diet_5
        )

        val normType = arrayListOf<SortType>()
        val categoryType = arrayListOf<SortType>()
        val dietType = arrayListOf<SortType>()

        for (i in 0 until normalNameTypeList.size)
            normType.add(SortType(normalNameTypeList[i], 0, normalImageList[i], selectedNormalImageList[i]))
        for ((i, category) in DBManagement.recipeCateList.withIndex())
            categoryType.add(
                SortType(
                    category.recipeCateName,
                    category.id,
                    categoryImageList[i],
                    selectedCategoryImageList[i]
                )
            )
        for ((i, diet) in DBManagement.recipeDietList.withIndex())
            dietType.add(SortType(diet.dietName, diet.id, dietImageList[i], selectedDietImageList[i]))

        val normalSortList = SortList(normType, 0, "Lọc")
        val categorySortList = SortList(categoryType, 1, "Loại")
        val dietSortList = SortList(dietType, 2, "Chế độ ăn")

        val sortLists = arrayListOf<SortList>()

        // show dropdown recycler ui
        sortLists.add(normalSortList)
        sortLists.add(categorySortList)
        sortLists.add(dietSortList)

        val adapter = ExpandRecyclerViewAdapter(sortLists, selectedCategoryId, selectedDietId, this)
        sortRecyclerView.adapter = adapter
        sortRecyclerView.layoutManager = LinearLayoutManager(this)

        applySortButton.setOnClickListener {
            Toast.makeText(
                this,
                "$selectedNormalID $selectedCategoryId \n${selectedDietId}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // change normal sort type
        adapter.onNormalSortTypeClick = { normalTypeID ->
            selectedNormalID = normalTypeID
        }
    }
}

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

    inner class SingleChoiceSortViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val sortTypeName: TextView = listItemView.findViewById(R.id.itemSort)
        val sortIconImage: ImageView = listItemView.findViewById(R.id.sortItemIcon)
//        val containerLayout : ConstraintLayout = listItemView.findViewById(R.id.normalSortConstraintLayoutContainer)

        init {
            listItemView.setOnClickListener{
                // unchoose
                normalSelectedID = if(SortRecipeActivity.selectedNormalID == bindingAdapterPosition.toLong())
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
            return SingleChoiceSortViewHolder(inflater.inflate(R.layout.normal_sort_item, parent, false))
        else if (viewType == 2222)
            return SquareSortViewHolder(inflater.inflate(R.layout.square_sort_item, parent, false))
        return SquareSortViewHolder(inflater.inflate(R.layout.square_sort_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == 1111){
            // view
            val tempHolder : SingleChoiceSortViewHolder = holder as SingleChoiceSortViewHolder
            val sortType: SortType = sortTypeList[position]
            val sortTypeName = tempHolder.sortTypeName
            val sortIconImage = tempHolder.sortIconImage

            // bind data
            sortTypeName.text = sortType.type
            sortIconImage.setImageResource(sortType.normImage)

            // change state base on condition
            if(position.toLong() == SortRecipeActivity.selectedNormalID){
                sortIconImage.setImageResource(sortType.selectedImage)
                sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
            }
            else{
                sortIconImage.setImageResource(sortType.normImage)
                sortTypeName.setTextColor(Color.BLACK)
            }
        }
        else if (holder.itemViewType == 2222 || holder.itemViewType == 3333) {
            val tempHolder : SquareSortViewHolder = holder as SquareSortViewHolder
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
                }
                else if (holder.itemViewType == 3333) {
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

            if(holder.itemViewType == 3333){
                if(selectedDietId.contains(position.toLong())){
                    // choose --> inside array
                    sortIconImage.setImageResource(sortType.selectedImage)
                    sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
                } else {// not choose --> black
                    sortIconImage.setImageResource(sortType.normImage)
                    sortTypeName.setTextColor(Color.BLACK)
                }
            }

            if(holder.itemViewType == 2222){
                if (selectedCategoryId.contains(position.toLong())){
                    // choose --> inside array
                    sortIconImage.setImageResource(sortType.selectedImage)
                    sortTypeName.setTextColor(Color.parseColor("#FDBF38"))
                }
                else {
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

class SortType(val type: String, val id: Long, val normImage: Int, val selectedImage: Int) {
}

class SortList(
    private val typeList: ArrayList<SortType>,
    private val viewType: Int,
    private val sortTitle: String
) {
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

        tempAdapter.onSortTypeClick = {id, newSelectedArrayOfOneTypeInSelectedIdList ->
            if (position == 0) {
                // unchoose normal sort option
                if(id != (-2).toLong()){
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
