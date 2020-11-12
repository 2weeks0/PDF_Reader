package com.ejooyoung.pdf_reader.base

object Const {

    object Permission {
        const val STORAGE = 1000
    }

    object DB {
        const val VERSION = 1
        const val FILE_NAME = "db.db"

        object Book {
            const val TABLE = "Book"
            const val COLUMN_GUID = "guid"
            const val COLUMN_FILE_NAME = "fileName"
            const val COLUMN_URI = "uri"
            const val COLUMN_THUMBNAIL_GUID = "thumbnailGuid"
        }

        object Thumbnail {
            const val TABLE = "Thumbnail"
            const val COLUMN_GUID = "guid"
            const val COLUMN_DIR_PATH = "dirPath"
            const val COLUMN_BOOK_GUID = "bookGuid"
        }
    }

    object Request {
        const val OPEN_PDF = 1000
    }
}
