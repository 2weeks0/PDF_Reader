package com.ejooyoung.pdf_reader.viewer

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Book

class ViewerActivity : AppCompatActivity() {

    private lateinit var viewerFragment: ViewerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        setupFragment()
    }

   private fun setupFragment() {
       (intent.getParcelableExtra(Const.KEY_BUNDLE_BOOK) as Book?)?.let {
           viewerFragment = ViewerFragment.newInstance(it)
           supportFragmentManager.beginTransaction()
               .add(R.id.layRoot, viewerFragment)
               .commit()
       }
   }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return viewerFragment.onVolumeKeyDown(keyCode)
        }
        return super.onKeyDown(keyCode, event)
    }
}