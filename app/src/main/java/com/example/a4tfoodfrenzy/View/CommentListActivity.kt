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
//        var cmtList = ArrayList<RecipeComment>()
//
//        cmtList.add(RecipeComment(1, "Đặng Ngọc Tiến", "",
//            R.drawable.avt,true,
//            "comrangduabo","Rất ngon và đơn giản", Date()
//        ))
//        cmtList.add(RecipeComment(2, "Đặng Ngọc Tiến", "", R.drawable.avt,true, null,"Đẹp quá", Date()))
//        cmtList.add(RecipeComment(3, "Trương Gia Tiến", "",
//            R.drawable.avt,true, null,"Món ăn tuyệt vời quá", Date()))
//        cmtList.add(RecipeComment(4, "Nguyễn Văn Việt", "",
//            R.drawable.avt,true,
//            "mitrungxaobo","Sao tôi làm món ăn bò sốt me mà ra mì hải sản vậy ?", Date()))


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
                if (cmt == it.id) {
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



        cmtAdapter = CommentListAdapter(this,cmtList, true, false)
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