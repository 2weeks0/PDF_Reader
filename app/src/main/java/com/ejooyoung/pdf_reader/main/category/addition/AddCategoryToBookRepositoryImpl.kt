package com.ejooyoung.pdf_reader.main.category.addition

import android.app.Application
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.CategoryRelation
import com.ejooyoung.pdf_reader.main.category.addition.model.AddableBook
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class AddCategoryToBookRepositoryImpl private constructor(
    private val bookDao: BookDao,
    private val categoryRelationDao: CategoryRelationDao
) : AddCategoryToBookRepository {

    companion object {
        fun newInstance(application: Application): AddCategoryToBookRepository {
            return AddCategoryToBookRepositoryImpl(
                DatabaseProvider.provideBookSource(application),
                DatabaseProvider.provideCategoryRelationSource(application)
            )
        }
    }

    override fun loadAddableBookList(categoryGuid: String): Flowable<List<AddableBook>> {
        return Flowable.fromCallable {
            bookDao.selectBooksNotInCategory(categoryGuid).map {
                AddableBook(it.guid, it.fileName, it.thumbnailGuid)
            }
        }
    }

    override fun saveAddToCategory(
        categoryGuid: String,
        bookGuidList: List<AddableBook>
    ): Completable {
        val categoryRelationList =
            bookGuidList.map { CategoryRelation.valueOf(it.guid, categoryGuid) }
        return categoryRelationDao.insertCategoryRelations(categoryRelationList)
    }
}