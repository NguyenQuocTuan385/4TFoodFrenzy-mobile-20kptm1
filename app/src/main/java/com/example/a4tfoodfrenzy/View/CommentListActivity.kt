package com.example.a4tfoodfrenzy.View

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.CommentListAdapter
import com.example.a4tfoodfrenzy.Model.RecipeComment
import com.example.a4tfoodfrenzy.R
import java.util.*
import kotlin.collections.ArrayList


class CommentListActivity : AppCompatActivity() {
    var cmtAdapter: CommentListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val backBtn = findViewById<Toolbar>(R.id.toolbar2)
        backBtn.setNavigationOnClickListener {
            finish()
        }

        val cmtRV = findViewById<RecyclerView>(R.id.cmtRV)
        var cmtList = ArrayList<RecipeComment>()

        cmtList.add(RecipeComment("Đặng Ngọc Tiến", "",
            R.drawable.avt,true,
            R.drawable.comrangduabo,"Rất ngon và đơn giản", Date()
        ))
        cmtList.add(RecipeComment("Đặng Ngọc Tiến", "", R.drawable.avt,true, 0,"Đẹp quá", Date()))
        cmtList.add(RecipeComment("Trương Gia Tiến", "",
            R.drawable.avt,true, 0,"Món ăn tuyệt vời quá", Date()))
        cmtList.add(RecipeComment("Nguyễn Văn Việt", "",
            R.drawable.avt,true,
            R.drawable.mitrungxaobo,"Sao tôi làm món ăn bò sốt me mà ra mì hải sản vậy ?", Date()))

        cmtAdapter = CommentListAdapter(cmtList, true, false)
        cmtRV.adapter = cmtAdapter
        cmtRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}