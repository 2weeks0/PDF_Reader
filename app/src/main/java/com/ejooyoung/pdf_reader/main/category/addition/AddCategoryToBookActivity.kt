package com.ejooyoung.pdf_reader.main.category.addition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.databinding.ActivityAddCategoryToBookBinding

class AddCategoryToBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCategoryToBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_category_to_book)
        setupFragment()
    }

    private fun setupFragment() {
        val categoryGuid = intent.getStringExtra(Const.KEY_BUNDLE_CATEGORY)
        supportFragmentManager.beginTransaction()
            .add(R.id.layRoot, AddCategoryToBookFragment.newInstance(categoryGuid))
            .commit()
    }
}