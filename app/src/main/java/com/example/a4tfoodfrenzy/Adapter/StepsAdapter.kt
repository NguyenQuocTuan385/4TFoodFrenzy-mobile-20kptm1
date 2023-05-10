package com.example.a4tfoodfrenzy.Adapter

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4tfoodfrenzy.Model.FoodRecipe
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StepsAdapter(private val stepsList: ArrayList<RecipeCookStep>, private val mainContext: Context, private val mainRV: RecyclerView, private val foodRecipe: FoodRecipe, ) : RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    val storageRef = Firebase.storage

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val stepIMG: ImageView = listItemView.findViewById(R.id.stepImageView)
        val stepInstruction: TextView = listItemView.findViewById(R.id.textInstructionTextView)
        val toolbar: androidx.appcompat.widget.Toolbar =
            listItemView.findViewById(R.id.stepItemToolbar)
        private val closeActivityButton: TextView =
            listItemView.findViewById(R.id.closeStepItemTextView)
        val progressRV: RecyclerView = listItemView.findViewById(R.id.stepProgressRecyclerView)

        init {
            closeActivityButton.setOnClickListener {
//                val intent = Intent(mainContext, ShowRecipeDetailsActivity::class.java)
//
//                intent.putExtra("foodRecipe", foodRecipe)
//                mainContext.startActivity(intent)

                (mainContext as Activity).finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.recipe_step_item, parent, false))
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = stepsList[position]

        val imgRef = stepsList[position].image?.let { it -> storageRef.getReference(it) }
        if (imgRef != null) {
            imgRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(mainContext)
                    .load(uri)
                    .into(holder.stepIMG)
                Thread.sleep(100)
            }
        } else
            holder.stepIMG.visibility = View.GONE

        holder.stepInstruction.text = step.description
        holder.toolbar.title = "Bước ${position + 1}"

        val progressList = arrayListOf<String>()

        for (i in 1..stepsList.size)
            progressList.add(i.toString())

        // get width
        val wm: WindowManager =
            mainContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val adapter =
            StepProgressAdapter(progressList, position, wm.defaultDisplay.width, this.mainRV)
        holder.progressRV.adapter = adapter

        holder.progressRV.layoutManager =
            LinearLayoutManager(mainContext, LinearLayoutManager.HORIZONTAL, false)
    }

}