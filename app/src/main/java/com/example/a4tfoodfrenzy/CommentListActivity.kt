package com.example.a4tfoodfrenzy

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CommentListActivity : AppCompatActivity() {
    var cmtAdapter: CommentListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val cmtRV = findViewById<RecyclerView>(R.id.cmtRV)
        var cmtList = ArrayList<Comment>()

        cmtList.add(Comment("Đặng Ngọc Tiến", "", R.drawable.avt, R.drawable.comrangduabo,"Rất ngon và đơn giản", "3 ngày trước"))
        cmtList.add(Comment("Đặng Ngọc Tiến", "", R.drawable.avt, 0,"Đẹp quá", "3 ngày trước"))
        cmtList.add(Comment("Trương Gia Tiến", "", R.drawable.avt, 0,"Ngon như Hiền Phương ??", "1 giờ trước"))
        cmtList.add(Comment("Nguyễn Văn Việt", "", R.drawable.avt, R.drawable.mitrungxaobo,"Ngon như Trần Nhi nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad", "7 ngày trước"))

        cmtAdapter = CommentListAdapter(cmtList, true, false)
        cmtRV.adapter = cmtAdapter
        cmtRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}