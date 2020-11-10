package com.ejooyoung.pdf_reader.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.repository.BookRepository
import com.ejooyoung.pdf_reader.util.Const
import com.ejooyoung.pdf_reader.util.ext.toBook

class SettingViewModel private constructor(
    application: Application,
    private val bookRepository: BookRepository
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(
            application: Application,
            bookRepository: BookRepository) = SettingViewModel(application, bookRepository)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd -> openFileManager(view.context as Activity)
        }
    }

    private fun openFileManager(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        activity.startActivityForResult(intent, Const.Request.OPEN_PDF)
    }

    fun insertBookToDB(data: Intent?) {
        data?.clipData?.let {
            val array = emptyArray<Book>().apply {
                for (i in 0 until it.itemCount) {
                    val uri = it.getItemAt(i).uri
                    this[i] = uri.toBook()
                }
            }
            bookRepository.insertBooks(*array)
            return
        }
        data?.data?.let {
            bookRepository.insertBooks(it.toBook())
        }
    }
}