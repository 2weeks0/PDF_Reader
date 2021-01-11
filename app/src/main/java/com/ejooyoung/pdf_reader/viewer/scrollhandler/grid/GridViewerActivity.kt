package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.databinding.ActivityGridViewerBinding

class GridViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridViewerBinding
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupArgument()
        setupDataBinding()
        setupFragment()
    }

    private fun setupArgument() {
        book = intent.getParcelableExtra(Const.KEY_BUNDLE_BOOK)
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_grid_viewer)
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.layRoot, GridViewerFragment.newInstance(book))
            .commit()
    }
}