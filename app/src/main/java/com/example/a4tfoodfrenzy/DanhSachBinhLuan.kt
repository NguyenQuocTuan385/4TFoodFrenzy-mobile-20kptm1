package com.example.a4tfoodfrenzy

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class DanhSachBinhLuan : AppCompatActivity() {
    private lateinit var dsBinhLuan: ArrayList<BinhLuan>
    private lateinit var listView: ListView
    private lateinit var adapter: BinhLuanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.danh_sach_binh_luan)

        listView = findViewById(R.id.listView)
        dsBinhLuan = ArrayList<BinhLuan>()

        dsBinhLuan.add(BinhLuan("Đặng Ngọc Tiến", "avt", "", "Rất ngon và đơn giản", "3 ngày trước", "69"))
        dsBinhLuan.add(BinhLuan("Đặng Ngọc Tiến", "avt", "bo_nuong", "Đẹp quá", "3 ngày trước", "99"))
        dsBinhLuan.add(BinhLuan("Trương Gia Tiến", "avt", "", "Ngon như Hiền Phương ??", "1 giờ trước", "69"))
        dsBinhLuan.add(BinhLuan("Nguyễn Văn Việt", "avt", "", "Ngon như Trần Nhi nhà em vậy", "7 ngày trước", "69"))

        adapter = BinhLuanAdapter(this, dsBinhLuan)
        listView.adapter = adapter



    }
}