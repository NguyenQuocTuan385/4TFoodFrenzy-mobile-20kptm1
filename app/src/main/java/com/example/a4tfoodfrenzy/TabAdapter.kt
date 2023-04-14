package com.example.a4tfoodfrenzy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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


class TabFragment1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.saved_recipe_list, container, false)

        val gridView = view.findViewById<GridView>(R.id.gridView1)
        val monAn = ArrayList<MonAn>()

        for (i in 0..10) {
            val monAn1 = MonAn()
            monAn1.title = "Món ăn $i"
            monAn1.image = "Image $i"
            monAn.add(monAn1)
        }

        val adapter = MonAnDaLuuAdapter(requireContext(), monAn)
        gridView.adapter = adapter

        return view
    }
}

class TabFragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.created_recipe_list, container, false)
        val gridView1 = view.findViewById<GridView>(R.id.gridView2)
        val monAn = ArrayList<MonAn>()

        for (i in 0..10) {
            val monAn1 = MonAn()
            monAn1.title = "Món ăn $i"
            monAn1.image = "Image $i"
            monAn.add(monAn1)
        }

        val adapter = CreatedRecipe(requireContext(), monAn)
        gridView1.adapter = adapter

        return view
    }
}
