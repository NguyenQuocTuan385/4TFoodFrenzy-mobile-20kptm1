package com.example.a4tfoodfrenzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar

class AddStepActivity : AppCompatActivity() {
    private lateinit var toolbarAddStep: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_step)
        initToolbar()
        setBackToobar()
        setCloseToobar()
    }
    private fun initToolbar()
    {
        toolbarAddStep=findViewById(R.id.toolbarAddStep)
    }
    private fun setBackToobar()
    {
        toolbarAddStep.setNavigationOnClickListener {
            finish()
        }
    }
    private fun setCloseToobar()
    {
        toolbarAddStep.setOnMenuItemClickListener { menuItem ->
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
}