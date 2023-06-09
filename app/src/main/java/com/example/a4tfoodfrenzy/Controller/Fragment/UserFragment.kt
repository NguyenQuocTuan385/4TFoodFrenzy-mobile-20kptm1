package com.example.a4tfoodfrenzy.Controller.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.UserAdapter.UserAdapter
import com.example.a4tfoodfrenzy.Controller.Activity.AccountDetailsManagementActivity
import com.example.a4tfoodfrenzy.Helper.HelperFunctionDB
import com.example.a4tfoodfrenzy.Model.DBManagement
import com.example.a4tfoodfrenzy.Model.User
import com.example.a4tfoodfrenzy.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class UserFragment(private var isAdmin:Boolean) : Fragment() {


    private lateinit var searchView: SearchView
    private lateinit var userAdapter:UserAdapter
    private lateinit var list_user:ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_user, container, false)
        val recyclerView= view?.findViewById<RecyclerView>(R.id.list_user)
        list_user= arrayListOf<User>()
        searchView=view.findViewById(R.id.search)
        if(!isAdmin) {
            for (user in DBManagement.userList) {
                if (!user.isAdmin)
                    list_user.add(user)
            }
        }
        else{
            for (user in DBManagement.userList) {
                if (user.isAdmin)
                    list_user.add(user)
            }
        }
        filerUsers()
        userAdapter= UserAdapter(requireContext(),list_user)
        optionMenu()
        recyclerView?.adapter=userAdapter
        recyclerView?.layoutManager=
            GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

        return view
    }
    private fun filerUsers() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle submit event if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredList = ArrayList<User>()
                    for (user in list_user) {
                        if (user.fullname.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(
                                Locale.ROOT)) ||
                            user.email.toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))) {
                            filteredList.add(user)
                        }
                    }
                    userAdapter.filterList(filteredList)
                }
                return true
            }
        })

    }
    private fun optionMenu() {
        userAdapter.onButtonClick = { view, user ->
            val popUpMenu = PopupMenu(requireContext(), view)
            popUpMenu.menuInflater.inflate(R.menu.menu_ingredient, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.update-> {
                        sendData(user)
                        true
                    }
                    R.id.delete-> {
                        // nếu là current hiện tại thì thông báo không được xóa
                        if(user.id == DBManagement.user_current?.id)
                        {
                            Toast.makeText(requireContext(), "Không thể xóa tài khoản hiện tại", Toast.LENGTH_SHORT).show()
                            return@setOnMenuItemClickListener true
                        }

                        val db = FirebaseFirestore.getInstance()
                        var helperFunctionDB= HelperFunctionDB(requireContext())
                        helperFunctionDB.showWarningAlert("Xóa người dùng",
                            "Bạn có chắc là sẽ xóa người dùng này?")
                        {confirm ->
                            if(confirm)
                            {
                                var helperFunctionDB = HelperFunctionDB(requireContext())

                                // lấy danh sách món ăn của user
                                val myFoodRecipes = DBManagement.foodRecipeList.filter { user.myFoodRecipes.contains(it.id) }
                                // xóa món ăn của user
                                val id_my = myFoodRecipes.map { it.id }
                                Log.d("TAGmyFoodRecipessssss", id_my.toString())
                                myFoodRecipes.forEach{ foodRecipe ->
                                    helperFunctionDB.deleteMyRecipe(foodRecipe, user.id.toString())
                                }


                                // xóa trong danh sách user của món đã lưu của bảng RecipeFoods
                                db.collection("RecipeFoods")
                                    .whereArrayContains("userSavedRecipes", user.id)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("RecipeFoods").document(document.id)
                                                .update("userSavedRecipes", FieldValue.arrayRemove(user.id))
                                                .addOnSuccessListener {
                                                    Log.d("TAG", "DocumentSnapshot successfully deleted!")
                                                }
                                                .addOnFailureListener { e ->
                                                    Log.w("TAG", "Error deleting document", e)
                                                }
                                        }
                                    }


                                // lấy danh sách bình luận của user
                                val myComments = user.recipeCmts
                                // xóa bình luận của user
                                Log.d("TAGmyCommentssssss", myComments.toString())
                                myComments.forEach{ comment ->
                                    db.collection("RecipeCmts").whereEqualTo("id",comment).get()
                                        .addOnSuccessListener { documents ->
                                            for (document in documents) {
                                                db.collection("RecipeCmts").document(document.id).delete()
                                                    .addOnSuccessListener {
                                                        Log.d("TAG", "DocumentSnapshot successfully deleted!")
                                                    }
                                                    .addOnFailureListener { e ->
                                                        Log.w("TAG", "Error deleting document", e)
                                                    }
                                            }
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w("TAG", "Error getting documents: ", exception)
                                        }
                                }

                                // xóa user trong database
                                Log.d("TAG", "user: $user")
                                db.collection("users").whereEqualTo("email",user.email).get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            db.collection("users").document(document.id).delete()
                                                .addOnSuccessListener {
                                                    Toast.makeText(requireContext(),"Xóa thành công",
                                                        Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(requireContext(),"Xóa thất bại",
                                                        Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w("TAG", "Error getting documents: ", exception)
                                    }

                                list_user.remove(user)
                                userAdapter.notifyDataSetChanged()
                            }
                        }
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }

    }
    private fun sendData(user:User)
    {
        val intent= Intent(requireContext(), AccountDetailsManagementActivity::class.java)
        intent.putExtra("user",user)
        startActivityForResult(intent,1)
    }

}