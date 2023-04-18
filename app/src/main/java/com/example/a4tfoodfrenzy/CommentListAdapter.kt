package com.example.a4tfoodfrenzy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.model.Comment

class CommentListAdapter(
    private var commentArray: ArrayList<Comment>, private var isCmtListUserView: Boolean,
    private var isCmtListAdminView: Boolean
) : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    companion object {
        private const val CMT_LIST_USER_VIEW = 1
        private const val CMT_LIST_ADMIN_VIEW = 2
    }

    var onItemClick: ((Comment, Int) -> Unit)? = null
    fun setCmtListUserView(isCmtListUserView: Boolean) {
        this.isCmtListUserView = isCmtListUserView
        notifyDataSetChanged()
    }

    fun setCmtListAdminView(isCmtListAdminView: Boolean) {
        this.isCmtListAdminView = isCmtListAdminView
        notifyDataSetChanged()
    }
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val avatarIV = listItemView.findViewById<ImageView>(R.id.avatarIV)
        val nameTV = listItemView.findViewById<TextView>(R.id.nameTV)
        val foodTV = listItemView.findViewById<TextView>(R.id.foodTV)
        val foodIV = listItemView.findViewById<ImageView>(R.id.foodIV)
        val cmtDescripTV = listItemView.findViewById<TextView>(R.id.cmtDescripTV)
        val timeTV = listItemView.findViewById<TextView>(R.id.timeTV)
        init {
            listItemView.setOnClickListener {
                onItemClick?.invoke(
                    commentArray.get(adapterPosition), adapterPosition
                )
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (isCmtListUserView) {
            return CMT_LIST_USER_VIEW
        }
        else {
            return CMT_LIST_ADMIN_VIEW
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val layoutResult = if (viewType == CMT_LIST_USER_VIEW) {
            R.layout.comment
        } else{
            R.layout.comment_management_item
        }

        val commentView = inflater.inflate(layoutResult, parent, false)
        // Return a new holder instance
        return ViewHolder(commentView)
    }

    override fun getItemCount(): Int {
        return commentArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val cmtRender: Comment = commentArray.get(position)
        // Set item views based on your views and data model
        val nameTV = holder.nameTV
        nameTV.text = cmtRender.username
        val foodTV = holder.foodTV
        foodTV.text = cmtRender.nameRecipe
        val cmtDescripTV = holder.cmtDescripTV
        cmtDescripTV.text = cmtRender.description
        val timeTV = holder.timeTV
        timeTV.text = cmtRender.date
        val avatarIV = holder.avatarIV
        cmtRender.avatarUser?.let { avatarIV.setImageResource(it) }
        val foodIV = holder.foodIV
        if (cmtRender.image == 0) {
            foodIV.visibility = View.GONE
        }
        else {
            foodIV.setImageResource(cmtRender.image)
        }
    }
}



