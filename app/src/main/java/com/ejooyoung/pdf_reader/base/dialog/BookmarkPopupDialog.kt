package com.ejooyoung.pdf_reader.base.dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.makeToast
import com.ejooyoung.pdf_reader.base.ext.startRenameActivity
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.databinding.DialogBookmarkPopupBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookmarkPopupDialog private constructor(
    val bookmark: Bookmark,
    private val bookmarkRepository: BookmarkRepository
): AppCompatDialogFragment() {

    private lateinit var binding: DialogBookmarkPopupBinding

    companion object {
        fun newInstance(bookmark: Bookmark, bookmarkRepository: BookmarkRepository): BookmarkPopupDialog {
            return BookmarkPopupDialog(bookmark, bookmarkRepository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DevLogger.i()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestFeature(Window.FEATURE_NO_TITLE)
        }

        val view = inflater.inflate(R.layout.dialog_bookmark_popup, container, false)
        binding = DialogBookmarkPopupBinding.bind(view).apply {
            dialog = this@BookmarkPopupDialog
        }
        return view
    }

    fun onClickHandler(view: View) {
        when (view.id) {
            R.id.tvRenaming -> startRenameActivity(
                R.string.txt_rename_bookmark,
                bookmark
            )
            R.id.tvDelete -> bookmarkRepository.deleteBookmark(bookmark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    makeToast(R.string.msg_toast_del_bookmark)
                    dismiss()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DevLogger.i()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                Const.KEY_REQUEST_RENAME ->
                    data?.getParcelableExtra<Bookmark>(Const.KEY_BUNDLE_RENAMABLE)?.let {
                        bookmarkRepository.updateBookmark(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                makeToast(R.string.msg_success_rename_bookmark)
                                dismiss()
                            }
                    }
            }
        }
    }
}