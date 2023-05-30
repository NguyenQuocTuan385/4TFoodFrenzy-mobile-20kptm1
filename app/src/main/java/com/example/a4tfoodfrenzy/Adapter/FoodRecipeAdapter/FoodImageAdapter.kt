package com.example.a4tfoodfrenzy.Adapter.FoodRecipeAdapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*

class FoodImageAdapter(
    private val imageList: ArrayList<String?>,
    private val currentContext: Context
) : RecyclerView.Adapter<FoodImageAdapter.ViewHolder>() {
    val storageRef = Firebase.storage
    var onImageClick: ((Drawable, Int) -> Unit)? = null
    private var currentSelectedImage = 0

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val imgView: ImageView = listItemView.findViewById(R.id.foodImageView)
        val imgCardView: MaterialCardView = listItemView.findViewById(R.id.foodImageCardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.food_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position : Int) {
        var currentImg = imageList[position]
        val imageRef = currentImg?.let { storageRef.getReference(it) }
        val downloadImageException = CoroutineExceptionHandler { _, _ ->
            currentImg = null

        }
        val downloadImageScope =
            CoroutineScope(Job() + Dispatchers.IO + downloadImageException) // scope
        val downloadImageJob = downloadImageScope.launch {
            // download image from storage and use glide to load into imageview
            imageRef?.downloadUrl?.addOnSuccessListener { uri ->
                Glide.with(currentContext)
                    .load(uri)
                    .into(holder.imgView)
            }
        }

        downloadImageJob.invokeOnCompletion {
            if(downloadImageJob.isCancelled)
                holder.imgCardView.visibility = View.GONE
        }

        if (currentImg != null) {
            // click listener for every image in array
            holder.imgCardView.setOnClickListener {
                if (holder.imgView.drawable != null) {
                    onImageClick?.invoke(holder.imgView.drawable, holder.bindingAdapterPosition)

                    // change selected img position
                    val prevItem = currentSelectedImage // store prev selected img
                    currentSelectedImage = holder.bindingAdapterPosition // assign new selected img

                    // notify that chosen image changed
                    notifyItemChanged(prevItem)
                    notifyItemChanged(currentSelectedImage)
                }
            }

            if (position == currentSelectedImage)
                holder.imgCardView.strokeWidth = 10

            // not selected position --> border = 0
            if (position != currentSelectedImage)
                holder.imgCardView.strokeWidth = 0
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}