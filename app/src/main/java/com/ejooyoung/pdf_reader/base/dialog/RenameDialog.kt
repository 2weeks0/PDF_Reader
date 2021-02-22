package com.ejooyoung.pdf_reader.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.databinding.DialogRenameBinding
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RenameDialog private constructor(
    val book: Book,
    private val bookDao: BookDao
): AppCompatDialogFragment() {

    private lateinit var binding: DialogRenameBinding

    companion object {
        fun newInstance(book: Book, bookDao: BookDao): RenameDialog {
            return RenameDialog(book, bookDao)
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

        val view = inflater.inflate(R.layout.dialog_rename, container, false)
        binding = DialogRenameBinding.bind(view).apply {
            dialog = this@RenameDialog
        }
        return view
    }

    fun onClickHandler(view: View) {
        when (view.id) {
            R.id.tvConfirm -> bookDao.updateBook(book)
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