package com.example.a4tfoodfrenzy.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.RecipeCateListAdapter
import com.example.a4tfoodfrenzy.Adapter.RecipeListAdapter
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var adapterCateRecipeRV: RecipeCateListAdapter? = null
    var adapterRecipeTodayEatRV: RecipeListAdapter? = null
    var adapterRecipeMostLikesRV: RecipeListAdapter? = null
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cateRecipeRV = findViewById<RecyclerView>(R.id.cateRecipeRV)
        var cateRecipeList = generateCateRecipeData() //implemened below

        adapterCateRecipeRV = RecipeCateListAdapter(cateRecipeList, true, false)
        cateRecipeRV!!.adapter = adapterCateRecipeRV
        cateRecipeRV!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterCateRecipeRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val recipeTodayEatRV = findViewById<RecyclerView>(R.id.recipeTodayEatRV)
        var recipeTodayEat = generateRecipeTodayEatData() //implemened below
        adapterRecipeTodayEatRV = RecipeListAdapter(recipeTodayEat)
        recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
        recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        var recipeMostLikes = generateRecipeMostLikesData() //implemened below
        adapterRecipeMostLikesRV = RecipeListAdapter(recipeMostLikes)
        recipeMostLikesRV!!.adapter = adapterRecipeMostLikesRV
        recipeMostLikesRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeMostLikesRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.searchLL).setOnClickListener {
            val intent = Intent(this, SearchScreen::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnViewMoreTodayEat).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnViewMoreMostLikes).setOnClickListener {
            val intent = Intent(this, AfterSearchActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botNavbar)
        val menu = bottomNavigationView.menu

        menu.findItem(R.id.home).isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
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
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
    fun generateCateRecipeData(): ArrayList<RecipeCategorySuggest> {
        var result = ArrayList<RecipeCategorySuggest>()
        var typeRecipe: RecipeCategorySuggest = RecipeCategorySuggest("Nấu nhanh",
            R.drawable.donghonaunhanh
        )
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ uống", R.drawable.douonghome)
        result.add(typeRecipe)


        typeRecipe = RecipeCategorySuggest("Món chính", R.drawable.monchinh)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Đồ ăn vặt", R.drawable.doanvathome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Hải sản", R.drawable.haisanhome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Rau củ", R.drawable.raucuhome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Điểm tâm", R.drawable.banhmihome)
        result.add(typeRecipe)

        typeRecipe = RecipeCategorySuggest("Khai vị", R.drawable.khaivihome)
        result.add(typeRecipe)

        return result
    }
    fun generateRecipeTodayEatData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe: FoodRecipe = FoodRecipe(1, "Canh khổ qua nhồi thịt",
            R.drawable.khoquanhoithit, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Canh chua cá lóc", R.drawable.canhcaloc, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe = FoodRecipe(1, "Bò sốt me", R.drawable.bosotme, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bò kho", R.drawable.bokho, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bún bò huế", R.drawable.bunbohue, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bưởi trộn khô gà", R.drawable.buoitronkhoga, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

    fun generateRecipeMostLikesData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe = FoodRecipe(1, "Cơm rang dưa bò", R.drawable.comrangduabo, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mì trứng xào bò", R.drawable.mitrungxaobo, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe= FoodRecipe(1, "Mì quảng gà", R.drawable.miquangga, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt xiên nướng cà ri", R.drawable.thitxiennuong, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mực nướng Malaysia", R.drawable.mucnuongmalaysia, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt ba chỉ nướng mật ong", R.drawable.thitbachimatong, 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }


    fun createDatabaseUsers()
    {
        val db = Firebase.firestore
        var users: ArrayList<User> = ArrayList()
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
        users.add(user1)

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
        users.add(user2)

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
        users.add(user3)

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
        users.add(user4)

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
        users.add(user5)

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
        users.add(user6)

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
        users.add(user7)

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
        users.add(user8)

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
        users.add(user9)

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
        users.add(user10)

        for(user in users) {
            db.collection("users").add(user).addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }

    fun createDatabaseRecipeDiets() {
        var recipeDietsList:ArrayList<RecipeDiet> = ArrayList()

        var recipeDiet = RecipeDiet(1,"Không đường", arrayListOf(1,3,5,7,9,12,13,21,25))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(2,"Không Gluten", arrayListOf(1,3,6,8,10,11,14,20,22))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(3,"Không thịt", arrayListOf(5,7,9,15,16,17))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(4,"Món thuần chay", arrayListOf(6,5,12,13,18,19))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(5,"Không cồn", arrayListOf(6,8,10,14,18,21,22,26,28,29))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(6,"Món chay", arrayListOf(6,8,10,13,18,23,24,27,28,29,30))
        recipeDietsList.add(recipeDiet)

        for(recipediet in recipeDietsList) {
            db.collection("RecipeDiets").add(recipediet).addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }

    fun createDatabaseRecipeFoodCate() {
        var recipeCatesList:ArrayList<RecipeCategory> = ArrayList()

        var recipeCate = RecipeCategory(1,"Khai vị", arrayListOf(1,3,5,7,9,12,13,21,25))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(2,"Món chính", arrayListOf(1,3,6,8,10,11,14,20,22))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(3,"Ăn vặt", arrayListOf(5,7,9,15,16,17))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(4,"Nấu nhanh", arrayListOf(6,5,12,13,18,19))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(5,"Ăn chay", arrayListOf(6,8,10,14,18,21,22,26,28,29))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(6,"Món tráng miệng", arrayListOf(6,8,10,13,18,23,24,27,28,29,30))
        recipeCatesList.add(recipeCate)

        for(recipecate in recipeCatesList) {
            db.collection("RecipeCates").add(recipecate).addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }
}