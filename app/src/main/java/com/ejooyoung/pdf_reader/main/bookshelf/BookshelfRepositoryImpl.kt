package com.ejooyoung.pdf_reader.main.bookshelf

import android.app.Application
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType
import io.reactivex.rxjava3.core.Flowable

class BookshelfRepositoryImpl private constructor(
    private val bookDao: BookDao,
    private val categoryRelationDao: CategoryRelationDao
) : BookshelfRepository {

    companion object {
        fun newInstance(application: Application): BookshelfRepository {
            return BookshelfRepositoryImpl(
                DatabaseProvider.provideBookSource(application),
                DatabaseProvider.provideCategoryRelationSource(application)
            )
        }
    }

    override fun loadOriginBookList(): Flowable<List<Book>> {
        return bookDao.selectAllBooks()
    }

    override fun loadBookList(
        bookList: List<Book>,
        currentCategory: CurrentCategory
    ): Flowable<List<Book>> {
        return Flowable.fromCallable {
            return@fromCallable when (currentCategory.type) {
                CurrentCategoryType.ALL -> bookList
                CurrentCategoryType.FAVORITE -> bookList.filter { it.favorite }
                CurrentCategoryType.CUSTOM -> {
                    val guidSet =
                        categoryRelationDao.selectBookGuidByCategoryGuid(currentCategory.guid!!)
                            .toSet()
                    bookList.filter { guidSet.contains(it.guid) }
                }
            }
        }
            .onErrorReturnItem(emptyList())!!
    }
}