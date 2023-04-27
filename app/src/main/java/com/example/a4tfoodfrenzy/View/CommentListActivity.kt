package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CommentListAdapter
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CommentListActivity : AppCompatActivity() {
    var cmtAdapter: CommentListAdapter? = null
    private lateinit var backBtn: Toolbar
    private lateinit var cmtRV: RecyclerView
    private lateinit var cmtList: HashMap<RecipeComment, User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        viewComment()
        event()
    }

    private fun viewComment() {
        cmtList = HashMap()
        var comments = ArrayList<RecipeComment>()
        val currentFoodRecipe: FoodRecipe? =
            intent.extras?.getParcelable("foodComment")!!

        // test
//        var test = FoodRecipe()
//        DBManagement.foodRecipeList.forEach {
//            if (it.id.toInt() == 2) {
//                test = it
//            }
//        }

        // lấy ds comment của món ăn
        DBManagement.recipeCommentList.forEach {
            currentFoodRecipe?.recipeCmts?.forEach { cmt ->
                if (cmt == it.id && it.description != "") {
                    comments.add(it)
                }
            }
        }

        // lấy ds user của comment
        DBManagement.userList.forEach { user ->
            comments.forEach { cmt ->
                user.recipeCmts.forEach { cmtId ->
                    if (cmtId == cmt.id) {
                        cmtList[cmt] = user
                    }
                }
            }
        }



        cmtAdapter = CommentListAdapter(this,cmtList, HashMap(),true, false)
        cmtRV = findViewById(R.id.cmtRV)
        cmtRV.adapter = cmtAdapter
        cmtRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun event(){
        backBtn = findViewById(R.id.toolbar2)
        backBtn.setNavigationOnClickListener {
            finish()
        }

        cmtRV = findViewById(R.id.cmtRV)
        findViewById<Button>(R.id.btnAddComment).setOnClickListener {
            val intent = Intent(this, WriteCommentActivity::class.java)
            startActivity(intent)
        }
    }
}