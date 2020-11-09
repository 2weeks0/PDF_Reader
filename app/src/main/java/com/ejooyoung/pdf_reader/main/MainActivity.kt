package com.ejooyoung.pdf_reader.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.util.Const
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.bookshelf.BookshelfFragment
import com.ejooyoung.pdf_reader.databinding.ActivityMainBinding
import com.ejooyoung.pdf_reader.viewer.ViewerActivity

class MainActivity : AppCompatActivity() {

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
        binding.onClickHandler = this
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

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd -> openFileManager()
        }
    }

    private fun openFileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        startActivityForResult(intent, Const.Request.OPEN_PDF)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.Request.OPEN_PDF && resultCode == Activity.RESULT_OK) {
            data?.clipData?.let {
                for (i in 0 until it.itemCount) {
                    startViewerActivity(it.getItemAt(i).uri)
                }
                return
            }
            data?.data?.let {
                startViewerActivity(Uri.parse(it.toString()))
            }
        }
    }

    private fun startViewerActivity(uri: Uri) {
        val intent = Intent(this, ViewerActivity::class.java).apply {
            data = uri
        }
        startActivity(intent)
    }
}