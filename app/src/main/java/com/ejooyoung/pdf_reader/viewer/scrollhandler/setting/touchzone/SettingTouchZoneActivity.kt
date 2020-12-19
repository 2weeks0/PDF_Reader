package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.ActivitySettingTouchZoneBinding

class SettingTouchZoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingTouchZoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_touch_zone)
        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layContainer, SettingTouchZoneFragment.newInstance())
            .commit()
    }
}