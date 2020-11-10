package com.ejooyoung.pdf_reader.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.discord.panels.OverlappingPanelsLayout
import com.ejooyoung.pdf_reader.util.Const
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.bookshelf.BookshelfFragment
import com.ejooyoung.pdf_reader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val settingViewModel by viewModels<SettingViewModel> {
        ViewModelFactories.of(application, this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupFragment()
    }

    override fun onResume() {
        super.onResume()
        checkStoragePermission()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.settingViewModel = settingViewModel
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layContainer, BookshelfFragment.newInstance())
            .commit()
    }

    private fun checkStoragePermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE),
                Const.Permission.STORAGE
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.Request.OPEN_PDF && resultCode == Activity.RESULT_OK) {
            settingViewModel.insertBookToDB(data)
        }
    }

    override fun onBackPressed() {
        if (binding.layOverlapping.getSelectedPanel() != OverlappingPanelsLayout.Panel.CENTER) {
            binding.layOverlapping.closePanels()
            return
        }
        super.onBackPressed()
    }
}