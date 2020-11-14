package com.ejooyoung.pdf_reader.viewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.model.Book

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        setupFragment()
    }

   private fun setupFragment() {
       (intent.getParcelableExtra(Const.KEY_BUNDLE_BOOK) as Book?)?.let {
           supportFragmentManager.beginTransaction()
               .add(R.id.layRoot, ViewerFragment.newInstance(it))
               .commit()
       }
   }
}