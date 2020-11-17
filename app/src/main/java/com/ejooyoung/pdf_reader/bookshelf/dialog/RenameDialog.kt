package com.ejooyoung.pdf_reader.bookshelf.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.ObservableField
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.databinding.DialogRenameBinding
import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RenameDialog private constructor(
    val book: Book
): AppCompatDialogFragment() {

    private lateinit var binding: DialogRenameBinding
    val bookName = ObservableField<String>(book.fileName)

    companion object {
        fun newInstance(book: Book): RenameDialog {
            return RenameDialog(book)
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

        val view = inflater.inflate(R.layout.dialog_rename, container, false)
        binding = DialogRenameBinding.bind(view).apply {
            dialog = this@RenameDialog
        }
        return view
    }

    fun onClickHandler(view: View) {
        when (view.id) {
            R.id.tvConfirm -> BookRepositoryImpl.getInstance(requireActivity().application)
                .updateBook(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i()
    }
}