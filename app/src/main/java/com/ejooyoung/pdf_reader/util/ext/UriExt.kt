package com.ejooyoung.pdf_reader.util.ext

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.util.ThumbnailUtils
import java.io.File


fun Intent.toBookList(application: MainApplication): List<Book> {
    clipData?.let {
        return arrayListOf<Book>().apply {
            for (i in 0 until it.itemCount) {
                val uri = it.getItemAt(i).uri
                uri.toBook(application)?.let {
                    add(it)
                }
            }
        }
    }
    return arrayListOf<Book>().apply {
        data!!.toBook(application)?.let {
            add(it)
        }
    }
}

private fun Uri.toBook(application: MainApplication): Book? {
    var fileName = File(toString()).name

    // fileName 추출
    if (toString().startsWith("content://")) {
        application.contentResolver.query(this, null, null, null, null).use {
            if (it != null && it.moveToFirst())
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }

    val book = Book.valueOf(fileName, toString(), "")

    // 이미 저장된 파일이면 null 배출
    DatabaseProvider.provideBookSource(application).let {
        Log.d("LEEJY", "containsBook($fileName, ${toString()}): ${it.containsBook(fileName, toString())}")
        if (it.containsBook(fileName, toString()) != 0) {
            return null
        }
    }

//     썸네일 생성 및 디비에 저장
    val thumbnail = ThumbnailUtils.create(application, book)

    return book.apply { thumbnailGuid = thumbnail.guid }
}
