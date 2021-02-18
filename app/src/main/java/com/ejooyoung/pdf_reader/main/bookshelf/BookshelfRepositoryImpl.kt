package com.ejooyoung.pdf_reader.main.bookshelf

import android.app.Application
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType
import io.reactivex.rxjava3.core.Flowable

class BookshelfRepositoryImpl private constructor(
    application: Application
) : BookshelfRepository {

    private val bookRepository: BookRepository = BookRepositoryImpl.getInstance(application)

    companion object {
        fun newInstance(application: Application) = BookshelfRepositoryImpl(application)
    }

    override fun loadBookList(currentCategory: CurrentCategory): Flowable<List<Book>> {
        return Flowable.fromCallable {
            return@fromCallable when (currentCategory.type) {
                CurrentCategoryType.ALL -> bookRepository.selectAllBooks()
                CurrentCategoryType.FAVORITE -> bookRepository.selectFavoriteBooks()
                CurrentCategoryType.CUSTOM -> bookRepository.selectAllBooks()
            }
        }
            .onErrorReturnItem(emptyList())!!
    }
}