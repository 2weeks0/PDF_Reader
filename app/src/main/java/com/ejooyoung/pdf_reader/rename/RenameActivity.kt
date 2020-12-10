package com.ejooyoung.pdf_reader.rename

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.databinding.ActivityRenameBinding

class RenameActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRenameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rename)
        intent?.let {
            binding.title = it.getStringExtra(Const.KEY_BUNDLE_TITLE)
            binding.renamable = it.getParcelableExtra(Const.KEY_BUNDLE_RENAMABLE)
        }
        binding.onRenameListener = object : OnRenameListener {
            override fun onRename() {
                binding.renamable!!.rename(binding.edt.text.toString())
                val intent = Intent().apply {
                    putExtra(Const.KEY_BUNDLE_RENAMABLE, binding.renamable)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}