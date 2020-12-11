package com.ejooyoung.pdf_reader.base.ext

import android.content.Intent
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.rename.Renamable
import com.ejooyoung.pdf_reader.rename.RenameActivity
import com.ejooyoung.pdf_reader.viewer.menu.ContentsActivity

fun Fragment.startContentsActivity(book: Book) {
    val intent = Intent(requireContext(), ContentsActivity::class.java).apply {
        putExtra(Const.KEY_BUNDLE_BOOK, book)
    }
    startActivityForResult(intent, Const.KEY_REQUEST_OPEN_CONTENTS)
}

fun Fragment.startRenameActivity(titleResId: Int, renamable: Renamable) {
    val intent = Intent(requireContext(), RenameActivity::class.java).apply {
        putExtra(Const.KEY_BUNDLE_TITLE, resources.getString(titleResId))
        putExtra(Const.KEY_BUNDLE_RENAMABLE, renamable)
    }
    startActivityForResult(intent, Const.KEY_REQUEST_RENAME)
}