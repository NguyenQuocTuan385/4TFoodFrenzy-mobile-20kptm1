package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a4tfoodfrenzy.Model.RecipeCookStep
import com.example.a4tfoodfrenzy.R

class ShowRecipeDetailsDescriptionActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipe_details_description)

        val rv = findViewById<RecyclerView>(R.id.stepsRecyclerView)
        val stepList = arrayListOf<RecipeCookStep>()

        stepList.add(
            RecipeCookStep(
                "1. Bắp bò rửa sơ để ráo\n" +
                        "2. Hành tây, xà lách, cà chua rửa sạch rồi để ráo nước",
                R.drawable.bosotme2
            )
        )
        stepList.add(
            RecipeCookStep(
                "1. Cho 1 muỗng canh dầu ăn vào làm nóng, chỉnh lửa vừa, phi thơm hành tỏi và cho bò vào xào trong 2 phút cho săn\n2. " +
                        "Cho gói sốt me vào đảo đều trong 3 phút. Nêm nếm lại cho vừa ăn\n3. Thêm hành tây vào xào sơ khoảng 1 phút rồi tắt bếp.",
                R.drawable.bosotme5
            )
        )
        stepList.add(
            RecipeCookStep(
                "1. Xếp rau, cà chua thái lát ra đĩa, cho bò lên trên. \n" +
                        "2. Rắc thêm mè để thêm phần hấp dẫn.\n" +
                        "3. Thưởng thức cùng bánh mì ngay khi còn nóng. Ngon hơn khi dùng với cơm nóng hoặc",
                R.drawable.bosotme3
            )
        )

        stepList.add(
            RecipeCookStep(
                "3. Thưởng thức cùng bánh mì ngay khi còn nóng. Ngon hơn khi dùng với cơm nóng hoặc",
                R.drawable.bosotme4
            )
        )


        val adapter = StepsAdapter(stepList, this, rv)
        rv.adapter = adapter

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv)


    }
}

class StepsAdapter(
    private val stepsList: ArrayList<RecipeCookStep>,
    private val mainContext: Context,
    private val mainRV : RecyclerView,
) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val stepIMG : ImageView = listItemView.findViewById(R.id.stepImageView)
        val stepInstruction : TextView = listItemView.findViewById(R.id.textInstructionTextView)
        val toolbar : androidx.appcompat.widget.Toolbar = listItemView.findViewById(R.id.stepItemToolbar)
        private val closeActivityButton : TextView = listItemView.findViewById(R.id.closeStepItemTextView)
        val progressRV : RecyclerView = listItemView.findViewById(R.id.stepProgressRecyclerView)

        init {
            closeActivityButton.setOnClickListener {
                val intent = Intent(mainContext, ShowRecipeDetailsActivity::class.java)
                mainContext.startActivity(intent)
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

        holder.stepIMG.setImageResource(step.imageResource)
        holder.stepInstruction.text = step.description
        holder.toolbar.title = "Bước ${position + 1}"

        val progressList = arrayListOf<String>()

        for (i in 1..stepsList.size)
            progressList.add(i.toString())

        // get width
        val wm : WindowManager = mainContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val adapter = StepProgressAdapter(progressList, position,  wm.defaultDisplay.width, this.mainRV)
        holder.progressRV.adapter = adapter

        holder.progressRV.layoutManager =
            LinearLayoutManager(mainContext, LinearLayoutManager.HORIZONTAL, false)

    }
}

class StepProgressAdapter(
    private val progressList: ArrayList<String>,
    private val currentPos: Int, // this is current position of carousel
    private val parentRvWidth : Int,
    private val mainRV : RecyclerView
) : RecyclerView.Adapter<StepProgressAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val progressTV : TextView = listItemView.findViewById(R.id.progressTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.step_progress_item, parent, false))
    }

    override fun getItemCount(): Int {
        return progressList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currentPos == position) {
            holder.progressTV.setBackgroundResource(R.color.SecondaryColor)
            holder.progressTV.typeface = Typeface.DEFAULT_BOLD
        }

        holder.progressTV.text = progressList[position]
        holder.progressTV.width = parentRvWidth / progressList.size

        holder.itemView.setOnClickListener{
            mainRV.scrollToPosition(position)
        }
    }
}
