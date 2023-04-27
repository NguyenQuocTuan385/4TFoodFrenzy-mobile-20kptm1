package com.example.a4tfoodfrenzy.View.Fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.example.a4tfoodfrenzy.View.AfterSearchActivity
import com.example.a4tfoodfrenzy.View.CommentDetailsManagementActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AdminCommentManagement : Fragment() {
    private lateinit var tvDatePicker : TextView
    private lateinit var btnDatePicker : Button
    lateinit var adapterCmtRV: CommentListAdapter
    val REQUEST_COMMENT_INFORMATION = 1111
    val recipeCommentUserList = HashMap<RecipeComment, User>()
    val recipeCommentFoodList = HashMap<RecipeComment, FoodRecipe>()
    lateinit var commentRV:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_admin_comment_management, container, false)

        tvDatePicker = view.findViewById(R.id.tvDate)
        btnDatePicker = view.findViewById(R.id.btnDatePicker)
        commentRV = view.findViewById<RecyclerView>(R.id.cmtRV)

        val myCalendar = Calendar.getInstance()
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)

        tvDatePicker.setText(sdf.format(myCalendar.time))

        for (recipeCmt in DBManagement.recipeCommentList) {
            if (recipeCmt.description != "") {
                for (user in DBManagement.userList) {
                    if (user.recipeCmts.contains(recipeCmt.id)) {
                        recipeCommentUserList.put(recipeCmt, user)
                    }
                }
                for (food in DBManagement.foodRecipeList) {
                    if (food.recipeCmts.contains(recipeCmt.id)) {
                        recipeCommentFoodList.put(recipeCmt, food)
                    }
                }
            }
        }

        adapterCmtRV = CommentListAdapter(requireContext(),recipeCommentUserList,recipeCommentFoodList, false, true)
        commentRV.adapter = adapterCmtRV
        commentRV.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapterCmtRV.onItemClick = { reciptCmt, user, foodRecipe, i ->
            val intent = Intent(requireContext(), CommentDetailsManagementActivity::class.java)
            intent.putExtra("foodRecipe", foodRecipe)
            intent.putExtra("recipeCmt",reciptCmt)
            intent.putExtra("user",user)
            startActivityForResult(intent, REQUEST_COMMENT_INFORMATION)
        }

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
    private fun findRecipeCmtWithId(id: Long) : RecipeComment {
        for((key, value) in recipeCommentUserList) {
            if (key.id == id) {
                return key
            }
        }
        return RecipeComment()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_COMMENT_INFORMATION) {
            if (resultCode === 1111) { //Delete recipe cmt
                val recipeComment:RecipeComment? = data!!.extras?.getParcelable("recipeCmt")
                val recipeCmt = findRecipeCmtWithId(recipeComment!!.id)
                recipeCommentUserList.remove(recipeCmt)
                recipeCommentFoodList.remove(recipeCmt)
                adapterCmtRV.notifyDataSetChanged()
            }
            else if (resultCode === 2222) { //close details recipe cmt

            }
        }
    }


}