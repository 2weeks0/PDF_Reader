package com.ejooyoung.pdf_reader.base.ext

import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import com.ejooyoung.pdf_reader.database.DatabaseProvider
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
    application.contentResolver.takePersistableUriPermission(this, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    var fileName = File(toString()).name

    // fileName 추출
    if (toString().startsWith("content://")) {
        application.contentResolver.query(this, null, null, null, null).use {
            if (it != null && it.moveToFirst())
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
    }
    // 이미 저장된 파일이면 null 배출
    if (DatabaseProvider.provideBookSource(application).selectBook(fileName, toString()).blockingGet() != null) {
        return null
    }
    // 썸네일 생성 및 디비에 저장
    val book = Book.valueOf(fileName, toString(), "")
    val thumbnail = ThumbnailUtils.createMainThumbnail(application, book)
    return book.apply { thumbnailGuid = thumbnail.guid }
}
