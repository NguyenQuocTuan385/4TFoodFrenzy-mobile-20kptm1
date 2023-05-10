package com.example.a4tfoodfrenzy.Controller.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CommentListAdapter
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import kotlin.collections.ArrayList


class CommentListActivity : AppCompatActivity() {
    var cmtAdapter: CommentListAdapter? = null
    private lateinit var backBtn: Toolbar
    private lateinit var cmtRV: RecyclerView
    private lateinit var recipeCmtList: ArrayList<RecipeCommentUserItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        viewComment()
        event()
    }

    private fun viewComment() {
        recipeCmtList = ArrayList()

        val currentFoodRecipe: FoodRecipe? =
            intent.extras?.getParcelable("foodComment")!!

        for (recipeCmt in DBManagement.recipeCommentList) {
            if (currentFoodRecipe?.recipeCmts?.contains(recipeCmt.id) == true && recipeCmt.description != "") {
                for (user in DBManagement.userList) {
                    if (user.recipeCmts.contains(recipeCmt.id)) {
                        recipeCmtList.add(RecipeCommentUserItem(recipeCmt,user,FoodRecipe()))
                    }
                }
            }
        }

        cmtAdapter = CommentListAdapter(this,recipeCmtList,true, false)
        cmtRV = findViewById(R.id.cmtRV)
        cmtRV.adapter = cmtAdapter
        cmtRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun event(){
        backBtn = findViewById(R.id.toolbar2)
        backBtn.setNavigationOnClickListener {
            finish()
        }

    }
}