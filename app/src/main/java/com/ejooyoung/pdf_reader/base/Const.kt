package com.ejooyoung.pdf_reader.base

object Const {

    const val DB_VERSION = 1
    const val DB_FILE_NAME = "db.db"

    const val DB_BOOK_TABLE = "Book"
    const val DB_BOOK_COLUMN_GUID = "guid"
    const val DB_BOOK_COLUMN_FILE_NAME = "fileName"
    const val DB_BOOK_COLUMN_URI = "uri"
    const val DB_BOOK_COLUMN_THUMBNAIL_GUID = "thumbnailGuid"
    const val DB_BOOK_COLUMN_LAST_PAGE = "lastPage"
    const val DB_BOOK_COLUMN_CURRENT_PAGE = "currentPage"
    const val DB_BOOK_COLUMN_READ_TIME = "readTime"

    const val DB_THUMBNAIL_TABLE = "Thumbnail"
    const val DB_THUMBNAIL_COLUMN_GUID = "guid"
    const val DB_THUMBNAIL_COLUMN_DIR_PATH = "dirPath"
    const val DB_THUMBNAIL_COLUMN_BOOK_GUID = "bookGuid"

    const val KEY_PERMISSION_STORAGE = 1000
    const val KEY_REQUEST_OPEN_PDF = 1000
    const val KEY_BUNDLE_BOOK = "bundleKeyBook"
}
