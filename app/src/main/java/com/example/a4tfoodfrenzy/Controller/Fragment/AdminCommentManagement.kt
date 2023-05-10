package com.example.a4tfoodfrenzy.Controller.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CommentAdapter.CommentListAdapter
import com.example.a4tfoodfrenzy.Adapter.GridSpacingItemDecoration
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.Controller.Activity.CommentDetailsManagementActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AdminCommentManagement : Fragment() {
    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    lateinit var adapterCmtRV: CommentListAdapter
    val REQUEST_COMMENT_INFORMATION = 1111
    val recipeCommentUserList = ArrayList<RecipeCommentUserItem>()
    lateinit var commentRV: RecyclerView
    var lastPosition:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_comment_management, container, false)

        tvDatePicker = view.findViewById(R.id.tvDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        commentRV = view.findViewById<RecyclerView>(R.id.cmtRV)

        val myCalendar = Calendar.getInstance()
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)

        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val scrollPos = sharedPreferences.getInt("scrollPos", 0)

        val calendarValue = sharedPreferences.getLong("calender", 0)
        val calendar = Calendar.getInstance()
        if (calendarValue != 0L) {
            calendar.timeInMillis = calendarValue
            tvDatePicker.setText(sdf.format(calendar.time))
            updateLable(calendar)
        } else {
            tvDatePicker.setText(sdf.format(myCalendar.time))
            updateLable(myCalendar)
        }

        adapterCmtRV = CommentListAdapter(
            requireContext(),
            recipeCommentUserList,
            false,
            true
        )
        commentRV.adapter = adapterCmtRV
        val linearLayout = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        commentRV.layoutManager = linearLayout
        commentRV.scrollToPosition(scrollPos)
        adapterCmtRV.onItemClick = { reciptCmt, user, foodRecipe, i ->
            val intent = Intent(requireContext(), CommentDetailsManagementActivity::class.java)
            intent.putExtra("foodRecipe", foodRecipe)
            intent.putExtra("recipeCmt", reciptCmt)
            intent.putExtra("user", user)
            startActivityForResult(intent, REQUEST_COMMENT_INFORMATION)
        }
        commentRV.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                lastPosition = linearLayout.findFirstVisibleItemPosition()
            }
        })

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        commentRV.addItemDecoration(GridSpacingItemDecoration(spacingInPixels))

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
            adapterCmtRV.notifyDataSetChanged()
        }
        btnDatePicker.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        return view
    }

    private fun compate2Date(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2

        val year1 = cal1.get(Calendar.YEAR)
        val month1 = cal1.get(Calendar.MONTH)
        val day1 = cal1.get(Calendar.DAY_OF_MONTH)

        val year2 = cal2.get(Calendar.YEAR)
        val month2 = cal2.get(Calendar.MONTH)
        val day2 = cal2.get(Calendar.DAY_OF_MONTH)

        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true
        } else {
            return false
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))

        recipeCommentUserList.clear()

        for (recipeCmt in DBManagement.recipeCommentList) {
            if (compate2Date(recipeCmt.date, myCalendar.time)) {
                if (recipeCmt.description != "") {
                    var userItem = User()
                    var foodItem = FoodRecipe()
                    for (user in DBManagement.userList) {
                        if (user.recipeCmts.contains(recipeCmt.id)) {
                            userItem = user
                        }
                    }
                    for (food in DBManagement.foodRecipeList) {
                        if (food.recipeCmts.contains(recipeCmt.id)) {
                            foodItem = food
                        }
                    }
                    recipeCommentUserList.add(RecipeCommentUserItem(recipeCmt, userItem, foodItem))
                }
            }
        }
    }

    private fun findRecipeCmtWithId(id: Long): RecipeCommentUserItem {
        for (recipeCmt in recipeCommentUserList) {
            if (recipeCmt.recipeComment.id == id) {
                return recipeCmt
            }
        }
        return RecipeCommentUserItem(RecipeComment(), User(), FoodRecipe())
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_COMMENT_INFORMATION) {
            if (resultCode === 1111) { //Delete recipe cmt
                val recipeComment: RecipeComment? = data!!.extras?.getParcelable("recipeCmt")
                val recipeCmt = findRecipeCmtWithId(recipeComment!!.id)
                recipeCommentUserList.remove(recipeCmt)
                adapterCmtRV.notifyDataSetChanged()
            } else if (resultCode === 2222) { //close details recipe cmt

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val sharedPreferences =
            requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("scrollPos", lastPosition).apply()

        // Lấy chuỗi ngày tháng năm được chọn từ tvDatePicker
        val selectedDateStr = tvDatePicker.text.toString()

        // Chuyển đổi chuỗi thành đối tượng Date
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        var selectedDate: Date? = null
        try {
            selectedDate = sdf.parse(selectedDateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val selectedTime = selectedDate!!.time
        sharedPreferences.edit().putLong("calender", selectedTime).apply();
    }
}