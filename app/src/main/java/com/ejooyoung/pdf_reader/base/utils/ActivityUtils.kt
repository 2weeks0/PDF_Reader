package com.ejooyoung.pdf_reader.base.utils

import android.content.Intent
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.rename.Renamable
import com.ejooyoung.pdf_reader.rename.RenameActivity

object ActivityUtils {

    fun <T: Fragment> startRenameActivity(
        fragment: T,
        titleResId: Int,
        renamable: Renamable
    ) {
        val intent = Intent(fragment.requireContext(), RenameActivity::class.java).apply {
            putExtra(Const.KEY_BUNDLE_TITLE, fragment.resources.getString(titleResId))
            putExtra(Const.KEY_BUNDLE_RENAMABLE, renamable)
        }
        fragment.startActivityForResult(intent, Const.KEY_REQUEST_RENAME)
    }
}