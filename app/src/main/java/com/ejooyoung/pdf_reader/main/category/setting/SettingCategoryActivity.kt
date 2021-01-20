package com.ejooyoung.pdf_reader.main.category.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.ActivitySettingCategoryBinding

class SettingCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupFragment()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_category)
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layRoot, SettingCategoryFragment.newInstance())
            .commit()
    }
}