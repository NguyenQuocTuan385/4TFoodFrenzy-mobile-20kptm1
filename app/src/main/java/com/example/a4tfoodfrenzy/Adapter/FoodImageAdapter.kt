package com.example.a4tfoodfrenzy.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FoodImageAdapter(private val imageList: ArrayList<String?>, private val currentContext : Context) : RecyclerView.Adapter<FoodImageAdapter.ViewHolder>(){
    val storageRef = Firebase.storage
    var onImageClick: ((Drawable, Int) -> Unit)? = null
    var currentSelectedImage = 0
    var currentImageView : ImageView? = null

    inner class ViewHolder(listItemView : View) : RecyclerView.ViewHolder(listItemView){
        val imgView : ImageView = listItemView.findViewById(R.id.foodImageView)
        val imgCardView : MaterialCardView = listItemView.findViewById(R.id.foodImageCardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.food_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImg = imageList[position]

        val imageRef = currentImg?.let { storageRef.getReference(it) }
        imageRef?.downloadUrl?.addOnSuccessListener { uri ->
            Glide.with(currentContext)
                .load(uri)
                .into(holder.imgView)
            Thread.sleep(100)

            holder.imgCardView.setOnClickListener{
                onImageClick?.invoke(holder.imgView.drawable, position)

                // change selected img position
                val prevItem = currentSelectedImage // store prev selected img
                currentSelectedImage = position // assign new selected img

                // notify that chosen image changed
                notifyItemChanged(prevItem)
                notifyItemChanged(currentSelectedImage)

            }

            if(position == currentSelectedImage)
                holder.imgCardView.strokeWidth = 20

            // not selected position --> border = 0
            if(position != currentSelectedImage)
                holder.imgCardView.strokeWidth = 0
        }?.addOnFailureListener { exception ->
            // Xử lý lỗi
        }



    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}