package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.ActivityViewerSettingBinding

class ViewerSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewerSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupFragment()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewer_setting)
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layRoot, ViewerSettingFragment.newInstance())
            .commit()
    }
}