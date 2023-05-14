package com.example.a4tfoodfrenzy.Helper

import android.content.Context
import android.util.Log
import com.example.a4tfoodfrenzy.Model.*
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class GenerateDBModel(private var context: Context) {

    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    val helperFunctionDB = HelperFunctionDB(context)
    fun generateDatabaseUsers()
    {
        val db = Firebase.firestore
        helperFunctionDB.uploadImageToCloudStorage("defaultavt","users")
        helperFunctionDB.uploadImageToCloudStorage("avt","users")

        var users: ArrayList<User> = ArrayList()
        val user1 = User(
            1,
            "dntienes@gmail.com",
            "Đặng Ngọc Tiến",
            Date(2001, 10, 1),
            "Xin chào, tôi là một đầu bếp trực tuyến đam mê nấu ăn và chia sẻ kiến thức về ẩm thực. Tôi rất vui khi được giới thiệu với các bạn qua đoạn tiểu sử này.",
            "users/defaultavt.png",
            arrayListOf(1,2),
            arrayListOf(1, 2,3,4),
            arrayListOf(1,2,3), false
        );
        users.add(user1)

        val user2 = User(
            2,
            "nqtuan@gmail.com",
            "Nguyễn Quốc Tuấn",
            Date(2002, 2, 2),
            "Tôi đã có hơn 5 năm kinh nghiệm làm việc trong lĩnh vực ẩm thực và tôi luôn cố gắng nâng cao kỹ năng và khả năng của mình bằng cách học hỏi từ các chuyên gia và thực hành nhiều hơn.",
            "users/avt.png",
            arrayListOf(3,4),
            arrayListOf(5,6),
            arrayListOf(1,2,3), false
        )
        users.add(user2)

        val user3 = User(
            3,
            "tgtien@gmail.com",
            "Trương Gia Tiến",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/avt.png",
            arrayListOf(5,6),
            arrayListOf(7,8),
            arrayListOf(5), false
        )
        users.add(user3)

        val user4 = User(
            4,
            "dcthong@gmail.com",
            "Dương Chí Thông",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/defaultavt.png",
            arrayListOf(),
            arrayListOf(9,10,11),
            arrayListOf(5,6,7), false
        )
        users.add(user4)

        val user5 = User(
            5,
            "hienphuong@gmail.com",
            "Hiền Phương",
            Date(2008, 2, 2),
            "Với tinh thần cầu tiến và đam mê nấu ăn, tôi đã trở thành một đầu bếp trực tuyến chuyên nghiệp. Tôi thường xuyên tạo ra các món ăn ngon và độc đáo và chia sẻ với mọi người qua kênh YouTube của mình.",
            "users/defaultavt.png",
            arrayListOf(7,8),
            arrayListOf(12,13,14,15),
            arrayListOf(1,3,4), false
        )
        users.add(user5)

        val user6 = User(
            6,
            "nvviet@gmail.com",
            "Nguyễn Văn Việt",
            Date(2002, 2, 2),
            "Sau khi thực hiện một số dự án nấu ăn trực tuyến, tôi nhận thấy rằng đây là một cách tuyệt vời để kết nối với mọi người và chia sẻ sở thích của mình với những người có cùng sở thích.",
            "users/defaultavt.png",
            arrayListOf(),
            arrayListOf(16,17,18),
            arrayListOf(3,5,8), false
        )
        users.add(user6)

        val user7 = User(
            7,
            "bhvu@gmail.com",
            "Bùi Hoàng Vũ",
            Date(2002, 2, 2),
            "Tôi yêu thích việc sáng tạo các món ăn mới và khám phá văn hóa ẩm thực của các nước khác nhau. Tôi tin rằng món ăn không chỉ là thứ để ăn uống, mà nó còn là một phần không thể thiếu trong cuộc sống của mỗi người.",
            "users/defaultavt.png",
            arrayListOf(9,10),
            arrayListOf(19,20,21,26,27,28,29,30),
            arrayListOf(), false
        )
        users.add(user7)

        val user8 = User(
            8,
            "ttnnhi@gmail.com",
            "Trần Thị Ngọc Nhi",
            Date(2002, 2, 2),
            "Tôi rất háo hức khi được hỗ trợ và giúp đỡ những người mới bắt đầu trong lĩnh vực nấu ăn. Tôi sẽ chia sẻ những kiến thức và kinh nghiệm của mình để giúp mọi người trở thành những đầu bếp tài ba.",
            "users/defaultavt.png",
            arrayListOf(11,12),
            arrayListOf(22,31,32,33),
            arrayListOf(), false
        )
        users.add(user8)

        val user9 = User(
            9,
            "npvinh@gmail.com",
            "Nguyễn Phú Vinh",
            Date(2002, 2, 2),
            "Không chỉ là một đầu bếp trực tuyến, tôi còn là một người đam mê giảng dạy nấu ăn. Tôi tin rằng mỗi người đều có thể học và phát triển kỹ năng nấu ăn của mình, và tôi luôn sẵn sàng chia sẻ kiến thức và kinh nghiệm của mình để giúp mọi người.",
            "users/defaultavt.png",
            arrayListOf(13),
            arrayListOf(24,23, 34),
            arrayListOf(), false
        )
        users.add(user9)

        val user10 = User(
            10,
            "ngocthu@gmail.com",
            "Ngọc Thư",
            Date(2002, 2, 2),
            "Tôi đã có cơ hội làm việc tại nhiều nhà hàng và khách sạn nổi tiếng ở nhiều quốc gia khác nhau, từ đó tôi đã học được rất nhiều kiến thức và kinh nghiệm về ẩm thực.",
            "users/defaultavt.png",
            arrayListOf(14,15),
            arrayListOf(25,35),
            arrayListOf(1, 2, 3), false
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

        var recipeDiet = RecipeDiet(1,"Không đường", arrayListOf(1,2,6,8,9,10,11,13,14,15,16,18,19,20,21,22,23,24,25))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(2,"Không Gluten", arrayListOf(3,5,7,12,17,26,27,28,29,30))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(3,"Không thịt", arrayListOf(1,2,3,4,5,6,7,31,32,33,34,35))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(4,"Món thuần chay", arrayListOf(3,4,5,6,7,8,9,10,11,12,13))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(5,"Không cồn", arrayListOf(21,22,23,24,25,26,27,28,29,30,31,32,33,34,35))
        recipeDietsList.add(recipeDiet)

        recipeDiet = RecipeDiet(6,"Món chay", arrayListOf(3,4,5,7,8,9,10,12,13))
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
//        helperFunctionDB.uploadImageToCloudStorage("douonghome","categories")
//        helperFunctionDB.uploadImageToCloudStorage("montrangmienghome","categories")
//        helperFunctionDB.uploadImageToCloudStorage("monchinh","categories")
//        helperFunctionDB.uploadImageToCloudStorage("doanvathome","categories")
//        helperFunctionDB.uploadImageToCloudStorage("banhmihome","categories")
//        helperFunctionDB.uploadImageToCloudStorage("khaivihome","categories")
//
//        helperFunctionDB.uploadImageToCloudStorage("drink","categories")
//        helperFunctionDB.uploadImageToCloudStorage("fastfood","categories")
//        helperFunctionDB.uploadImageToCloudStorage("english_breakfast","categories")
//        helperFunctionDB.uploadImageToCloudStorage("mainfood","categories")
//        helperFunctionDB.uploadImageToCloudStorage("dessert_search","categories")
//        helperFunctionDB.uploadImageToCloudStorage("appetizer_search","categories")

        var recipeCate = RecipeCategory(1,"Khai vị", "categories/khaivihome.png",
            "categories/appetizer_search.png",arrayListOf(16,17,18,19,20))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(2,"Món chính", "categories/monchinh.png",
            "categories/mainfood.png",arrayListOf(1,2,4,5,6,7,8,9,10))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(3,"Ăn vặt", "categories/doanvathome.png",
            "categories/fastfood.png",arrayListOf(26,27,28,29,30))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(4,"Điểm tâm", "categories/banhmihome.png",
            "categories/english_breakfast.png", arrayListOf(21,22,23,24,25))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(5,"Món tráng miệng","categories/montrangmienghome.png",
            "categories/dessert_search.png",arrayListOf(31,32,33,34,35))
        recipeCatesList.add(recipeCate)

        recipeCate = RecipeCategory(6,"Thức uống", "categories/douonghome.png",
            "categories/drink.png", arrayListOf(11,12,13,14,15))
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
//        helperFunctionDB.uploadImageToCloudStorage("bosotme","comments")
//        helperFunctionDB.uploadImageToCloudStorage("bokho","comments")
//        helperFunctionDB.uploadImageToCloudStorage("canhcaloc","comments")
//        helperFunctionDB.uploadImageToCloudStorage("comrangduabo","comments")
//        helperFunctionDB.uploadImageToCloudStorage("bo_nuong","comments")
//        helperFunctionDB.uploadImageToCloudStorage("bunbohue","comments")

        var recipeCmt = RecipeComment(1, true, "comments/bosotme.png", "Món này nấu ngon lắm, hy vọng bạn ra tiếp công thức mới <3", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(2, false, "comments/bokho.png", "Món này có vẻ vẫn còn thiếu gì đó khiến vị chưa được ngon lắm", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(3, true, null, "Công thức rất rõ ràng, tôi sẽ thử nấu lại lần tiếp theo", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(4, true, "comments/canhcaloc.png", "Món ăn ngon quá :3", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(5, true, "comments/comrangduabo.png", "Món ăn tuyệt nhất mà tôi từng nấu trước đó", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(6, true, null, "Tôi có một góp ý nho nhỏ là hình như công thức thiếu 1 nguyên liệu quan trọng nào đó", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(7, false, null, "Món ăn dở tệ", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(8, true, null, "So refreshing dear", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(9, true, null, "Món ăn rất healthy, thanh đạm", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(10, true, "comments/bonuong.png", "Món ăn rất tuyệt vời, 100 điểm :3", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(11, false, null, "Công thức gì chả có đầu có đuôi gì cả, 0 điểm", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(12, true, "comments/bunbohue.png", "Niceeeeeeeee", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(13, false, null, "Too bad", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(14, false, null, "Món ăn cần nhiều hình ảnh minh họa cho " +
                "rõ các bước hơn", Date()
        )
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(15, false, null, "Không góp ý", Date())
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

    fun generateDatabaseRecipeFood(){
        var result = ArrayList<FoodRecipe>()

//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe9_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe9_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe11_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe11_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe11_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe11_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe11_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe12_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe12_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe12_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe12_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe13_6","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe14_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe14_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe14_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe14_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe15_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe15_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe16_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe16_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe17_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe18_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe18_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe19_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe19_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe19_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe20_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe20_2","foods")
//
//                helperFunctionDB.uploadImageToCloudStorage("foodrecipe26","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe26_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe26_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe26_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe26_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe27","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe27_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe27_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe27_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe28_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_6","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe29_7","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe30_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe31_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe31_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe31_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe31_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe31_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe32_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe32_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe32_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe32_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe33_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe33_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_5","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe34_6","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe35_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe35_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe35_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe35_4","foods")

        // 5 món diểm tâm
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe36_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe36_2","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe36_3","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe36_4","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe36_5","foods")
//
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe37_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe37_2","foods")
//
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe38_1","foods")
//
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe39_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe39_2","foods")
//
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe40_1","foods")
//        helperFunctionDB.uploadImageToCloudStorage("foodrecipe40_2","foods")

        var listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150.5,"Xốt cà chua","g",50.0))
        listIngredient.add(RecipeIngredient(300.5,"Cá ngừ sạch","g",100.0))

        var listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Cá ngừ rã đông ở nhiệt độ mát, sau đó rửa sạch",
                "foods/foodrecipe1_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Đặt chảo lên bếp mở lửa vừa cho 100gr dầu ăn vào làm nóng, sau đó cho cá ngừ vào chiên vàng đều 2 mặt, vớt ra thấm ráo dầu.\n" +
                        "Chuẩn bị chảo khác, mở lửa vừa cho 50gr nước cùng với xốt cà chua, cá ngừ đã chiên vàng vào nấu thêm khoảng 5 phút, nêm nếm cho vừa ăn thì tắt bếp.",
                "foods/foodrecipe1_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho món ăn ra dĩa và thưởng thúc cùng với cơm trắng",
                "foods/foodrecipe1_3.png"
            )
        )

        var mon_an_1=
            FoodRecipe(1,"Cá ngừ chiên sốt gà","foods/foodrecipe1_3.png",2,"Dưới 30 phút", Date(),true,
                arrayListOf(1,3),listStep,listIngredient,
                arrayListOf(1,2),
                arrayListOf(1,2,5,10), 1)

        result.add(mon_an_1)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60.0,"Xốt Kho Tộ CookyMADE","g",100.0))
        listIngredient.add(RecipeIngredient(200.0,"Ba Rọi Heo Cooky (Thịt Tươi)","g",150.0))
        listIngredient.add(RecipeIngredient(15.0,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",50.0))
        listIngredient.add(RecipeIngredient(200.5,"Cá Hú Tươi Làm Sạch Cắt Khúc","g",100.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Rửa sạch các nguyên liệu đã sơ chế để ráo nước. Ướp cá và thịt ba rọi với sốt gia vị trong vòng 10 phút.",
                "foods/foodrecipe2_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho 1 muỗng canh dầu ăn vào nồi làm nóng. Băm nhuyễn hành tím, tỏi cho vào phi thơm. Cho hỗn hợp cá, thịt ba rọi đã ướp vào đảo nhẹ tay 2 - 3 phút cho thịt và cá hơi săn lại. Thêm 100ml nước lọc vào, kho lửa liu riu 15 phút đến khi nước hơi sệt sệt lại. Nêm nếm lần cuối cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe2_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho cá hú kho thịt ba rọi ra đĩa, rắc ít hành lá cắt nhuyễn.",
                "foods/foodrecipe2_3.png"
            )
        )

        var mon_an_2= FoodRecipe(
            2,
            "Cá Hú Kho Thịt Ba Rọi Heo",
            "foods/foodrecipe2_3.png",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(1,3),
            listStep,
            listIngredient,
            arrayListOf(),
            arrayListOf(1,2,10),0)
        result.add(mon_an_2)


        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150.0,"Mọc Viên","g",100.0))
        listIngredient.add(RecipeIngredient(150.5,"Xốt Cà Chua","g",150.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "Mọc rã đông ở nhiệt độ mát.",
                "foods/foodrecipe3_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo mở lửa vừa cho 50gr nước cùng với xốt cà chua, mọc viên vào đảo đều khoảng 5 phút, nêm nếm cho vừa ăn thì tắt bếp.",
                "foods/foodrecipe3_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho món ăn ra dĩa và thưởng thúc cùng với cơm trắng.",
                "foods/foodrecipe3_3.png"
            )
        )

        var mon_an_3=
            FoodRecipe(3,"Mọc Viên Xốt Cà","foods/foodrecipe3_3.png",2,"Dưới 30 phút", Date(),true,
                arrayListOf(2,3,4,6),listStep,listIngredient,
                arrayListOf(),
                arrayListOf(1,2,5,6,10),0)
        result.add(mon_an_3)

        listIngredient = ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200.0, "Khoai Mỡ (Cắt Sẵn)", "g",100.0))
        listIngredient.add(RecipeIngredient(36.5, "Bộ Nêm Canh (Ngò Gai, Ngò Om, Bột Gia Vị Canh CookyMADE)", "g",50.0))
        listIngredient.add(RecipeIngredient(100.5, "Thịt Heo Xay", "g",150.0))

        listStep = ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Rửa sạch các nguyên liệu đã sơ chế, để ráo nước. Rau om, ngò gai cắt nhỏ. Khoai mỡ bào nhuyễn.",
                "foods/foodrecipe4_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Đặt nồi lên bếp, cho 2 muỗng canh dầu ăn vào rồi cho thịt heo xay vào xào săn lại. Sau đó, rót 750ml nước lọc vào nồi đun sôi lên.",
                "foods/foodrecipe4_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Tiếp đến, cho khoai mỡ vào nấu 15 phút. Từ từ cho gói gia vị hoàn chỉnh món canh rau củ vào khuấy đều. Khi khoai đã nhừ, dùng muỗng canh tán thêm cho nhuyễn. Cho ngò gai, rau om lên mặt, nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe4_3.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Bày món ăn ra tô, rắc một ít tiêu xay lên mặt và thưởng thức. Món canh ngon hơn khi ăn nóng cùng cơm trắng.",
                "foods/foodrecipe4_4.png"
            )
        )

        var mon_an_4 = FoodRecipe(
            4,
            "Canh Thịt Bằm Nấu Khoai Mỡ",
            "foods/foodrecipe4_4.png",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(3,4,6),
            listStep,
            listIngredient,
            arrayListOf(3,4),
            arrayListOf(5), 2
        )
        result.add(mon_an_4)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(280.5,"Đậu Hũ Ta Vị Nguyên","g",50.0))
        listIngredient.add(RecipeIngredient(15.0,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",100.0))
        listIngredient.add(RecipeIngredient(160.0,"Bộ Xốt Nấm Đông Cô","g",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành tây, hành boa rô băm nhỏ\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Nấm đông cô cắt hạt lựu nhỏ.",
                "foods/foodrecipe5_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Bắt nồi lên bếp cho 500ml nước lọc vào đun sôi sau đó cho nguyên miếng đậu hủ vào luộc 5 phút rồi vớt ra cho ráo nước, cắt thành miếng vừa ăn rồi xếp ra đĩa.",
                "foods/foodrecipe5_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Bắt chảo lên bếp cho 20gr canh dầu ăn vào làm nóng, sau đó cho hành boa rô, hành tây vào phi thơm, tiếp tục cho thêm 150ml nước lọc và gói 2 sốt xào chay vào nấu khoảng 5 phút để xốt sệt lại, nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe5_3.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Rưới xốt nấm vừa nấu xong lên đĩa đậu hủ rắc thêm hành lá, ớt cắt nhỏ lên và thưởng thức. Ăn kèm với cơm trắng",
                "foods/foodrecipe5_4.png"
            )
        )

        var mon_an_5= FoodRecipe(5,"Đậu Hũ Xốt Nấm Đông Cô Chay","foods/foodrecipe5_4.png",2,"Dưới 30 phút",
            Date(),true,
            arrayListOf(2,3,4,6),listStep,listIngredient,
            arrayListOf(5,6,7),
            arrayListOf(3,4,6),2)
        result.add(mon_an_5)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60.0,"Xốt Kho Tộ CookyMADE","g",50.0))
        listIngredient.add(RecipeIngredient(15.5,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",100.0))
        listIngredient.add(RecipeIngredient(200.0,"Cá Lóc Tươi Làm Sạch (Cắt Khúc)","g",150.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Cá lóc rã đông ở nhiệt độ mát, rửa sạch, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe6_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "- Chuẩn bị chảo cho cá đã ướp và 200ml nước vào kho với lửa nhỏ, khoảng 15 phút\n" +
                        "- Nêm nếm lại cho vừa ăn, cuối cùng cho hành lá, ớt rồi tắt bếp.",
                "foods/foodrecipe6_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe6_3.png"
            )
        )

        var mon_an_6= FoodRecipe(
            6,
            "Cá Lóc Kho Tộ",
            "foods/foodrecipe6_3.png",
            2,
            "Dưới 30 phút",
            Date(),
            true,
            arrayListOf(1,3,4),
            listStep,
            listIngredient,
            arrayListOf(8,9,10),
            arrayListOf(4),3
        )
        result.add(mon_an_6)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60.5,"Xốt Kho Tộ CookyMADE","g",150.0))
        listIngredient.add(RecipeIngredient(300.5,"Cốt Lết Heo Cooky (Thịt Tươi)","g",50.0))
        listIngredient.add(RecipeIngredient(37.5,"Bộ Kho Tiêu (Tiêu Xanh, Hành Lá, Ớt Chỉ Thiên Đỏ, Tiêu Đen Xay)","g",100.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Hành lá, ớt cắt nhỏ\n" +
                        "- Tiêu xanh đập dập\n" +
                        "- Sườn cốt lết rửa sạch, cắt miếng nhỏ vừa ăn, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe7_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo cho sườn cốt lết đã ướp, tiêu xanh và 200ml nước vào kho với lửa nhỏ, khoảng 15 phút\n" +
                        "Nêm nếm lại cho vừa ăn, cuối cùng cho hành lá, ớt, tiêu xay rồi tắt bếp.",
                "foods/foodrecipe7_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe7_3.png"
            )
        )

        var mon_an_7= FoodRecipe(7,"Sườn Cốt Lết Heo Kho Tiêu","foods/foodrecipe7_3.png",2,"Dưới 30 phút",
            Date(),true,
            arrayListOf(2,3,4,6),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(4),0)
        result.add(mon_an_7)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60.5,"Xốt Kho Tộ CookyMADE","g",150.0))
        listIngredient.add(RecipeIngredient(300.0,"Thịt Vịt Xiêm (Chặt Sẵn)","g",50.0))
        listIngredient.add(RecipeIngredient(30.0,"Kho Gừng (Gừng Củ Tươi Gọt Sẵn)","g",200.0))
        listIngredient.add(RecipeIngredient(1.5,"(Lựa chọn) Dừa Xiêm Bến Tre","trái",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sơ chế, rửa sạch nguyên vật liệu, để ráo nước hoàn toàn, tiến hành cắt thái\n" +
                        "- Gừng cắt sợi nhỏ vừa ăn\n" +
                        "- Thịt vịt rã đông ở nhiệt độ mát, rửa sạch, ướp với xốt kho tộ khoảng 30 phút",
                "foods/foodrecipe8_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Chuẩn bị chảo cho thịt vịt đã ướp, gừng và 200ml nước vào kho với lửa vừa, khoảng 20 phút.\n" +
                        "Nêm nếm lại cho vừa ăn rồi tắt bếp.",
                "foods/foodrecipe8_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Ăn kèm với cơm trắng",
                "foods/foodrecipe8_3.png"
            )
        )

        var mon_an_8=
            FoodRecipe(8,"Vịt Kho Gừng","foods/foodrecipe8_3.png",2,"Dưới 30 phút", Date(),true,
                arrayListOf(1,4,6),listStep,listIngredient,
                arrayListOf(11,12,13),
                arrayListOf(6),1)
        result.add(mon_an_8)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200.5,"Thịt bò","g",100.0))
        listIngredient.add(RecipeIngredient(300.0,"Bông cải xanh","g",50.0))
        listIngredient.add(RecipeIngredient(1.0,"Dầu hào","g",100.0))
        listIngredient.add(RecipeIngredient(5.5,"Bột bắp","g",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Thịt bò ướp 1g dầu hào, tỏi băm, 5g bột bắp. Phi tỏi thơm xào chín\n",
                "foods/foodrecipe9_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Bông cải hấp chín, xào sơ hoặc chứ thế thêm vào đĩa với bò xào, rắc xíu rong biển.\n",
                "foods/foodrecipe9_2.png"
            )
        )

        var mon_an_9=
            FoodRecipe(9,"Cơm Bò Xào Bông Cải","foods/foodrecipe9_2.png",2,"Dưới 30 phút", Date(),true,
                arrayListOf(1,4,6),listStep,listIngredient,
                arrayListOf(),
                arrayListOf(14,15),0)
        result.add(mon_an_9)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(500.0,"Bò vụn","g",50.0))
        listIngredient.add(RecipeIngredient(500.0,"Cải chua","g",100.0))
        listIngredient.add(RecipeIngredient(2.0,"Cà chua","trái",150.0))
        listIngredient.add(RecipeIngredient(1.0,"Hành lá","tép",120.0))
        listIngredient.add(RecipeIngredient(1.0,"Ngò gai","tép",150.0))
        listIngredient.add(RecipeIngredient(3.0,"Tỏi băm","mcf",20.0))
        listIngredient.add(RecipeIngredient(1.0,"Nước lọc","lít",40.0))
        listIngredient.add(RecipeIngredient(5.0,"Dầu ăn","g",50.0))


        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Bò xắt miếng vừa ăn.\nƯớp bò với tỏi băm, hạt nêm. Bọc kín 30 phút trong tủ mát",
                "foods/foodrecipe10_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Dưa cải chua rửa qua, nếu chua quá thì nên trụng sơ rồi để ráo nước.",
                "foods/foodrecipe10_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Bắc chảo phi tổ thơm cho bò vào xào săn.\nThêm nước hoặc nước dừa nấu sôi. Tắt bếp để đó. Nguội thì nấu sôi lại lần nữa rồi lại tắt bếp để đó.",
                "foods/foodrecipe10_3.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Sau cùng thêm dưa cải nấu mềm.\nNêm lại vừa ăn, thêm cà chua rau thơm.",
                "foods/foodrecipe10_4.png"
            )
        )

        var mon_an_10= FoodRecipe(10,"Canh Bò Nấu Dưa Cải Chua","foods/foodrecipe10_4.png",4,"Dưới 1 tiếng",
            Date(),true,
            arrayListOf(1,4,6),listStep,listIngredient,
            arrayListOf(14,15),
            arrayListOf(),0)
        result.add(mon_an_10)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(20.0,"Rong chân vịt hoặc rong sụn khô (khoảng 10 cọng rong)","g",50.0))
        listIngredient.add(RecipeIngredient(100.0,"Đường phèn","g",100.0))
        listIngredient.add(RecipeIngredient(5.0,"Táo đỏ","quả",150.0))
        listIngredient.add(RecipeIngredient(1.0,"Gừng bằng 1 đốt ngón tay cái","nhánh",120.0))
        listIngredient.add(RecipeIngredient(3.0,"Lá dứa","",150.0))


        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Rong rửa sơ, ngâm với nước dấm hoặc chanh pha loãng 1 tiếng cho nở. Cần phải ngâm với chanh hoặc dấm để rong bớt tanh nha.\n" +
                        "Rong nở xong nhặt sạch sạn và rửa sạch, để ráo",
                "foods/foodrecipe11_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho 3 lít nước vào nồi cùng táo đỏ, gừng cắt lát, 03 lá dứa buộc chặt (nếu có). Đun sôi nước rồi thả rong vào. Cho nước sôi lại rồi để liu riu 15 phút cho rong tan mềm. Lúc này bạn kiểm tra nhen, nếu thấy nước rong đặc thì thêm nước (nấu đặc sẽ thành thạch, phải ăn kèm thứ khác mới ngon nha).",
                "foods/foodrecipe11_2.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Vớt lá dứa, cho đường phèn vào nước rong, chỉ hơi man mát thôi nha rồi khuấy đều. Nếu không có lá dứa bạn cho 2 thìa cf tinh chất vani rồi tắt bếp. Đây là rong vừa nấu xong, vẫn còn chưa mềm hết, phần rong trắng là vẫn còn sần sật đó.",
                "foods/foodrecipe11_3.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Đây là nước rong để nguội, rong trong suốt và mềm như yến. Bạn bỏ vào chai và cất tủ lạnh uống trong 1, 2 ngày nha! Rong khi nguội chỉ loãng như vậy thôi nha!",
                "foods/foodrecipe11_4.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Tips: Nếu khi để nguội mà nước rong của bạn đông lại thì bạn nấu sôi lại và chế thêm nước, đường cho vừa khẩu vị của bạn nhen!\n" +
                        "Nếu loãng thì không phải chỉnh ạ!",
                "foods/foodrecipe11_5.png"
            )
        )

        var mon_an_11= FoodRecipe(11,"Nước uống từ rong biển","foods/foodrecipe11_5.png",1,"Trên 1 tiếng",
            Date(),true,
            arrayListOf(1,4),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_11)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(0.0,"Bột rau câu","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Cốt dừa","",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Crystal light vị chanh","",150.0))
        listIngredient.add(RecipeIngredient(0.0,"Bột khoai tím","",120.0))
        listIngredient.add(RecipeIngredient(0.0,"Tắc","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Trà","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Đá","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Lá bạc hà (hoặc húng lủi)","",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Đường","",150.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Trước tiên, dùng 2 khuôn chữ nhật hoặc vuông đổ thạch rau câu màu trắng và vàng (đây là phần bún và đậu - mình dùng cốt dừa và bột crystal light để pha màu)\n" +
                        "Lấy 1 ly, cho bột khoai tím và bột rau câu dẻo vào, làm rau câu tím ở phần đáy ly (đây sẽ là mắm tôm)\n" +
                        "Vắt tiếp 2 trái tắc vào ly",
                "foods/foodrecipe12_1.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Cho nước đường, và trà vào ly",
                "foods/foodrecipe12_2.png"
            )
        )

        listStep.add(
            RecipeCookStep("Cho đá viên vào. Rồi bây giờ bắt đầu xếp rau câu vàng và trắng vào ly"
                ,"foods/foodrecipe12_3.png"
            )
        )

        listStep.add(
            RecipeCookStep(
                "Thêm 1 lát tắc, 1 ít húng lủi hoặc bạc hà.\n" +
                        "Thế là đã xong 1 ly bún đậu mắm tôm ^^"
                ,"foods/foodrecipe12_4.png"
            )
        )

        var mon_an_12 = FoodRecipe(12,"Bún đậu mắm tôm phiên bản nước uống","foods/foodrecipe12_4.png",1,"Dưới 15 phút",
            Date(),true,
            arrayListOf(2,4,6),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_12)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(300.0,"Lá tía tô","g",50.0))
        listIngredient.add(RecipeIngredient(2.0,"Nước","lít",100.0))
        listIngredient.add(RecipeIngredient(2.0,"Thìa ăn phở giấm (hoặc nước cốt chanh)","",150.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Tía tô nhặt lấy lá, bỏ cành. Rửa sạch. Nếu không rõ nguồn gốc mua thì ngâm với nước muối loãng.",
                "foods/foodrecipe13_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cho lá tía tô và nước vào nồi, đun sôi khoảng 5 phút. Lá tía tô chuyển từ đỏ sang xanh thì vớt lá ra.",
                "foods/foodrecipe13_2.png"
            )
        )
        listStep.add(
            RecipeCookStep("Cho thêm đường vào nồi nước luộc tía tô, độ ngọt vừa khẩu vị. Đun và khuấy cho tan đường."
                ,"foods/foodrecipe13_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Tắt bếp, cho chút giấm (nước cốt chanh) vào khuấy đều, nếm độ chua vừa khẩu vị. Nhờ chất chua, màu nước lá tía tô chuyển sang hồng đẹp."
                ,"foods/foodrecipe13_4.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Rót nước tía tô lọc qua rây cho sạch cặn lá sót lại. Để nguội thì cho vào bình kín, cất ngăn mát."
                ,"foods/foodrecipe13_5.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Khi uống, pha loãng hoặc thêm đá/đường hoặc pha với rượu/soda theo khẩu vị người uống."
                ,"foods/foodrecipe13_6.png"
            )
        )

        var mon_an_13 = FoodRecipe(13,"Nước uống từ lá tía tô","foods/foodrecipe13_6.png",1,"Dưới 30 phút",
            Date(),true,
            arrayListOf(1,4,6),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_13)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(4.0,"Chanh","quả",50.0))
        listIngredient.add(RecipeIngredient(3.0,"Sả","nhánh",100.0))
        listIngredient.add(RecipeIngredient(50.0,"Gừng","g",150.0))
        listIngredient.add(RecipeIngredient(15.0,"Tỏi","g",50.0))
        listIngredient.add(RecipeIngredient(2.0,"Nước","lít",100.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Chanh vắt lấy nước (giữ lại phần vỏ), gừng thái mỏng, cho nước cốt chanh, gừng, tỏi vào máy sinh tố Electrolux xay mịn.",
                "foods/foodrecipe14_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Sả đập dập cho vào nồi, cùng với vỏ chanh, và 2 lít nước nấu sôi đúng 1 tiếng. - Nấu vỏ chanh và sả sôi, để lửa nhỏ và đậy nắp. Nên nước cũng không hao hụt bao nhiêu. Sau khi nấu vỏ chanh và sả được 1 tiếng, cho hỗn hợp đã xay mịn ở bước 1 vào nấu sôi lại tắc bếp, rồi lượt vào chai, để nguội cho vào tủ lạnh",
                "foods/foodrecipe14_2.png"
            )
        )
        listStep.add(
            RecipeCookStep("Mới uống nên uống ly nhỏ, từ từ tăng dần"
                ,"foods/foodrecipe14_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Chia hỗn hợp nước thanh lọc đã nấu ra làm 3 phần.\n" +
                        "Sáng ngủ dậy uống 1 phần, rồi 1 tiếng sau đó mới được ăn sáng.\n" +
                        "Trưa và Chiều cũng uống trước bữa ăn 1 tiếng.\n" +
                        "Chúc cả nhà thành công."
                ,"foods/foodrecipe14_4.png"
            )
        )

        var mon_an_14 = FoodRecipe(14,"Nước Uống Thanh Lọc Giảm Mỡ","foods/foodrecipe14_4.png",2,"Dưới 30 phút",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_14)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(0.0,"Nước cam (bán sẵn)","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Đào ngâm (bán sẵn)","",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Thạch cam (bán sẵn)","",150.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Cho đào và thạch cam vào cốc", "foods/foodrecipe15_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Đổ nước cam vào, thêm đá nếu muốn uống lạnh. Cùng thưởng thức thôi",
                "foods/foodrecipe15_2.png"
            )
        )

        var mon_an_15 = FoodRecipe(15,"Nước cam giải khát mùa hè","foods/foodrecipe15_2.png",2,"Dưới 15 phút",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_15)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(3.0,"Khía muối","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Tỏi","",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Ớt","",150.0))
        listIngredient.add(RecipeIngredient(0.0,"Đường","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Chanh","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Cà chua","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Đậu đũa","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Đu đủ già","",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Sử dụng ba khía muối thì mọi người trụng lại với nước sôi để làm giảm độ mặn. Mình sử dụng ba khía loại ướp sẵn tỏi ớt cũng rất thơm ngon",
                "foods/foodrecipe16_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cà chua bỏ hạt, cắt nhỏ. Đậu đũa để sống hoặc luộc chín rồi ngâm nước đá lạnh. Đu đủ già bỏ vỏ, cắt thành các khúc rồi ngâm nước muối cho bớt mủ, sau đó cắt lát mỏng/bào sợi rồi ngâm nước đá cho giòn.",
                null
            )
        )
        listStep.add(
            RecipeCookStep(
                "Ba khía đập dập sơ, cho các nguyên liệu vào trong hộp to, trộn đều. Thêm đường, nước cốt chanh, ớt tỏi tùy khẩu vị. Để khoảng 15-20 phút cho các nguyên liệu thấm gia vị. Đu đủ, đậu đũa giòn sừn sựt, ba khía mặn mặn chua chua cay cay, ăn với cơm trắng nóng hoặc bún tươi bao ngon luôn ạ. Bỏ tủ lạnh ăn dần vẫn ngon ạ ^^"
                ,"foods/foodrecipe16_2.png"
            )
        )

        var mon_an_16= FoodRecipe(16,"Gỏi ba khía","foods/foodrecipe16_2.png",4,"Dưới 45 phút",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_16)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(300.0,"giò sống","g",50.0))
        listIngredient.add(RecipeIngredient(100.0,"cà rốt thái sợi","g",100.0))
        listIngredient.add(RecipeIngredient(100.0,"đậu quả","g",150.0))
        listIngredient.add(RecipeIngredient(0.0,"Gia vị: tiêu; nước mắm; muối","",50.0))
        listIngredient.add(RecipeIngredient(50.0,"Mộc nhĩ","g",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep(
                "Giò sống ướp với1/2 thìa cafe muối ; 1 thìa canh nước mắm; 1/2 thìa cafe tiêu. Cà rốt thái sợi trần sơ qua nước sôi cùng đậu quả (vớt ra ngâm nước lạnh) mộc nhĩ, váng đậu ngâm nước ấm cho nở rồi rửa sạch.",null
            )
        )
        listStep.add(
            RecipeCookStep(
                "Trải 1 lớp giấy bạc bằng với lớp váng đậu rồi phết 1 lớp giò sống lên dàn đều cho cà rốt đều lên trên. Sau đó trải 1 lớp mộc nhĩ phết giò sống lên rồi 1 lớp đậu que cuối cùng là 1 lớp giò rồi cuộn tròn lại nhớ cuộn chặt tay nếu ko giò sẽ dễ bị bung ra khi hấp. Và không được đẹp.",null
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cố định giò bằng cách xoắn chặt 2 đầu giấy bạc. Đem hấp cách thủy 15-20p",null
            )
        )
        listStep.add(
            RecipeCookStep(
                "Sau đó lấy ra để nguội rồi thái thành từng khoanh và thưởng thức. Nuớc chấm có thể chấm với nước tương; nước mắm chua ngọt; tương ớt, sốt mayonnaise (tùy sở thích mỗi người)"
                ,"foods/foodrecipe17_1.png"
            )
        )

        var mon_an_17= FoodRecipe(17,"Giò hoa khai vị","foods/foodrecipe17_1.png",4,"Dưới 45 phút",
            Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_17)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(2.0,"khổ qua","trái",50.0))
        listIngredient.add(RecipeIngredient(0.0,"Tôm thẻ","",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Rau thơm","",150.0))
        listIngredient.add(RecipeIngredient(2.0,"Chanh","trái",50.0))
        listIngredient.add(RecipeIngredient(0.0,"tỏi","",50.0))
        listIngredient.add(RecipeIngredient(0.0,"ớt","",50.0))
        listIngredient.add(RecipeIngredient(2.0,"đường","mc",50.0))
        listIngredient.add(RecipeIngredient(1.0,"nước mắm","mc",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep("Khổ qua bỏ ruột bỏ luôn phần trắng bào mỏng rồi ngâm trong nước đá khoảng 2 h cho trong. tôm thẻ lột vỏ (mình làm tôm thủy tinh ăn cho giòn). đem luộc tôm chín để nguội. nặn 2 trái chanh, thêm 1/2 mc nước mắm, 2mc đường quậy đều bằm tỏi ớt nhuyển, chia 2 một phần trộn vào tôm, phần kia trộn vào khổ qua"
                , "foods/foodrecipe18_1.png"))
        listStep.add(
            RecipeCookStep("Cho khổ qua ra dĩa cho rau thơm tiếp đến cho tôm lên có thể cho mè rang hay đậu phộng lên.",
                "foods/foodrecipe18_2.png"
            )
        )

        var mon_an_18= FoodRecipe(18,"Gỏi khổ qua","foods/foodrecipe18_2.png",3,"Trên 1 tiếng",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_18)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(2.0,"mướp đắng","quả",50.0))
        listIngredient.add(RecipeIngredient(2.0,"gừng nhỏ","miếng",100.0))
        listIngredient.add(RecipeIngredient(1.0,"xì dầu","muỗng canh",150.0))
        listIngredient.add(RecipeIngredient(1.0,"dầu ô liu","muỗng canh",50.0))
        listIngredient.add(RecipeIngredient(1.0,"dầu mè","muỗng cà phê",50.0))
        listIngredient.add(RecipeIngredient(1.0,"đường (tuỳ ý)","muỗng cà phê",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep("Rửa sạch mướp đắng, cắt đôi, nạo hạt ra. Thái lát mỏng. Gừng gọt vỏ, thái sợi, cắt nhỏ.", "foods/foodrecipe19_1.png"))
        listStep.add(
            RecipeCookStep("Cho xì dầu, dầu mè, dầu ô liu, xíu đường (có thể không cho đường nếu muốn).",
                "foods/foodrecipe19_2.png"
            )
        )
        listStep.add(
            RecipeCookStep("Trộn đều và măm măm thôi!",
                "foods/foodrecipe19_3.png"
            )
        )

        var mon_an_19= FoodRecipe(19,"Mướp đắng trộn gừng","foods/foodrecipe19_3.png",2,"Dưới 15 phút",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_19)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(100.0,"bạc hà","g",50.0))
        listIngredient.add(RecipeIngredient(20.0,"tôm đất hấp chín","g",100.0))
        listIngredient.add(RecipeIngredient(0.0,"Rau thơm: húng quế, húng cay,…","g",150.0))
        listIngredient.add(RecipeIngredient(1.0,"Mè rang","mcf",50.0))
        listIngredient.add(RecipeIngredient(1.0,"Hành phi","mcf",50.0))
        listIngredient.add(RecipeIngredient(2.0,"Nước mắm trộn gỏi","muỗng canh",50.0))

        listStep=ArrayList<RecipeCookStep>()

        listStep.add(
            RecipeCookStep("Loại rau này có người bóp muối sơ rồi trộn còn mình thích trụng sơ. Sau đó vắt ráo.",
                "foods/foodrecipe20_1.png"
            )
        )
        listStep.add(
            RecipeCookStep("Thêm các nguyên liệu rau thơm tôm nước mắm, hành phi, mè rang là ăn được.",
                "foods/foodrecipe20_2.png"
            )
        )

        var mon_an_20 = FoodRecipe(20,"Gỏi Bạc Hà Tôm Đất","foods/foodrecipe20_2.png",2,"Dưới 30 phút",
            Date(),true,
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_20)

        var mon_an_21 = FoodRecipe(21,"Bữa sáng với trứng trần siêu ngon","foods/foodrecipe36_5.png", 1, "Dưới 15 phút",
            Date(),true,arrayListOf(1, 5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_21)


        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(2.0, "ngũ cốc Granola", "muỗng", 30.0))
        listIngredient.add(  RecipeIngredient(1.0, "sữa chua không đường", "hộp",40.0))
        listIngredient.add(  RecipeIngredient(2.0, "mật ong", "muỗng", 15.0))
        listIngredient.add(  RecipeIngredient(1.0, "chuối", "quả", 40.0))


        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Cho sữa chua vào bát. Sau đó, cho 2 muỗng ngũ cốc vào thêm",
                "foods/foodrecipe37_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cắt chuối vừa ăn cho vào đĩa. Sau đó rưới thêm mật ong và thưởng thức. Bữa sáng tốt cho sức khỏe đơn giản mà dễ làm. Chúc cả nhà ăn ngon miệng ạ",
                "foods/foodrecipe37_2.png"
            )
        )

        var mon_an_22 = FoodRecipe(22,"Ngũ cốc Granola mix sữa chua bữa sáng giảm cân","foods/foodrecipe37_2.png", 1, "Dưới 15 phút",
            Date(),true,arrayListOf(1, 5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_22)


        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(2.0, "sandwich ngũ cốc", "lát", 50.0))
        listIngredient.add(  RecipeIngredient(1.0, "bơ lạc", "thỏi",20.0))
        listIngredient.add(  RecipeIngredient(2.0, "chuối", "quả", 30.0))
        listIngredient.add(  RecipeIngredient(1.0, "bơ", "quả", 40.0))


        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Nướng sandwich lại cho giòn. Sau đó cắt bơ, chuối cho ra đĩa cùng nửa viên bơ lạt. Thưởng thức thôi nào",
                "foods/foodrecipe38_1.png"
            )
        )

        var mon_an_23 = FoodRecipe(23,"Sandwich ngũ cốc + Bơ - chuối","foods/foodrecipe38_1.png", 2, "Dưới 15 phút",
            Date(),true,arrayListOf(1, 5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_23)

        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(2.0, "bacon", "lát", 50.0))
        listIngredient.add(  RecipeIngredient(1.0, "trứng gà", "trái",15.0))
        listIngredient.add(  RecipeIngredient(4.0, "măng Tây", "cây", 30.0))
        listIngredient.add(  RecipeIngredient(3.0, "cà chua bi", "trái", 40.0))
        listIngredient.add(  RecipeIngredient(0.5, "trái dưa chuột", "trái", 20.0))



        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Áp chảo bacon trước, để mỡ từ bacon chảy ra, dùng để áp chảo măng tây nha mọi người.",
                "foods/foodrecipe39_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Dưa leo cắt nhỏ, và bày các nguyên liệu ra đĩa và thưởng thức ạ",
                "foods/foodrecipe39_2.png"
            )
        )

        var mon_an_24 = FoodRecipe(24,"Sandwich ngũ cốc + Bơ - chuối","foods/foodrecipe39_2.png", 2, "Dưới 15 phút",
            Date(),true,arrayListOf(1, 5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_24)


        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(1.0, "thanh long trắng", "trái", 50.0))
        listIngredient.add(  RecipeIngredient(1.0, "ngũ cốc", "chén",25.0))
        listIngredient.add(  RecipeIngredient(200.0, "sữa tươi không đường", "ml", 80.0))
        listIngredient.add(  RecipeIngredient(3.0, "cà chua bi", "trái", 40.0))
        listIngredient.add(  RecipeIngredient(4.0, "hạnh nhân", "hạt", 20.0))

        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Thanh long cắt ô cờ, cho vào tô cùng ngũ cốc",
                "foods/foodrecipe40_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Đổ sữa tươi vào, và trang trí bằng vài hạt hạnh nhân trên top.\n" +
                        "Lưu ý món này sẽ ngon hơn khi sữa và thanh long đều lạnh nghen. Ăn sáng sảng khoải lắm luôn ạ",
                "foods/foodrecipe40_2.png"
            )
        )

        var mon_an_25 = FoodRecipe(25," Bữa Sáng Ngũ Cốc, Sữa Tươi, Thanh Long ","foods/foodrecipe40_2.png", 2, "Dưới 15 phút",
            Date(),true,arrayListOf(1, 5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_25)

        // Snack - tgt
        // 26
        listStep = arrayListOf()
        listStep.add(RecipeCookStep("Thịt bò thái thành miếng rộng 5cm – dài 4cm – dày 0,3cm ngang thớ.", "foods/foodrecipe26_1.png"))
        listStep.add(RecipeCookStep("Cho tất cả các gia vị nước sốt ở trên vào một cái bát và trộn đều thành hỗn hợp nước sốt gia vị.", "foods/foodrecipe26_2.png"))
        listStep.add(RecipeCookStep("Cho thịt bò, hành lá vào và trộn đều cho thịt bò ngấm đều nước sốt gia vị. Ướp thịt bò ít nhất là 1 giờ.", "foods/foodrecipe26_3.png"))
        listStep.add(RecipeCookStep("Nướng thịt bò ở nhiệt độ cao vừa phải. Nếu lửa quá lớn, thịt bò sẽ bị cháy vì trong nước ướp thịt có đường. Nướng thịt bò khoảng 2 phút mỗi bên là được", "foods/foodrecipe26_4.png"))

        listIngredient = arrayListOf()
        listIngredient.add(RecipeIngredient(450.0,"Thịt bò", "g", 118.5))
        listIngredient.add(RecipeIngredient(1.0, "Hành lá", "cây", 10.0))
        listIngredient.add(RecipeIngredient(5.0, "Nước tương", "muỗng canh", 29.0))
        listIngredient.add(RecipeIngredient(3.0, "Đường trắng", "muỗng canh", 99.0))
        listIngredient.add(RecipeIngredient(2.0, "Rượu trắng", "muỗng canh", 123.0))
        listIngredient.add(RecipeIngredient(4.0, "Nước ép thơm", "muỗng canh", 120.0))
        listIngredient.add(RecipeIngredient(1.0, "Tỏi băm", "muỗng canh", 50.0))
        listIngredient.add(RecipeIngredient(1.0, "Gừng băm", "muỗng cà phê", 60.0))
        listIngredient.add(RecipeIngredient(2.0, "Dầu mè", "muỗng canh", 20.0))
        listIngredient.add(RecipeIngredient(2.0, "Mè trắng", "muỗng cà phê", 10.0))


        var mon_an_26 = FoodRecipe(26,"Bò Nướng Kiểu Hàn Quốc","foods/foodrecipe26.png",4,"Dưới 45 phút",
            Date(),true,
            arrayListOf(2,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_26)

        // 27
        listStep = arrayListOf()
        listStep.add(RecipeCookStep("1. Đổ sứa ra tô lớn\n2. Rửa qua 2-3 lần nước rồi để ráo", "foods/foodrecipe27_1.png"))
        listStep.add(RecipeCookStep("Cho đường, dầu mè vào trộn đều, để khoảng 3 phút để ngấm gia vị", "foods/foodrecipe27_2.png"))
        listStep.add(RecipeCookStep("1. Cho wasabi và xì dầu sau đó trộn đều rồi tiếp tục để khoảng 3 phút.\n2. Sau 3 phút thì ta có thể thưởng thức", "foods/foodrecipe27_3.png"))

        listIngredient = arrayListOf()
        listIngredient.add(RecipeIngredient(250.0, "Sứa", "g", 150.0))
        listIngredient.add(RecipeIngredient(1.0, "Wasabi", "muỗng cà phê", 20.0))
        listIngredient.add(RecipeIngredient(1.0, "Xì dầu", "muỗng canh", 50.0))
        listIngredient.add(RecipeIngredient(1.0, "Đường", "muỗng cà phê", 80.0))
        listIngredient.add(RecipeIngredient(1.0, "Dầu mè", "muỗng cà phê", 40.0))

        var mon_an_27 = FoodRecipe(27,"Sứa Trộn Wasabi","foods/foodrecipe27.png",1,"Dưới 15 phút",
            Date(),true,
            arrayListOf(2,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_27)

        // 28
        listStep = arrayListOf()
        listStep.add(RecipeCookStep("1. Nướng 2 quả ớt ngọt trong nckd ở 150C trong 20 phút.\n 2. Bóc vỏ của quả ớt ra bỏ đi, xé nhỏ, bỏ núm và hạt.", "foods/foodrecipe28_1.png"))
        listStep.add(RecipeCookStep("Cho bột thì là, bột tiểu hồi vào máy xay và xay đến khi thật nhuyễn.", "foods/foodrecipe28_2.png"))
        listStep.add(RecipeCookStep("1. Cho muối và đường vào máy. \n2. Bổ đôi quả chanh, vắt nước cốt vào máy xay.\n3. Cho hết ớt ngọt vào vừa nướng vào máy", "foods/foodrecipe28_3.png"))
        listStep.add(RecipeCookStep("Cho tiếp dầu điều hoặc dầu ô liu, và bột hạnh nhân. Xay nhuyễn tất cả là xong. Ở đây mình không dùng máy xay sinh tố mà dùng máy xay tay cho nhanh gọn", "foods/foodrecipe28_4.png"))
        listStep.add(RecipeCookStep("Có thể dùng nước sốt này để chấm bất kỳ thứ gì bạn muốn nhe, ví dụ như món bánh mì que đính kèm! Sốt này có thể để tủ lạnh trong hũ kín 1 tuần vẫn ngon", "foods/foodrecipe28_5.png"))

        listIngredient = arrayListOf()
        listIngredient.add(RecipeIngredient(2.0, "Ớt ngọt (màu đỏ, vàng, cam)", "quả", 150.0))
        listIngredient.add(RecipeIngredient(1.0, "Muối", "muỗng cà phê", 20.0))
        listIngredient.add(RecipeIngredient(1.0, "Đường nâu", "muỗng canh", 150.0))
        listIngredient.add(RecipeIngredient(1.0, "Bột thì là", "muỗng canh", 10.0))
        listIngredient.add(RecipeIngredient(1.0, "Bột tiểu hồi", "muỗng canh", 30.0))
        listIngredient.add(RecipeIngredient(4.0, "Dầu điều (hoặc dầu ô liu)", "muỗng canh", 100.0))
        listIngredient.add(RecipeIngredient(5.0, "Bột hạnh nhân", "muỗng canh", 30.0))
        listIngredient.add(RecipeIngredient(1.0, "Chanh tươi", "quả", 10.0))
        listIngredient.add(RecipeIngredient(2.0, "Bánh mì", "ổ", 150.0))

        var mon_an_28 = FoodRecipe(28,"Bánh Mì Chấm Sốt Ớt Ngọt Đà Lạt","foods/foodrecipe28.png",3,"Dưới 45 phút",
            Date(),true,
            arrayListOf(2,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_28)

        // 29
        listStep = arrayListOf()
        listStep.add(RecipeCookStep("Xay 1 cup yến mạch thành bột mịn, nếu bạn có sẵn bột yến mạch thì khỏi xay.", "foods/foodrecipe29_1.png"))
        listStep.add(RecipeCookStep("Cho vào thố với chút muối, không muối cũng không sao cả chỉ để chúng thêm mặn mà thôi.","foods/foodrecipe29_2.png"))
        listStep.add(RecipeCookStep("Nấu sôi nước, cho vào tô bột yến mạch trộn 2 phút đến khi không dính và bột trở nên dày hơn", "foods/foodrecipe29_3.png"))
        listStep.add(RecipeCookStep("Nhồi mịn, khi nguội bớt thì cho ra bàn có phủ bột áo ngắt thành 5 viên.", "foods/foodrecipe29_4.png"))
        listStep.add(RecipeCookStep("Thêm ít bột áo cán dẹp từng viên bột, dùng 1 cái chén để cắt cho tròn hoặc để hình con amip cũng được nốt.", "foods/foodrecipe29_5.png"))
        listStep.add(RecipeCookStep("Nướng khô trên một chảo nóng hai mặt, nó sẽ phồng lên như cái gối. Hãy lật qua lại để bánh chín và vàng đều. 1 bánh nướng chừng 3 phút là xong", "foods/foodrecipe29_6.png"))
        listStep.add(RecipeCookStep("Bánh vàng cạnh thì lấy ra khỏi chảo, hoàn tất và lặp lại quá trình với những bánh khác.\n" +
                "Khi nguội bánh roti yến mạch sẽ xẹp và mềm dẻo hơn, sẵn sàng để dùng kèm các món Ấn có độ sệt như cà ri, masala…", "foods/foodrecipe29_7.png"))

        listIngredient = arrayListOf()
        listIngredient.add(RecipeIngredient(1.0, "Bột yến mạch", "cup", 100.0))
        listIngredient.add(RecipeIngredient(1.0, "Bột yến mạch áo", "cup", 80.0))
        listIngredient.add(RecipeIngredient(1.0, "Nước sôi", "cup", 0.0))
        listIngredient.add(RecipeIngredient(1.0, "Muối", "muỗng cà phê", 10.0))

        var mon_an_29 = FoodRecipe(29,"Bánh Roti yến mạch","foods/foodrecipe29.png",4,"Dưới 30 phút",
            Date(),true,
            arrayListOf(2,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_29)

        // 30
        listStep = arrayListOf()
        listStep.add(RecipeCookStep("Lấy dao cắt bỏ phần rìa vàng của sandwich, lấy cây cà mỏng ra, xúc xích cắt khúc vừa cuộn", "foods/foodrecipe30_1.png"))
        listStep.add(RecipeCookStep("Cuộn xúc xích lại như này","foods/foodrecipe30_2.png"))
        listStep.add(RecipeCookStep("Trứng gà đánh đều ra, sau đó nhúng vào trứng lăn qua bột xù", "foods/foodrecipe30_3.png"))
        listStep.add(RecipeCookStep("Dầu bật sôi bỏ vào chiên vàng đều", "foods/foodrecipe30_4.png"))
        listStep.add(RecipeCookStep("Chấm tương ớt cay cay giòn thơm béo cực", "foods/foodrecipe30_5.png"))

        listIngredient = arrayListOf()
        listIngredient.add(RecipeIngredient(2.0, "Xúc xích", "cây", 130.0))
        listIngredient.add(RecipeIngredient(3.0, "Sandwich", "miếng", 100.0))
        listIngredient.add(RecipeIngredient(1.0, "Trứng gà", "quả", 50.0))
        listIngredient.add(RecipeIngredient(1.0, "Bột chiên xù", "bịch", 70.0))

        var mon_an_30 = FoodRecipe(30,"Xúc Xích Cuộn Sandwich Chiên xù","foods/foodrecipe30.png",2,"Dưới 30 phút",
            Date(),true,
            arrayListOf(2,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_30)

        // món tráng miệng
        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(5.0,"Lá dứa", "cái", 50.0))
        listIngredient.add(RecipeIngredient(300.0,"Đậu xanh", "g", 300.0))

        listStep=ArrayList<RecipeCookStep>()
        listStep.add(
            RecipeCookStep(
                "Đậu xanh vo sạch, ngâm nước nóng 30p.\n" +
                        "Lá dứa mình chuẩn bị tầm 5 lá.",
                "foods/foodrecipe31_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Bắt nươc nấu sôi, cho đậu xanh vào nấu, nấu sôi vớt bọt tầm 10p.\n" +
                        "Cho thêm lá dứa vào, lại nấu sôi vớt bọt.",
                "foods/foodrecipe31_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Nấu sôi, b hạ lửa nhỏ đậy nắp, nấu thêm 20p cho đậu nở bung ra.\n" +
                        "Sau khi đậu nở bung, mềm, vớt bỏ lá dứa.",
                "foods/foodrecipe31_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Lúc này sẽ nêm đường, đường nâu ít ngọt, tùy theo loại đường mà bạn dùng.\n" +
                        "Đường của mình ở đây là 2 vá. Tắt lửa, khuấy cho đường tan. Đậy nắp thêm 5p => hoàn thành.",
                "foods/foodrecipe31_4.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Món này có thể dùng nóng, ướp lạnh, cho đá, cách nào cũng ngon. Bảo quản ngăn mat tủ lạnh trong 3 ngày :)",
                "food/foodrecipe31_5.png"
            )
        )

        var mon_an_31 = FoodRecipe(31,"Chè đậu xanh lá dữa","foods/foodrecipe31_5.png", 2, "Dưới 30 phút",
            Date(),true,arrayListOf(3,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_31)


        listIngredient= ArrayList()
        listIngredient.add(RecipeIngredient(200.0,"Bột nếp", "g", 100.0))
        listIngredient.add(RecipeIngredient(300.0,"Mật mía", "ml", 300.0))

        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Bột năng trộn bột nếp, chia làm 3 phần. Đun sôi nước. Vừa đổ vừa quấy 1 phần sao cho bột sệt sệt dạng sữa chua. Đổ tiếp 1 phần vào trộn đều (tạo thành 1 hỗn hợp lợn cợn như cơm nát, nếu khô quá có thể thêm chút nước) Để bột nghỉ 30 phút. Thêm nốt chỗ bột còn lại vào (chừa lại 1 ít để áo bột) tùy độ hút nước của bột mà điều chỉnh (khi làm nên dự trù thêm bột ở ngoài) nặn to nhỏ, dài dẹp tùy thích. Khi nặn xong áo qua 1 lớp bột (bước này có thể làm xong cất tủ đông ăn dần)",
                "foods/foodrecipe32_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Đun 1 nồi nước thật sôi, thả bánh, hạ lửa trung bình nhỏ đun đến khi bánh nổi vớt ra thả ngay vào 1 bát nước lạnh",
                "foods/foodrecipe32_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Gừng thái sợi, mật mía và 1 ít nước đun sôi. Thả bánh vào đun tiếp đến khi nước sệt sệt là được",
                "foods/foodrecipe32_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Bánh ngon nhất khi thưởng thức nóng.",
                "foods/foodrecipe32_4.png"
            )
        )

        var mon_an_32 = FoodRecipe(32,"Bánh nếp mía","foods/foodrecipe32_4.png", 2, "Dưới 2 tiếng",
            Date(),true,arrayListOf(3,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_32)


        listIngredient= ArrayList()
        listIngredient.add(RecipeIngredient(200.0,"Bột mỳ", "g", 100.0))
        listIngredient.add(RecipeIngredient(300.0,"Sữa tươi", "ml", 300.0))
        listIngredient.add(RecipeIngredient(160.0,"Đường", "g", 50.0))

        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Lấy ra 1mcf đường và 30g sữa tươi cho vào men ủ 30 phút sau đó cho vào thau bột cùng đường nước lá cẩm sữa tươi quậy đều ủ 1 h cho vào 2mc dầu ăn ủ thêm 15 phút. bắc xửng nước thật sôi cho bột vào đầy khuôn haps với lửa lớn. mình hấp bằng chén lớn nhưng bị đụng nắp nên chuyển sang hộp nhỏ",
                "foods/foodrecipe33_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Ghim bánh không dính là bánh chín lấy ra",
                "foods/foodrecipe33_2.png"
            )
        )

        var mon_an_33 = FoodRecipe(33,"Bánh bò bông","foods/foodrecipe33_2.png", 2, "Dưới 2 tiếng",
            Date(),true,arrayListOf(3,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_33)


        listIngredient= ArrayList()
        listIngredient.add( RecipeIngredient(1.0, "chùm ruột", "kg",100.0))
        listIngredient.add( RecipeIngredient(700.0, "đường vàng", "g", 100.0))

        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Chùm ruột rửa sạch, để ráo, cho vô bịch nilong, bỏ vô ngăn đông tủ lạnh 1 ngày. Sau 1 ngày lấy ra rả đông, cho từng ít một khăn xô vắt ráo nước.",
                "foods/foodrecipe34_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cho đường vô chùm ruột trộn đều, để ướp 5 tiếng cho tan hết đường.",
                "foods/foodrecipe34_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Đổ chùm ruột ngâm đường vô chảo,bắc lên bếp rim lửa to cho đến khi chùm ruột bắt đầu chuyển màu thì hạ nhỏ lửa.",
                "foods/foodrecipe34_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Tiếp tục rim chùm ruột đến khi đường cạn hết, rút vô trái chùm ruột và chuyển màu đỏ đẹp mắt thì tắt bếp. (Trong lúc rim lâu lâu đảo nhẹ tay).",
                "foods/foodrecipe34_4.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cho chùm ruột ra khay để nguội rồi cho vô hũ sạch, bảo quản trong ngăn mát tủ lạnh.",
                "foods/foodrecipe34_5.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Mứt chùm ruột để ăn vặt hoặc làm si rô đá bào đều ngon.",
                "foods/foodrecipe34_6.png"
            )
        )

        var mon_an_34 = FoodRecipe(34,"Mứt chùm ruột","foods/foodrecipe34_6.png", 2, "Dưới 3 tiếng",
            Date(),true,arrayListOf(3,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_34)

        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(200.0, "bột ớt", "g", 100.0))
        listIngredient.add(  RecipeIngredient(40.0, "bột ngọt", "g",40.0))
        listIngredient.add(  RecipeIngredient(40.0, "muối", "g", 20.0))

        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Nấu nước với ít muối,chờ nước sôi vừa bỏ khoai vào ngâm tầm 2-3 phút vớt ra giấy để khô.",
                "foods/foodrecipe35_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Trộn khoai tây với bột ớt,bột ngọt,muối,xíu dầu lắc đều đợi ngấm gia vị 3-5 phút",
                "foods/foodrecipe35_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Bỏ vào lò chiên không dầu thôi bật 200 độ chiên khoảng 20-25 phút",
                "foods/foodrecipe35_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Sản phẩm hoàn thành gắp ra dĩa và sơi thôi",
                "foods/foodrecipe35_4.png"
            )
        )

        var mon_an_35 = FoodRecipe(35,"Khoai tây chiên","foods/foodrecipe35_4.png", 2, "Dưới 30 phút",
            Date(),true,arrayListOf(3,5),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),0)
        result.add(mon_an_35)



        listIngredient= ArrayList()
        listIngredient.add(  RecipeIngredient(1.0, "bánh Sandwich", "miếng", 50.0))
        listIngredient.add(  RecipeIngredient(1.0, "trứng gà", "quả",40.0))
        listIngredient.add(  RecipeIngredient(5.0, "muối", "g", 5.0))
        listIngredient.add(  RecipeIngredient(5.0, "đường", "g", 10.0))


        listStep= ArrayList()
        listStep.add(
            RecipeCookStep(
                "Đập trứng vào một cái bát con có lót màng bọc thực phẩm!\n" +
                        "Sau đó cuộn trứng lại",
                "foods/foodrecipe36_1.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Nhúng nước sôi khoảng 3-4’",
                "foods/foodrecipe36_2.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Giữ nguyên chiếc chảo, áp chảo bánh mì sandwich nhà nào có máy nướng bỏ vào máy cũng được",
                "foods/foodrecipe36_3.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cho trứng lên mặt bánh, rắc xíu muối và tiêu",
                "foods/foodrecipe36_4.png"
            )
        )
        listStep.add(
            RecipeCookStep(
                "Cắt đôi trứng cho lòng đào tan chảy ngấm vào bánh và thưởng thức liền! So good cả nhà ơi",
                "foods/foodrecipe36_5.png"
            )
        )

        // thêm vào firebase
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