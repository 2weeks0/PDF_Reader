package com.ejooyoung.pdf_reader.bookshelf

import android.os.Environment
import android.util.Log
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.model.PDF
import com.ejooyoung.pdf_reader.util.ThumbnailUtils
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.lang.Exception

class BookshelfRepository {

    companion object {
        const val PDF_PATTERN = ".pdf"
        const val PAGE_NUM_TO_MAKE_THUMB = 0
        fun newInstance() = BookshelfRepository()
    }

    fun loadPdfList(application: MainApplication) = Observable.fromCallable {
        arrayListOf<PDF>().apply {
            findPdf(application, this, Environment.getExternalStorageDirectory())
        }
    }.onErrorReturnItem(arrayListOf())

    private fun findPdf(application: MainApplication, list: ArrayList<PDF>, dir: File) {
        if (dir.listFiles() == null) {
            return
        }

        try {
            for (file in dir.listFiles()) {
                if (file.isDirectory) findPdf(application, list, file)
                else if (file.name.endsWith(PDF_PATTERN)) {
                    ThumbnailUtils.makeThumbnailIfNotExist(application, file)
                    list.add(PDF(file))
                }
            }
        }
        catch (e: Exception) {
            Log.e("LEEJY", e.message.toString())
        }
    }
}