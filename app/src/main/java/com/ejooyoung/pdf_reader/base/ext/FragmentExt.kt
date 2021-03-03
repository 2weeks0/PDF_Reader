package com.ejooyoung.pdf_reader.base.ext

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.category.addition.AddCategoryToBookActivity
import com.ejooyoung.pdf_reader.main.category.setting.SettingCategoryActivity
import com.ejooyoung.pdf_reader.rename.Renamable
import com.ejooyoung.pdf_reader.rename.RenameActivity
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsActivity
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.GridViewerActivity
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingActivity

fun Fragment.makeToast(stringId: Int) {
    Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
}

fun Fragment.startGridViewerActivity(book: Book) {
    val intent = Intent(requireContext(), GridViewerActivity::class.java).apply {
        putExtra(Const.KEY_BUNDLE_BOOK, book)
    }
    startActivityForResult(intent, Const.KEY_REQUEST_OPEN_GRID_VIEWER)
}

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

fun Fragment.startViewerSettingActivity() {
    val intent = Intent(requireContext(), ViewerSettingActivity::class.java)
    startActivity(intent)
}

fun Fragment.startSettingCategoryActivity() {
    val intent = Intent(requireContext(), SettingCategoryActivity::class.java)
    startActivity(intent)
}

fun Fragment.startAddCategoryToBookActivity(categoryGuid: String) {
    val intent = Intent(requireContext(), AddCategoryToBookActivity::class.java).apply {
        putExtra(Const.KEY_BUNDLE_CATEGORY, categoryGuid)
    }
    startActivity(intent)
}