package com.example.a4tfoodfrenzy.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.a4tfoodfrenzy.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddIngredient : AppCompatActivity() {
    private lateinit var toolbarAddIngredient: MaterialToolbar
    private lateinit var unitIngredientEdit: EditText
    private lateinit var unitPicker: NumberPicker
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var listUnit: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)

        initViews()
        initListeners()
    }

    private fun initViews() {
        toolbarAddIngredient = findViewById(R.id.toolbarAddIngredient)
        unitIngredientEdit = findViewById(R.id.unitIngredientEdit)
        listUnit = listOf("g", "kg", "ml", "l", "quả", "trái", "gói")
    }

    private fun initListeners() {
        setBackToolbar()
        setCloseToolbar()
        setUnitPickerListener()
    }

    private fun setBackToolbar() {
        toolbarAddIngredient.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setCloseToolbar() {
        toolbarAddIngredient.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    finish()
                    // Xử lý khi người dùng chọn Save
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUnitPickerListener() {
        unitIngredientEdit.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showUnitPickerDialog()
                true
            } else {
                false
            }
        }
    }

    private fun showUnitPickerDialog() {
        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_dialog_unit, findViewById(R.id.bottomSheetContainer))

        //thêm dữ liệu vào dialog
        unitPicker = bottomSheetView.findViewById(R.id.unitPicker)
        unitPicker.minValue = 0
        unitPicker.maxValue = listUnit.size - 1
        unitPicker.displayedValues = listUnit.toTypedArray()

        //chọn đơn vị
        bottomSheetView.findViewById<Toolbar>(R.id.toolBar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    bottomSheetDialog.hide()
                    val selectedValue = listUnit[unitPicker.value]
                    unitIngredientEdit.text = Editable.Factory.getInstance().newEditable(selectedValue)
                    true
                }
                else -> false
            }
        }

        //hiển thị
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}
