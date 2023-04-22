package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Adapter.RecipeListInProfileAdapter
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList

class TabProfileAdapter(private var context: Context,fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }
}


class TabFoodRecipeSaved(private var context: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.saved_recipe_in_profile, container, false)
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.gridView1)

        val monAn = generateRecipeSaved()
        val adapter = context?.let { RecipeListInProfileAdapter(it,monAn, true, false) }
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView1!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recyclerView1!!.adapter = adapter
        recyclerView1!!.layoutManager = GridLayoutManager(inflater.context, 2)
        recyclerView1.adapter = adapter

        return view
    }

    private fun generateRecipeSaved(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()
        for (i in 0..10) {
            val monAn1 = FoodRecipe(1, "Thịt ba chỉ nướng mật ong", "thitbachimatong", 2, "15 phút",
                Date(2022, 2,2), true,
                ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(),"Đặng Ngọc Tiến",R.drawable.defaultavt,0)
            monAn1.authorName = "Đặng Ngọc Tiến"
            result.add(monAn1)
        }
        return result
    }
}

class TabMyFoodRecipe(private var context: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_recipe_in_profile, container, false)
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.created_recipe_RV)
        val monAn = generateRecipeSaved()

        val adapter = context?.let { RecipeListInProfileAdapter(it,monAn, false, true) }
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        recyclerView1!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
        recyclerView1!!.adapter = adapter
        recyclerView1!!.layoutManager = GridLayoutManager(inflater.context, 2)
        recyclerView1.adapter = adapter

        return view
    }

    private fun generateRecipeSaved(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()
            val monAn1 = FoodRecipe(1, "Mực nướng Malaysia", "mucnuongmalaysia", 2, "15 phút",
                Date(2022, 2,2), true,
                ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())

            result.add(monAn1)
        return result
    }
}
