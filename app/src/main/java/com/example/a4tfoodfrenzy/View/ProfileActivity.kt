package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.a4tfoodfrenzy.Adapter.TabFoodRecipeSaved
import com.example.a4tfoodfrenzy.Adapter.TabMyFoodRecipe
import com.example.a4tfoodfrenzy.Adapter.TabProfileAdapter
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // tạo dữ liệu mẫu
//        createDataUsers()

        // lấy dữ liệu từ firebase
        val db = Firebase.firestore
        val user = db.collection("users").document("ngoctien")
        user.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    val name = findViewById<TextView>(R.id.name_profile)
                    name.text = user?.fullname
                    val avatar = findViewById<ImageView>(R.id.creatorImage)
                    avatar.setImageResource(user?.avatar!!)
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


        val option_adapter = findViewById<ImageView>(R.id.option_profile)
        val list_option = listOf<String>("Chỉnh sửa thông tin", "Đăng xuất")
        option_adapter.setOnClickListener {
            val popup = PopupMenu(this, option_adapter)
            for (i in list_option) {
                popup.menu.add(i)
            }
            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Chỉnh sửa thông tin" -> {
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    "Đăng xuất" -> {
                        val intent = Intent(this, LogoutActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }


        val adapter = TabProfileAdapter(supportFragmentManager)
        adapter.addFragment(TabFoodRecipeSaved(), "Món đã lưu")
        adapter.addFragment(TabMyFoodRecipe(), "Món của tôi")
        val view_pager = findViewById<ViewPager>(R.id.view_pager)
        view_pager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        tabs.setupWithViewPager(view_pager)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu
        menu.findItem(R.id.profile).isChecked = true
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search -> {
                    val intent = Intent(this, SearchScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.addRecipe -> {
                    val intent = Intent(this, AddNewRecipe::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }

    fun createDataUsers()
    {
        val db = Firebase.firestore

        val user1 = User(
            1,
            "dntienes@gmail.com",
            "123456",
            "Đặng Ngọc Tiến",
            Date(2001, 10, 1),
            "Xin chào, tôi là một đầu bếp trực tuyến đam mê nấu ăn và chia sẻ kiến thức về ẩm thực. Tôi rất vui khi được giới thiệu với các bạn qua đoạn tiểu sử này.",
            R.drawable.avt,
            arrayListOf(11,12,13,14,15),
            arrayListOf(1, 2, 3),
            arrayListOf(11, 12, 13)
        );


        val user2 = User(
            2,
            "nqtuan@gmail.com",
            "123456",
            "Nguyễn Quốc Tuấn",
            Date(2002, 2, 2),
            "Tôi đã có hơn 5 năm kinh nghiệm làm việc trong lĩnh vực ẩm thực và tôi luôn cố gắng nâng cao kỹ năng và khả năng của mình bằng cách học hỏi từ các chuyên gia và thực hành nhiều hơn.",
            R.drawable.avt,
            arrayListOf(14,15,16),
            arrayListOf(4,5,6),
            arrayListOf(14,15,16)
        )


        val user3 = User(
            3,
            "tgtien@gmail.com",
            "123456",
            "Trương Gia Tiến",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            R.drawable.avt,
            arrayListOf(17,18,19),
            arrayListOf(7,8,9),
            arrayListOf(17,18,19)
        )

        val user4 = User(
            4,
            "dcthong@gmail.com",
            "123456",
            "Dương Chí Thông",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            R.drawable.avt,
            arrayListOf(20,21,22),
            arrayListOf(10,11, 12),
            arrayListOf(20,21,22)
        )

        val user5 = User(
            5,
            "hienphuong@gmail.com",
            "123456",
            "Hiền Phương",
            Date(2008, 2, 2),
            "Với tinh thần cầu tiến và đam mê nấu ăn, tôi đã trở thành một đầu bếp trực tuyến chuyên nghiệp. Tôi thường xuyên tạo ra các món ăn ngon và độc đáo và chia sẻ với mọi người qua kênh YouTube của mình.",
            R.drawable.avt,
            arrayListOf(23,24,25),
            arrayListOf(13,14,15),
            arrayListOf(23,24,25)
        )

        val user6 = User(
            6,
            "nvviet@gmail.com",
            "123456",
            "Nguyễn Văn Việt",
            Date(2002, 2, 2),
            "Sau khi thực hiện một số dự án nấu ăn trực tuyến, tôi nhận thấy rằng đây là một cách tuyệt vời để kết nối với mọi người và chia sẻ sở thích của mình với những người có cùng sở thích.",
            R.drawable.avt,
            arrayListOf(26,27,28),
            arrayListOf(16,17,18),
            arrayListOf(26,27,28)
        )

        val user7 = User(
            7,
            "bhvu@gmail.com",
            "123456",
            "Bùi Hoàng Vũ",
            Date(2002, 2, 2),
            "Tôi yêu thích việc sáng tạo các món ăn mới và khám phá văn hóa ẩm thực của các nước khác nhau. Tôi tin rằng món ăn không chỉ là thứ để ăn uống, mà nó còn là một phần không thể thiếu trong cuộc sống của mỗi người.",
            R.drawable.avt,
            arrayListOf(29,30,1),
            arrayListOf(19,20,21),
            arrayListOf(29,30,1)
        )

        val user8 = User(
            8,
            "ttnnhi@gmail.com",
            "123456",
            "Trần Thị Ngọc Nhi",
            Date(2002, 2, 2),
            "Tôi rất háo hức khi được hỗ trợ và giúp đỡ những người mới bắt đầu trong lĩnh vực nấu ăn. Tôi sẽ chia sẻ những kiến thức và kinh nghiệm của mình để giúp mọi người trở thành những đầu bếp tài ba.",
            R.drawable.avt,
            arrayListOf(2,3,4),
            arrayListOf(29,30,22),
            arrayListOf(2,3,4)
        )

        val user9 = User(
            9,
            "npvinh@gmail.com",
            "123456",
            "Nguyễn Phú Vinh",
            Date(2002, 2, 2),
            "Không chỉ là một đầu bếp trực tuyến, tôi còn là một người đam mê giảng dạy nấu ăn. Tôi tin rằng mỗi người đều có thể học và phát triển kỹ năng nấu ăn của mình, và tôi luôn sẵn sàng chia sẻ kiến thức và kinh nghiệm của mình để giúp mọi người.",
            R.drawable.avt,
            arrayListOf(5,6,7),
            arrayListOf(23,24,25),
            arrayListOf(5, 6,7)
        )

        val user10 = User(
            10,
            "ngocthu@gmail.com",
            "123456",
            "Ngọc Thư",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            R.drawable.avt,
            arrayListOf(1, 2, 3, 4, 5),
            arrayListOf(26,27,28),
            arrayListOf(1, 2, 3, 4, 5)
        )

        db.collection("users").document("ngoctien").set(user1)
        db.collection("users").document("tuan").set(user2)
        db.collection("users").document("giatien").set(user3)
        db.collection("users").document("thong").set(user4)
        db.collection("users").document("hienphuong").set(user5)
        db.collection("users").document("viet").set(user6)
        db.collection("users").document("vu").set(user7)
        db.collection("users").document("nhi").set(user8)
        db.collection("users").document("vinh").set(user9)
        db.collection("users").document("ngocthu").set(user10)

    }
}