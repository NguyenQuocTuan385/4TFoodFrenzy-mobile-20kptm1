package com.example.a4tfoodfrenzy

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.RecipeComment
import java.text.SimpleDateFormat
import java.util.*

class Admin_Comment_Management : AppCompatActivity() {
    private lateinit var tvDatePicker : TextView
    private lateinit var btnDatePicker : Button
    var adapterCmtRV: CommentListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_comment_management)

        tvDatePicker = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)


        val myCalendar = Calendar.getInstance()

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))

        val commentRV = findViewById<RecyclerView>(R.id.cmtRV)
        var recipeCommentList = ArrayList<RecipeComment>()

        recipeCommentList.add(
            RecipeComment(
                "Đặng Ngọc Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                R.drawable.bokho,
                "Rất ngon và đơn giản",
                "3 ngày trước"
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Đặng Ngọc Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                0,
                "Đẹp quá",
                "3 ngày trước"
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Trương Gia Tiến",
                "Bún bò huế",
                R.drawable.avt,
                true,
                R.drawable.comrangduabo,
                "Ngon như Hiền Phương ??",
                "1 giờ trước"
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                R.drawable.thitxiennuong,
                "Ngon như Bao Tran nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                "7 ngày trước"
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                R.drawable.thitxiennuong,
                "Ngon như Hong Suong nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                "7 ngày trước"
            )
        )
        recipeCommentList.add(
            RecipeComment(
                "Nguyễn Văn Việt",
                "Bún bò huế",
                R.drawable.avt,
                true,
                R.drawable.thitxiennuong,
                "Ngon như Dieu Trang nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad",
                "7 ngày trước"
            )
        )
        adapterCmtRV = CommentListAdapter(recipeCommentList, false, true)
        commentRV.adapter = adapterCmtRV
        commentRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        commentRV!!.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        btnDatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))
    }
}