package com.example.a4tfoodfrenzy.View.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CommentListAdapter
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.R
import java.text.SimpleDateFormat
import java.util.*


class AdminCommentManagement : Fragment() {
    private lateinit var tvDatePicker : TextView
    private lateinit var btnDatePicker : Button
    var adapterCmtRV: CommentListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_admin_comment_management, container, false)
        tvDatePicker = view.findViewById(R.id.tvDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)


        val myCalendar = Calendar.getInstance()

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))

        val commentRV = view.findViewById<RecyclerView>(R.id.cmtRV)
        var recipeCommentList = ArrayList<RecipeComment>()

        recipeCommentList.add(
            RecipeComment(
                "Đặng Ngọc Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                "bokho",
                "Rất ngon và đơn giản",
                Date()
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Đặng Ngọc Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                null,
                "Đẹp quá",
                Date()
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Trương Gia Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                "comrangduabo",
                "Ngon như Hiền Phương ??",
                Date()
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                "thitxiennuong",
                "Ngon như Bao Tran nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                Date()
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                "thitxiennuong",
                "Ngon như Hong Suong nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                Date()
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                "thitxiennuong",
                "Ngon như Dieu Trang nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                Date()
            )
        )
        adapterCmtRV = CommentListAdapter(requireContext(),recipeCommentList, false, true)
        commentRV.adapter = adapterCmtRV
        commentRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        commentRV!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        btnDatePicker.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        return view
    }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }



}