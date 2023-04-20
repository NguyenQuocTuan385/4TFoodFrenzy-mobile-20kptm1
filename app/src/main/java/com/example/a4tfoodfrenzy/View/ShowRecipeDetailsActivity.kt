package com.example.a4tfoodfrenzy.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Adapter.FoodImage
import com.example.a4tfoodfrenzy.Adapter.FoodImageAdapter
import com.example.a4tfoodfrenzy.R

class ShowRecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details)

        val rv = findViewById<RecyclerView>(R.id.foodImageRecyclerView)
        val mainIMG : ImageView = findViewById(R.id.mainFoodImageView)
        val showStepDetailsButton : Button = findViewById(R.id.moreDetailsButton)
        val writeCommentButton : Button = findViewById(R.id.writeCommentButton)

        val imgList = arrayListOf<FoodImage>()
        imgList.add(FoodImage(R.drawable.bosotmemainimage))
        imgList.add(FoodImage(R.drawable.bosotme2))
        imgList.add(FoodImage(R.drawable.bosotme3))
        imgList.add(FoodImage(R.drawable.bosotme4))
        imgList.add(FoodImage(R.drawable.bosotme5))

        val adapter = FoodImageAdapter(imgList)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // set main img = first img in list
        mainIMG.setImageResource(imgList[0].getImgResource())
        adapter.onImageClick = {imgID ->
            mainIMG.setImageResource(imgID)
        }

        showStepDetailsButton.setOnClickListener{
            val myIntent = Intent(this, ShowRecipeDetailsDescriptionActivity::class.java)

            startActivity(myIntent)
        }

        writeCommentButton.setOnClickListener{
            val myIntent = Intent(this, WriteCommentActivity::class.java)
            startActivity(myIntent)
        }

        val toListComment = findViewById<LinearLayout>(R.id.list_commentLinearLayout)
        toListComment.setOnClickListener{
            val myIntent = Intent(this, CommentListActivity::class.java)
            startActivity(myIntent)
        }
    }
}
