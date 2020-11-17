package com.ejooyoung.pdf_reader.bookshelf.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.databinding.DialogLongClickPopupBinding
import com.ejooyoung.pdf_reader.model.Book

class LongClickPopupDialog private constructor(
    private val book: Book,
    private val longClickPopupListener: LongClickPopupListener
): AppCompatDialogFragment() {

    private lateinit var binding: DialogLongClickPopupBinding

    companion object {
        fun newInstance(
            book: Book,
            longClickPopupListener: LongClickPopupListener
        ): LongClickPopupDialog {
            return LongClickPopupDialog(book, longClickPopupListener)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.i()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestFeature(Window.FEATURE_NO_TITLE)
        }

        val view = inflater.inflate(R.layout.dialog_long_click_popup, container, false)
        binding = DialogLongClickPopupBinding.bind(view).apply {
            book = this@LongClickPopupDialog.book
            onClickListener = this@LongClickPopupDialog
        }
        return view
    }

    fun onClickHandler(view: View) {
        when (view.id) {
            R.id.tvRenaming -> longClickPopupListener.renameBook(book)
            R.id.tvBookmark -> longClickPopupListener.bookMarkBook(book)
            R.id.tvDelete -> longClickPopupListener.deleteBook(book)
        }
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i()
    }
}