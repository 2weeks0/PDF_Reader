package com.ejooyoung.pdf_reader.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.util.Const
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        checkStoragePermission()
        setupFragment()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.onClickHandler = this
    }

    private fun checkStoragePermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    Const.REQUEST_STORAGE_PERMISSION
                )
        }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layContainer, MainFragment.newInstance())
            .commit()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd ->
        }
    }
}