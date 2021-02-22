package com.ejooyoung.pdf_reader.main.bookshelf

import android.app.Application
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType
import io.reactivex.rxjava3.core.Flowable

class BookshelfRepositoryImpl private constructor(
    application: Application
) : BookshelfRepository {

    private val bookDao = DatabaseProvider.provideBookSource(application)

    companion object {
        fun newInstance(application: Application) = BookshelfRepositoryImpl(application)
    }

    override fun loadBookList(currentCategory: CurrentCategory): Flowable<List<Book>> {
        return Flowable.fromCallable {
            return@fromCallable when (currentCategory.type) {
                CurrentCategoryType.ALL -> bookDao.selectAllBooks()
                CurrentCategoryType.FAVORITE -> bookDao.selectFavoriteBooks()
                CurrentCategoryType.CUSTOM -> bookDao.selectAllBooks()
            }
        }
            .onErrorReturnItem(emptyList())!!
    }
}