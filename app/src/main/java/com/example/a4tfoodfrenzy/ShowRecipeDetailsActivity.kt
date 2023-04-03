package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ShowRecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details)

        var rv = findViewById<RecyclerView>(R.id.foodImageRecyclerView)
        val mainIMG : ImageView = findViewById(R.id.mainFoodImageView)

        val imgList = arrayListOf<FoodImage>()
        imgList.add(FoodImage(R.drawable.avt))
        imgList.add(FoodImage(R.drawable.no_alcohol_icon))
        imgList.add(FoodImage(R.drawable.logo_fb))
        imgList.add(FoodImage(R.drawable.appetizer_icon))
        imgList.add(FoodImage(R.drawable.no_gluten_icon))
        imgList.add(FoodImage(R.drawable.no_sugar_icon))
        imgList.add(FoodImage(R.drawable.no_meat_icon))

        val adapter = FoodImageAdapter(imgList)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // set main img = first img in list
        mainIMG.setImageResource(imgList[0].getImgResource())
        adapter.onImageClick = {imgID ->
            mainIMG.setImageResource(imgID)
        }
    }
}

class FoodImageAdapter(private val imageList : ArrayList<FoodImage>) : RecyclerView.Adapter<FoodImageAdapter.ViewHolder>(){

    var onImageClick: ((Int) -> Unit)? = null
    var currentSelectedImage = 0

    inner class ViewHolder(listItemView : View) : RecyclerView.ViewHolder(listItemView){
        val imgView : ImageView = listItemView.findViewById(R.id.foodImageView)
        val imgCardView : MaterialCardView = listItemView.findViewById(R.id.foodImageCardView)

        init {
            listItemView.setOnClickListener{
                onImageClick?.invoke(imageList[bindingAdapterPosition].getImgResource())

                // change selected img position
                val temp = currentSelectedImage // store prev selected img
                currentSelectedImage = bindingAdapterPosition // assign new selected img

                // notify that chosen image changed
                notifyItemChanged(temp)
                notifyItemChanged(currentSelectedImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.food_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImg = imageList[position]

        holder.imgView.setImageResource(currentImg.getImgResource())

        // selected --> border = 10
        if(position == currentSelectedImage)
            holder.imgCardView.strokeWidth = 10

        // not selected position --> border = 0
        if(position != currentSelectedImage)
            holder.imgCardView.strokeWidth = 0
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}

class FoodImage(){
    private var imgStringUrl = ""
    private var imgResourceInt = -1
    constructor(img : String) : this() {
        this.imgStringUrl = img
    }
    constructor(img : Int) : this(){
        this.imgResourceInt = img
    }

    fun getImgResource() : Int{return imgResourceInt}
    fun getImgUrl() : String{return imgStringUrl}
}