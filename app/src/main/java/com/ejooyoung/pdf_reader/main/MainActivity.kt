package com.ejooyoung.pdf_reader.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amitshekhar.DebugDB
import com.discord.panels.OverlappingPanelsLayout
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.mvvm.BaseActivity
import com.ejooyoung.pdf_reader.main.bookshelf.BookshelfFragment
import com.ejooyoung.pdf_reader.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun onResume() {
        super.onResume()
        checkStoragePermission()
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(application, this)
            .create(MainViewModel::class.java)
    }

    override fun setupDataBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = this@MainActivity.viewModel
        }
        setContentView(binding.root)
    }

    override fun setupObserver() {

    }

    override fun onBindingCreated() {
        DebugDB.getAddressLog()
        setupFragment()
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
                Const.KEY_PERMISSION_STORAGE
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.KEY_REQUEST_OPEN_PDF && resultCode == Activity.RESULT_OK) {
            viewModel.insertBookToDB(data!!)
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