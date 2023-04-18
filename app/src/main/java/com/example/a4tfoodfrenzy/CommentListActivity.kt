package com.example.a4tfoodfrenzy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.Comment


class CommentListActivity : AppCompatActivity() {
    var cmtAdapter: CommentListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        val cmtRV = findViewById<RecyclerView>(R.id.cmtRV)
        var cmtList = ArrayList<Comment>()

        cmtList.add(Comment("Đặng Ngọc Tiến", "", R.drawable.avt,true, R.drawable.comrangduabo,"Rất ngon và đơn giản", "3 ngày trước"))
        cmtList.add(Comment("Đặng Ngọc Tiến", "", R.drawable.avt,true, 0,"Đẹp quá", "3 ngày trước"))
        cmtList.add(Comment("Trương Gia Tiến", "", R.drawable.avt,true, 0,"Ngon như Hiền Phương ??", "1 giờ trước"))
        cmtList.add(Comment("Nguyễn Văn Việt", "", R.drawable.avt,true, R.drawable.mitrungxaobo,"Ngon như Trần Nhi nhà em vậydâdadadasdsadasdasdasdsadasdadasdsdadsad", "7 ngày trước"))

        cmtAdapter = CommentListAdapter(cmtList, true, false)
        cmtRV.adapter = cmtAdapter
        cmtRV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}