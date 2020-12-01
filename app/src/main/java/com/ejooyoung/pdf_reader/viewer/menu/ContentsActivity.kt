package com.ejooyoung.pdf_reader.viewer.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.databinding.ActivityContentsBinding
import com.ejooyoung.pdf_reader.model.Book

class ContentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setupFragment()
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contents)
    }

    private fun setupFragment() {
        val book: Book = intent.getParcelableExtra(Const.KEY_BUNDLE_BOOK)
        supportFragmentManager.beginTransaction()
            .add(R.id.layRoot, ContentsFragment.newInstance(book))
            .commit()
    }
}