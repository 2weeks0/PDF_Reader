package com.ejooyoung.pdf_reader.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.toBookList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingViewModel private constructor(
    application: Application,
    private val bookRepository: BookRepository
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(
            application: Application,
            bookRepository: BookRepository
        ) = SettingViewModel(application, bookRepository)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd -> openFileManager(view.context as Activity)
        }
    }

    private fun openFileManager(activity: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        activity.startActivityForResult(intent, Const.Request.OPEN_PDF)
    }

    fun insertBookToDB(data: Intent) {
        Observable.fromCallable { data.toBookList(getApplication()).toTypedArray() }
//            .onErrorReturnItem(emptyArray())
            .flatMapCompletable { bookRepository.insertBooks(*it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("LEEJY", "insertBookToDB complete")
            }
    }
}