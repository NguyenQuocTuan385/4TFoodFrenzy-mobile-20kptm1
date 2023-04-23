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
            arrayListOf(1, 2),
            arrayListOf(1,2,3)
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
            arrayListOf(),
            arrayListOf(1,2,3)
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
            arrayListOf(3,4),
            arrayListOf(5)
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
            arrayListOf(),
            arrayListOf(5,6,7)
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
            arrayListOf(),
            arrayListOf(1,3,4)
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
            arrayListOf(5,6),
            arrayListOf(3,5,8)
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
            arrayListOf(7),
            arrayListOf()
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
            arrayListOf(8),
            arrayListOf()
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
            arrayListOf(),
            arrayListOf()
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
            arrayListOf(9, 10),
            arrayListOf(1, 2, 3)
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
        helperFunctionDB.uploadImageToCloudStorage("bosotme","comments")
        helperFunctionDB.uploadImageToCloudStorage("bokho","comments")
        helperFunctionDB.uploadImageToCloudStorage("canhcaloc","comments")
        helperFunctionDB.uploadImageToCloudStorage("comrangduabo","comments")
        helperFunctionDB.uploadImageToCloudStorage("bo_nuong","comments")
        helperFunctionDB.uploadImageToCloudStorage("bunbohue","comments")

        var recipeCmt = RecipeComment(true, "comments/bosotme.png", "Món này nấu ngon lắm, hy vọng bạn ra tiếp công thức mới <3", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, "comments/bokho.png", "Món này có vẻ vẫn còn thiếu gì đó khiến vị chưa được ngon lắm", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, null, "Công thức rất rõ ràng, tôi sẽ thử nấu lại lần tiếp theo", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/canhcaloc.png", "Món ăn ngon quá :3", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/comrangduabo.png", "Món ăn tuyệt nhất mà tôi từng nấu trước đó", Date())

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

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Công thức gì chả có đầu có đuôi gì cả, 0 điểm", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(true, "comments/bunbohue.png", "Niceeeeeeeee", Date())

        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Too bad", Date())
        recipeCmtsList.add(recipeCmt)

        recipeCmt = RecipeComment(false, null, "Món ăn cần nhiều hình ảnh minh họa cho " +
                "rõ các bước hơn", Date()
        )
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

    fun generateDatabaseRecipeFood(){
        var result = ArrayList<FoodRecipe>()

        var listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150,"Xốt cà chua","g",50))
        listIngredient.add(RecipeIngredient(300,"Cá ngừ sạch","g",100))

        var listStep=ArrayList<RecipeCookStep>()
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe1_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe2_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe3_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe4_4","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe5_4","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe6_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe7_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe8_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe9_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe9_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_1","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_2","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_3","foods")
        helperFunctionDB.uploadImageToCloudStorage("foodrecipe10_4","foods")

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
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(1,2),
            arrayListOf(1,2,5,10),"Hiền Phương", R.drawable.defaultavt,15)

        result.add(mon_an_1)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g",100))
        listIngredient.add(RecipeIngredient(200,"Ba Rọi Heo Cooky (Thịt Tươi)","g",150))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",50))
        listIngredient.add(RecipeIngredient(200,"Cá Hú Tươi Làm Sạch Cắt Khúc","g",100))

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
            arrayListOf(2),
            listStep,
            listIngredient,
            arrayListOf(),
            arrayListOf(1,2,10),
            "Đặng Ngọc Tiến",
            R.drawable.avt,
            10
        )
        result.add(mon_an_2)


        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(150,"Mọc Viên","g",100))
        listIngredient.add(RecipeIngredient(150,"Xốt Cà Chua","g",150))

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
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(1,2,5,6,10),"Đặng Ngọc Tiến", R.drawable.avt,8)
        result.add(mon_an_3)

        listIngredient = ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200, "Khoai Mỡ (Cắt Sẵn)", "g",100))
        listIngredient.add(RecipeIngredient(36, "Bộ Nêm Canh (Ngò Gai, Ngò Om, Bột Gia Vị Canh CookyMADE)", "g",50))
        listIngredient.add(RecipeIngredient(100, "Thịt Heo Xay", "g",150))

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
            arrayListOf(1),
            listStep,
            listIngredient,
            arrayListOf(3,4),
            arrayListOf(5),
            "Nguyễn Văn Việt",
            R.drawable.defaultavt,
            21
        )
        result.add(mon_an_4)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(280,"Đậu Hũ Ta Vị Nguyên","g",50))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",100))
        listIngredient.add(RecipeIngredient(160,"Bộ Xốt Nấm Đông Cô","g",50))

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
            arrayListOf(1),listStep,listIngredient,
            arrayListOf(5,6,7),
            arrayListOf(3,4,6),"Bùi Hoàng Vũ",
            R.drawable.defaultavt,100)
        result.add(mon_an_5)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g",50))
        listIngredient.add(RecipeIngredient(15,"Hành Lá, Ớt Chỉ Thiên Đỏ","g",100))
        listIngredient.add(RecipeIngredient(200,"Cá Lóc Tươi Làm Sạch (Cắt Khúc)","g",150))

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
            arrayListOf(2),
            listStep,
            listIngredient,
            arrayListOf(8,9,10),
            arrayListOf(4),
            "Dương Chí Thông",
            R.drawable.defaultavt,
            20
        )
        result.add(mon_an_6)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g",150))
        listIngredient.add(RecipeIngredient(300,"Cốt Lết Heo Cooky (Thịt Tươi)","g",50))
        listIngredient.add(RecipeIngredient(37,"Bộ Kho Tiêu (Tiêu Xanh, Hành Lá, Ớt Chỉ Thiên Đỏ, Tiêu Đen Xay)","g",100))

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
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(4),"Trần Thị Ngọc Nhi",
            R.drawable.defaultavt,10)
        result.add(mon_an_7)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(60,"Xốt Kho Tộ CookyMADE","g",150))
        listIngredient.add(RecipeIngredient(300,"Thịt Vịt Xiêm (Chặt Sẵn)","g",50))
        listIngredient.add(RecipeIngredient(30,"Kho Gừng (Gừng Củ Tươi Gọt Sẵn)","g",200))
        listIngredient.add(RecipeIngredient(1,"(Lựa chọn) Dừa Xiêm Bến Tre","trái",50))

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
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(11,12,13),
            arrayListOf(6),"Ngọc Thư", R.drawable.defaultavt,5)
        result.add(mon_an_8)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(200,"Thịt bò","g",100))
        listIngredient.add(RecipeIngredient(300,"Bông cải xanh","g",50))
        listIngredient.add(RecipeIngredient(1,"Dầu hào","g",100))
        listIngredient.add(RecipeIngredient(5,"Bột bắp","g",50))

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
            FoodRecipe(9,"Cơm Bò Xào Bông Cải\n","foods/foodrecipe9_2.png",2,"Dưới 30 phút", Date(),true,
            arrayListOf(2),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(14,15),"Ngọc Thư", R.drawable.defaultavt,5)
        result.add(mon_an_9)

        listIngredient=ArrayList<RecipeIngredient>()
        listIngredient.add(RecipeIngredient(500,"Bò vụn","g",50))
        listIngredient.add(RecipeIngredient(500,"Cải chua","g",100))
        listIngredient.add(RecipeIngredient(2,"Cà chua","trái",150))
        listIngredient.add(RecipeIngredient(1,"Hành lá","tép",120))
        listIngredient.add(RecipeIngredient(1,"Ngò gai","tép",150))
        listIngredient.add(RecipeIngredient(3,"Tỏi băm","mcf",20))
        listIngredient.add(RecipeIngredient(1,"Nước lọc","lít",40))
        listIngredient.add(RecipeIngredient(5,"Dầu ăn","g",50))


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
            arrayListOf(1,4),listStep,listIngredient,
            arrayListOf(),
            arrayListOf(),"Đặng Ngọc Tiến",
            R.drawable.avt,10)
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