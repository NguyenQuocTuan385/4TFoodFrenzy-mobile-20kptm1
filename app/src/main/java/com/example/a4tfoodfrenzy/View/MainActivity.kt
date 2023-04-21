package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var adapterCateRecipeRV: RecipeCateListAdapter? = null
    var adapterRecipeTodayEatRV: RecipeListAdapter? = null
    var adapterRecipeMostLikesRV: RecipeListAdapter? = null
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    @SuppressLint("WrongThread")
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
        adapterRecipeTodayEatRV = RecipeListAdapter(this, recipeTodayEat)
        recipeTodayEatRV!!.adapter = adapterRecipeTodayEatRV
        recipeTodayEatRV!!.layoutManager = GridLayoutManager(this, 3)
        adapterRecipeTodayEatRV!!.onItemClick = { foodRecipe, i ->
            val intent = Intent(this, ShowRecipeDetailsActivity::class.java)
            startActivity(intent)
        }

        val recipeMostLikesRV = findViewById<RecyclerView>(R.id.recipeMostLikesRV)
        var recipeMostLikes = generateRecipeMostLikesData() //implemened below
        adapterRecipeMostLikesRV = RecipeListAdapter(this, recipeMostLikes)
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
//        generateDatabaseRecipeFoodCate()
//        generateDatabaseRecipeComment()
//        generateDatabaseRecipeDiets()
//        generateDatabaseRecipeFood()
//        generateDatabaseUsers()
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
            "khoquanhoithit", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Canh chua cá lóc", "canhcaloc", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe = FoodRecipe(1, "Bò sốt me", "bosotme", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bò kho", "bokho", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bún bò huế","bunbohue", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe = FoodRecipe(1, "Bưởi trộn khô gà", "buoitronkhoga", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

    fun generateRecipeMostLikesData(): ArrayList<FoodRecipe> {
        var result = ArrayList<FoodRecipe>()

        var foodRecipe = FoodRecipe(1, "Cơm rang dưa bò", "comrangduabo", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mì trứng xào bò", "mitrungxaobo", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)


        foodRecipe= FoodRecipe(1, "Mì quảng gà", "miquangga", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt xiên nướng cà ri", "thitxiennuong", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Mực nướng Malaysia", "mucnuongmalaysia", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        foodRecipe= FoodRecipe(1, "Thịt ba chỉ nướng mật ong", "thitbachimatong", 2, "15 phút",
            Date(2022, 2,2), true,
            ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())
        result.add(foodRecipe)

        return result
    }

    fun uploadImageToCloudStorage(drawableName: String, pathStorage: String) {
        // Tạo đường dẫn đến tệp tin trên Cloud Storage
        val filename = "$drawableName.png"
        val storageRef = storage.reference.child(pathStorage).child(filename)

        // Tải hình ảnh từ thư mục drawable
        val drawableId = resources.getIdentifier(drawableName, "drawable", packageName)
        val bitmap = BitmapFactory.decodeResource(resources, drawableId)

        // Nén hình ảnh và lưu trữ dữ liệu trên Cloud Storage
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)

        storageRef.putBytes(baos.toByteArray())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("Storage", "Upload successful")
                } else {
                    // Tải lên thất bại, do something
                }
            }
    }

    fun generateDatabaseUsers()
    {
        val db = Firebase.firestore
        uploadImageToCloudStorage("defaultavt","users")
        uploadImageToCloudStorage("avt","users")

        var users: ArrayList<User> = ArrayList()
        val user1 = User(
            1,
            "dntienes@gmail.com",
            "Đặng Ngọc Tiến",
            Date(2001, 10, 1),
            "Xin chào, tôi là một đầu bếp trực tuyến đam mê nấu ăn và chia sẻ kiến thức về ẩm thực. Tôi rất vui khi được giới thiệu với các bạn qua đoạn tiểu sử này.",
            "users/defaultavt.png",
            arrayListOf(11,12,13,14,15),
            arrayListOf(1, 2, 3),
            arrayListOf(11, 12, 13)
        );
        users.add(user1)

        val user2 = User(
            2,
            "nqtuan@gmail.com",
            "Nguyễn Quốc Tuấn",
            Date(2002, 2, 2),
            "Tôi đã có hơn 5 năm kinh nghiệm làm việc trong lĩnh vực ẩm thực và tôi luôn cố gắng nâng cao kỹ năng và khả năng của mình bằng cách học hỏi từ các chuyên gia và thực hành nhiều hơn.",
            "users/avt.png",
            arrayListOf(14,15,16),
            arrayListOf(4,5,6),
            arrayListOf(14,15,16)
        )
        users.add(user2)

        val user3 = User(
            3,
            "tgtien@gmail.com",
            "Trương Gia Tiến",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/avt.png",
            arrayListOf(17,18,19),
            arrayListOf(7,8,9),
            arrayListOf(17,18,19)
        )
        users.add(user3)

        val user4 = User(
            4,
            "dcthong@gmail.com",
            "Dương Chí Thông",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/defaultavt.png",
            arrayListOf(20,21,22),
            arrayListOf(10,11, 12),
            arrayListOf(20,21,22)
        )
        users.add(user4)

        val user5 = User(
            5,
            "hienphuong@gmail.com",
            "Hiền Phương",
            Date(2008, 2, 2),
            "Với tinh thần cầu tiến và đam mê nấu ăn, tôi đã trở thành một đầu bếp trực tuyến chuyên nghiệp. Tôi thường xuyên tạo ra các món ăn ngon và độc đáo và chia sẻ với mọi người qua kênh YouTube của mình.",
            "users/defaultavt.png",
            arrayListOf(23,24,25),
            arrayListOf(13,14,15),
            arrayListOf(23,24,25)
        )
        users.add(user5)

        val user6 = User(
            6,
            "nvviet@gmail.com",
            "Nguyễn Văn Việt",
            Date(2002, 2, 2),
            "Sau khi thực hiện một số dự án nấu ăn trực tuyến, tôi nhận thấy rằng đây là một cách tuyệt vời để kết nối với mọi người và chia sẻ sở thích của mình với những người có cùng sở thích.",
            "users/defaultavt.png",
            arrayListOf(26,27,28),
            arrayListOf(16,17,18),
            arrayListOf(26,27,28)
        )
        users.add(user6)

        val user7 = User(
            7,
            "bhvu@gmail.com",
            "Bùi Hoàng Vũ",
            Date(2002, 2, 2),
            "Tôi yêu thích việc sáng tạo các món ăn mới và khám phá văn hóa ẩm thực của các nước khác nhau. Tôi tin rằng món ăn không chỉ là thứ để ăn uống, mà nó còn là một phần không thể thiếu trong cuộc sống của mỗi người.",
            "users/defaultavt.png",
            arrayListOf(29,30,1),
            arrayListOf(19,20,21),
            arrayListOf(29,30,1)
        )
        users.add(user7)

        val user8 = User(
            8,
            "ttnnhi@gmail.com",
            "Trần Thị Ngọc Nhi",
            Date(2002, 2, 2),
            "Tôi rất háo hức khi được hỗ trợ và giúp đỡ những người mới bắt đầu trong lĩnh vực nấu ăn. Tôi sẽ chia sẻ những kiến thức và kinh nghiệm của mình để giúp mọi người trở thành những đầu bếp tài ba.",
            "users/defaultavt.png",
            arrayListOf(2,3,4),
            arrayListOf(29,30,22),
            arrayListOf(2,3,4)
        )
        users.add(user8)

        val user9 = User(
            9,
            "npvinh@gmail.com",
            "Nguyễn Phú Vinh",
            Date(2002, 2, 2),
            "Không chỉ là một đầu bếp trực tuyến, tôi còn là một người đam mê giảng dạy nấu ăn. Tôi tin rằng mỗi người đều có thể học và phát triển kỹ năng nấu ăn của mình, và tôi luôn sẵn sàng chia sẻ kiến thức và kinh nghiệm của mình để giúp mọi người.",
            "users/defaultavt.png",
            arrayListOf(5,6,7),
            arrayListOf(23,24,25),
            arrayListOf(5, 6,7)
        )
        users.add(user9)

        val user10 = User(
            10,
            "ngocthu@gmail.com",
            "Ngọc Thư",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/defaultavt.png",
            arrayListOf(1, 2, 3, 4, 5),
            arrayListOf(26,27,28),
            arrayListOf(1, 2, 3, 4, 5)
        )
        users.add(user10)

        for(user in users) {
            db.collection("users").add(user)
                .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }

    fun generateDatabaseRecipeDiets() {
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

    fun generateDatabaseRecipeFoodCate() {
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

        recipeCate = RecipeCategory(7,"Thức uống", arrayListOf(2,3,5,6,8,10,13,15,18,25))
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

    fun generateDatabaseRecipeComment() {
        var recipeCmtsList:ArrayList<RecipeComment> = ArrayList()
        uploadImageToCloudStorage("bosotme","comments")
        var recipeCmt = RecipeComment(true, "comments/bosotme.png", "Món này nấu ngon lắm, hy vọng bạn ra tiếp công thức mới <3", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, "comments/bokho.png", "Món này có vẻ vẫn còn thiếu gì đó khiến vị chưa được ngon lắm", Date())
        uploadImageToCloudStorage("bokho","comments")
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, null, "Công thức rất rõ ràng, tôi sẽ thử nấu lại lần tiếp theo", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/canhcaloc.png", "Món ăn ngon quá :3", Date())
        uploadImageToCloudStorage("canhcaloc","comments")
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/comrangduabo.png", "Món ăn tuyệt nhất mà tôi từng nấu trước đó", Date())
        uploadImageToCloudStorage("comrangduabo","comments")
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, null, "Tôi có một góp ý nho nhỏ là hình như công thức thiếu 1 nguyên liệu quan trọng nào đó", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Món ăn dở tệ", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, null, "So refreshing dear", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, null, "Món ăn rất healthy, thanh đạm", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/bonuong.png", "Món ăn rất tuyệt vời, 100 điểm :3", Date())
        uploadImageToCloudStorage("bo_nuong","comments")
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Công thức gì chả có đầu có đuôi gì cả, 0 điểm", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/bunbohue.png", "Niceeeeeeeee", Date())
        uploadImageToCloudStorage("bunbohue","comments")
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Too bad", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Món ăn cần nhiều hình ảnh minh họa cho " +
                "rõ các bước hơn", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Không góp ý", Date())
        recipeCmtsList.add(recipeCmt)

        for(recipecmt in recipeCmtsList) {
            db.collection("RecipeCmts").add(recipecmt).addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }

    private fun generateDatabaseRecipeFood(){
        var result = ArrayList<FoodRecipe>()

        var listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150,"Xốt cà chua","g"))
        listIngredient.add(RecipeIngredient(300,"Cá ngừ sạch","g"))

        var listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe1_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Cá ngừ rã đông ở nhiệt độ mát, sau đó rửa sạch",
                "foods/foodrecipe1_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe1_2","foods")
        listStep.add(
            RecipeCookStep(
                "Đặt chảo lên bếp mở lửa vừa cho 100gr dầu ăn vào làm nóng, sau đó cho cá ngừ vào chiên vàng đều 2 mặt, vớt ra thấm ráo dầu.\n" +
                        "Chuẩn bị chảo khác, mở lửa vừa cho 50gr nước cùng với xốt cà chua, cá ngừ đã chiên vàng vào nấu thêm khoảng 5 phút, nêm nếm cho vừa ăn thì tắt bếp.",
                "foods/foodrecipe1_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe1_3","foods")
        listStep.add(
            RecipeCookStep(
                "Cho món ăn ra dĩa và thưởng thúc cùng với cơm trắng",
                "foods/foodrecipe1_3"
            )
        )

        var mon_an_1=FoodRecipe(1,"Cá ngừ chiên sốt gà","foods/foodrecipe1_3",2,"Dưới 30 phút",Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(2,5,8,4),
            arrayListOf(6,12,5,1,9),"Hiền Phương",R.drawable.defaultavt,15)

        result.add(mon_an_1)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g"))
        listIngredient.add(RecipeIngredient(200,"Ba Rọi Heo Cooky (Thịt Tươi)","g"))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g"))
        listIngredient.add(RecipeIngredient(200,"Cá Hú Tươi Làm Sạch Cắt Khúc","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe2_1","foods")
        listStep.add(
            RecipeCookStep(
                "Rửa sạch các nguyên liệu đã sơ chế để ráo nước. Ướp cá và thịt ba rọi với sốt gia vị trong vòng 10 phút.",
                "foods/foodrecipe2_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe2_2","foods")
        listStep.add(
            RecipeCookStep(
                "Cho 1 muỗng canh dầu ăn vào nồi làm nóng. Băm nhuyễn hành tím, tỏi cho vào phi thơm. Cho hỗn hợp cá, thịt ba rọi đã ướp vào đảo nhẹ tay 2 - 3 phút cho thịt và cá hơi săn lại. Thêm 100ml nước lọc vào, kho lửa liu riu 15 phút đến khi nước hơi sệt sệt lại. Nêm nếm lần cuối cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe2_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe2_3","foods")
        listStep.add(
            RecipeCookStep(
                "Cho cá hú kho thịt ba rọi ra đĩa, rắc ít hành lá cắt nhuyễn.",
                "foods/foodrecipe2_3"
            )
        )

        var mon_an_2=FoodRecipe(
            2,
            "Cá Hú Kho Thịt Ba Rọi Heo",
            "foods/foodrecipe2_3",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(2),
            listStep,
            listIngredient,
            arrayListOf(1,3,9,4),
            arrayListOf(8,15,12,9,2,7),
            "Đặng Ngọc Tiến",
            R.drawable.avt,
            10
        )
        result.add(mon_an_2)


        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150,"Mọc Viên","g"))
        listIngredient.add(RecipeIngredient(150,"Xốt Cà Chua","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe3_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "Mọc rã đông ở nhiệt độ mát.",
                "foods/foodrecipe3_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe3_2","foods")
        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo mở lửa vừa cho 50gr nước cùng với xốt cà chua, mọc viên vào đảo đều khoảng 5 phút, nêm nếm cho vừa ăn thì tắt bếp.",
                "foods/foodrecipe3_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe3_3","foods")
        listStep.add(
            RecipeCookStep(
                "Cho món ăn ra dĩa và thưởng thúc cùng với cơm trắng.",
                "foods/foodrecipe3_3"
            )
        )

        var mon_an_3=FoodRecipe(3,"Mọc Viên Xốt Cà","foods/foodrecipe3_3",2,"Dưới 30 phút",Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(1,3,9,4),
            arrayListOf(8,15,12,9,2,7),"Đặng Ngọc Tiến",R.drawable.avt,8)
        result.add(mon_an_3)

        listIngredient = ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200, "Khoai Mỡ (Cắt Sẵn)", "g"))
        listIngredient.add(RecipeIngredient(36, "Bộ Nêm Canh (Ngò Gai, Ngò Om, Bột Gia Vị Canh CookyMADE)", "g"))
        listIngredient.add(RecipeIngredient(100, "Thịt Heo Xay", "g"))

        listStep = ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe4_1","foods")
        listStep.add(
            RecipeCookStep(
                "Rửa sạch các nguyên liệu đã sơ chế, để ráo nước. Rau om, ngò gai cắt nhỏ. Khoai mỡ bào nhuyễn.",
                "foods/foodrecipe4_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe4_2","foods")
        listStep.add(
            RecipeCookStep(
                "Đặt nồi lên bếp, cho 2 muỗng canh dầu ăn vào rồi cho thịt heo xay vào xào săn lại. Sau đó, rót 750ml nước lọc vào nồi đun sôi lên.",
                "foods/foodrecipe4_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe4_3","foods")
        listStep.add(
            RecipeCookStep(
                "Tiếp đến, cho khoai mỡ vào nấu 15 phút. Từ từ cho gói gia vị hoàn chỉnh món canh rau củ vào khuấy đều. Khi khoai đã nhừ, dùng muỗng canh tán thêm cho nhuyễn. Cho ngò gai, rau om lên mặt, nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe4_3"
            )
        )
        uploadImageToCloudStorage("foodrecipe4_4","foods")
        listStep.add(
            RecipeCookStep(
                "Bày món ăn ra tô, rắc một ít tiêu xay lên mặt và thưởng thức. Món canh ngon hơn khi ăn nóng cùng cơm trắng.",
                "foods/foodrecipe4_4"
            )
        )

        var mon_an_4 = FoodRecipe(
            4,
            "Canh Thịt Bằm Nấu Khoai Mỡ",
            "foods/foodrecipe4_4",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(1),
            listStep,
            listIngredient,
            arrayListOf(1, 5, 10,11,2),
            arrayListOf(15, 2, 11, 9, 5,7),
            "Nguyễn Văn Việt",
            R.drawable.defaultavt,
            21
        )
        result.add(mon_an_4)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(280,"Đậu Hũ Ta Vị Nguyên","g"))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g"))
        listIngredient.add(RecipeIngredient(160,"Bộ Xốt Nấm Đông Cô","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe5_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành tây, hành boa rô băm nhỏ\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Nấm đông cô cắt hạt lựu nhỏ.",
                "foods/foodrecipe5_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe5_2","foods")
        listStep.add(
            RecipeCookStep(
                "Bắt nồi lên bếp cho 500ml nước lọc vào đun sôi sau đó cho nguyên miếng đậu hủ vào luộc 5 phút rồi vớt ra cho ráo nước, cắt thành miếng vừa ăn rồi xếp ra đĩa.",
                "foods/foodrecipe5_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe5_3","foods")
        listStep.add(
            RecipeCookStep(
                "Bắt chảo lên bếp cho 20gr canh dầu ăn vào làm nóng, sau đó cho hành boa rô, hành tây vào phi thơm, tiếp tục cho thêm 150ml nước lọc và gói 2 sốt xào chay vào nấu khoảng 5 phút để xốt sệt lại, nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe5_3"
            )
        )
        uploadImageToCloudStorage("foodrecipe5_4","foods")
        listStep.add(
            RecipeCookStep(
                "Rưới xốt nấm vừa nấu xong lên đĩa đậu hủ rắc thêm hành lá, ớt cắt nhỏ lên và thưởng thức. Ăn kèm với cơm trắng",
                "foods/foodrecipe5_4"
            )
        )

        var mon_an_5=FoodRecipe(5,"Đậu Hũ Xốt Nấm Đông Cô Chay","foods/foodrecipe5_4",2,"Dưới 30 phút",Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(1,7,6,9,15,8,3),
            arrayListOf(19,15,4,1,8,11,5,9),"Bùi Hoàng Vũ",R.drawable.defaultavt,100)
        result.add(mon_an_5)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g"))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g"))
        listIngredient.add(RecipeIngredient(200,"Cá Lóc Tươi Làm Sạch (Cắt Khúc)","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe6_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Cá lóc rã đông ở nhiệt độ mát, rửa sạch, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe6_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe6_2","foods")
        listStep.add(
            RecipeCookStep(
                "- Chuẩn bị chảo cho cá đã ướp và 200ml nước vào kho với lửa nhỏ, khoảng 15 phút\n" +
                        "- Nêm nếm lại cho vừa ăn, cuối cùng cho hành lá, ớt rồi tắt bếp.",
                "foods/foodrecipe6_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe6_3","foods")
        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe6_3"
            )
        )

        var mon_an_6=FoodRecipe(
            6,
            "Cá Lóc Kho Tộ",
            "foods/foodrecipe6_3",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(2),
            listStep,
            listIngredient,
            arrayListOf(2,5,8,4,3,1,11,15,18),
            arrayListOf(6,12,5,1,9,15,3,11,19,20),
            "Dương Chí Thông",
            R.drawable.defaultavt,
            20
        )
        result.add(mon_an_6)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g"))
        listIngredient.add(RecipeIngredient(300,"Cốt Lết Heo Cooky (Thịt Tươi)","g"))
        listIngredient.add(RecipeIngredient(37,"Bộ Kho Tiêu (Tiêu Xanh, Hành Lá, Ớt Chỉ Thiên Đỏ, Tiêu Đen Xay)","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe7_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Tiêu xanh đập dập\n" +
                        "- Sườn cốt lết rửa sạch, cắt miếng nhỏ vừa ăn, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe7_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe7_2","foods")
        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo cho sườn cốt lết đã ướp, tiêu xanh và 200ml nước vào kho với lửa nhỏ, khoảng 15 phút\n" +
                        "Nêm nếm lại cho vừa ăn, cuối cùng cho hành lá, ớt, tiêu xay rồi tắt bếp.",
                "foods/foodrecipe7_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe7_3","foods")
        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe7_3"
            )
        )

        var mon_an_7=FoodRecipe(7,"Sườn Cốt Lết Heo Kho Tiêu","foods/foodrecipe7_3",2,"Dưới 30 phút",Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(1,4,5,8,18,14),
            arrayListOf(2,12,3,5),"Trần Thị Ngọc Nhi",R.drawable.defaultavt,10)
        result.add(mon_an_7)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g"))
        listIngredient.add(RecipeIngredient(300,"Thịt Vịt Xiêm (Chặt Sẵn)","g"))
        listIngredient.add(RecipeIngredient(30,"Kho Gừng (Gừng Củ Tươi Gọt Sẵn)","g"))
        listIngredient.add(RecipeIngredient(1,"(Lựa chọn) Dừa Xiêm Bến Tre","trái"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe8_1","foods")
        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Gừng cắt sợi nhỏ vừa ăn\n" +
                        "- Thịt vịt rã đông ở nhiệt độ mát, rửa sạch, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe8_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe8_2","foods")
        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo cho thịt vịt đã ướp, gừng và 200ml nước vào kho với lửa vừa, khoảng 20 phút.\n" +
                        "Nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe8_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe8_3","foods")
        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe8_3"
            )
        )

        var mon_an_8=FoodRecipe(8,"Vịt Kho Gừng","foods/foodrecipe8_3",2,"Dưới 30 phút",Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(3,5,7,10,11,15,19),
            arrayListOf(6,12,5,1,9,10,18),"Ngọc Thư",R.drawable.defaultavt,5)
        result.add(mon_an_8)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200,"Thịt bò","g"))
        listIngredient.add(RecipeIngredient(300,"Bông cải xanh","g"))
        listIngredient.add(RecipeIngredient(1,"Dầu hào","g"))
        listIngredient.add(RecipeIngredient(5,"Bột bắp","g"))

        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe9_1","foods")
        listStep.add(
            RecipeCookStep(
                "Thịt bò ướp 1g dầu hào, tỏi băm, 5g bột bắp. Phi tỏi thơm xào chín\n",
                "foods/foodrecipe9_1"
            )
        )
        uploadImageToCloudStorage("foodrecipe9_2","foods")
        listStep.add(
            RecipeCookStep(
                "Bông cải hấp chín, xào sơ hoặc chứ thế thêm vào đĩa với bò xào, rắc xíu rong biển.\n",
                "foods/foodrecipe9_2"
            )
        )

        var mon_an_9=FoodRecipe(9,"Cơm Bò Xào Bông Cải\n","foods/foodrecipe9_2",2,"Dưới 30 phút",Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(3,5,7,10,11,15,19),
            arrayListOf(6,12,5,1,9,10,18),"Ngọc Thư",R.drawable.defaultavt,5)
        result.add(mon_an_9)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(500,"Bò vụn","g"))
        listIngredient.add(RecipeIngredient(500,"Cải chua","g"))
        listIngredient.add(RecipeIngredient(2,"Cà chua","trái"))
        listIngredient.add(RecipeIngredient(1,"Hành lá","tép"))
        listIngredient.add(RecipeIngredient(1,"Ngò gai","tép"))
        listIngredient.add(RecipeIngredient(3,"Tỏi băm","mcf"))
        listIngredient.add(RecipeIngredient(1,"Nước lọc","lít"))
        listIngredient.add(RecipeIngredient(5,"Dầu ăn","g"))


        listStep=ArrayList<RecipeCookStep>()
        uploadImageToCloudStorage("foodrecipe10_1","foods")
        listStep.add(
            RecipeCookStep(
                "Bò xắt miếng vừa ăn.\nƯớp bò với tỏi băm, hạt nêm. Bọc kín 30 phút trong tủ mát",
                "foods/foodrecipe9_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe10_2","foods")
        listStep.add(
            RecipeCookStep(
                "Dưa cải chua rửa qua, nếu chua quá thì nên trụng sơ rồi để ráo nước.",
                "foods/foodrecipe10_2"
            )
        )
        uploadImageToCloudStorage("foodrecipe10_3","foods")
        listStep.add(
            RecipeCookStep(
                "Bắc chảo phi tổ thơm cho bò vào xào săn.\nThêm nước hoặc nước dừa nấu sôi. Tắt bếp để đó. Nguội thì nấu sôi lại lần nữa rồi lại tắt bếp để đó.",
                "foods/foodrecipe10_3"
            )
        )
        uploadImageToCloudStorage("foodrecipe10_4","foods")
        listStep.add(
            RecipeCookStep(
                "Sau cùng thêm dưa cải nấu mềm.\nNêm lại vừa ăn, thêm cà chua rau thơm.",
                "foods/foodrecipe10_4"
            )
        )

        var mon_an_10=FoodRecipe(10,"Canh Bò Nấu Dưa Cải Chua","foods/foodrecipe10_4",4,"Dưới 1 tiếng",Date(),true,
            arrayListOf(1,4),listStep,listIngredient,
            arrayListOf(1,5,8,7),
            arrayListOf(2,10,5,1,9),"Đặng Ngọc Tiến",R.drawable.avt,10)
        result.add(mon_an_10)

        for(recipefood in result) {
            db.collection("RecipeFoods").add(recipefood).addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }
}