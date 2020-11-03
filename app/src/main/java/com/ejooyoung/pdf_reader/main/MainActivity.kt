package com.ejooyoung.pdf_reader.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ejooyoung.pdf_reader.util.Const
import com.ejooyoung.pdf_reader.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkStoragePermission()
        setupFragment()
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
}