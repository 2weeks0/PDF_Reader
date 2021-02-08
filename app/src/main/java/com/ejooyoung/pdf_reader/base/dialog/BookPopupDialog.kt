package com.ejooyoung.pdf_reader.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.databinding.DialogBookPopupBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookPopupDialog private constructor(
    val book: Book,
    private val bookRepository: BookRepository
) : AppCompatDialogFragment() {

    private lateinit var binding: DialogBookPopupBinding

    companion object {
        fun newInstance(book: Book, bookRepository: BookRepository): BookPopupDialog {
            return BookPopupDialog(book, bookRepository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.requestFeature(Window.FEATURE_NO_TITLE)
        }

        val view = inflater.inflate(R.layout.dialog_book_popup, container, false)
        binding = DialogBookPopupBinding.bind(view).apply {
            dialog = this@BookPopupDialog
        }
        return view
    }

    fun showRenameDialog() {
        RenameDialog.newInstance(
            book,
            BookRepositoryImpl.getInstance(requireActivity().application)
        )
            .show(requireActivity().supportFragmentManager, null)
        dismiss()
    }

    fun updateFavorite() {
        book.favorite = !book.favorite
        bookRepository.updateBook(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val stringId =
                    if (book.favorite) R.string.txt_success_add_favorite
                    else R.string.txt_success_delete_favorite
                Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT)
                    .show()
            }
        dismiss()
    }

    fun deleteBook() {
        bookRepository.deleteBooks(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(requireContext(), R.string.txt_success_delete_book, Toast.LENGTH_SHORT)
                    .show()
            }
        dismiss()
    }
}