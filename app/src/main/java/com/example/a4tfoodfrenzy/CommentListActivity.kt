package com.example.a4tfoodfrenzy

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class CommentListActivity : AppCompatActivity() {
    private lateinit var dsBinhLuan: ArrayList<Comment>
    private lateinit var listView: ListView
    private lateinit var adapter: CommentListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        listView = findViewById(R.id.listView)
        dsBinhLuan = ArrayList<Comment>()

        dsBinhLuan.add(Comment("Đặng Ngọc Tiến", "avt", "", "Rất ngon và đơn giản", "3 ngày trước", "69"))
        dsBinhLuan.add(Comment("Đặng Ngọc Tiến", "avt", "bo_nuong", "Đẹp quá", "3 ngày trước", "99"))
        dsBinhLuan.add(Comment("Trương Gia Tiến", "avt", "chicken", "Ngon như Hiền Phương ??", "1 giờ trước", "69"))
        dsBinhLuan.add(Comment("Nguyễn Văn Việt", "avt", "", "Ngon như Trần Nhi nhà em vậy", "7 ngày trước", "69"))

        adapter = CommentListAdapter(this, dsBinhLuan)
        listView.adapter = adapter



    }
}