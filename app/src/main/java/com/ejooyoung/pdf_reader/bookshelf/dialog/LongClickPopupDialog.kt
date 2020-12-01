package com.ejooyoung.pdf_reader.bookshelf.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.DialogLongClickPopupBinding
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LongClickPopupDialog private constructor(
    val book: Book,
    private val bookRepository: BookRepository
): AppCompatDialogFragment() {

    private lateinit var binding: DialogLongClickPopupBinding

    companion object {
        fun newInstance(book: Book, bookRepository: BookRepository): LongClickPopupDialog {
            return LongClickPopupDialog(book, bookRepository)
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

        val view = inflater.inflate(R.layout.dialog_long_click_popup, container, false)
        binding = DialogLongClickPopupBinding.bind(view).apply {
            dialog = this@LongClickPopupDialog
        }
        return view
    }

    fun onClickHandler(view: View) {
        when (view.id) {
            R.id.tvRenaming -> RenameDialog.newInstance(
                book,
                BookRepositoryImpl.getInstance(requireActivity().application)
            )
                .show(requireActivity().supportFragmentManager, null)
            R.id.tvBookmark -> DevLogger.i()
            R.id.tvDelete -> bookRepository.deleteBooks(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        DevLogger.i()
    }
}