package com.ejooyoung.pdf_reader.main.category

import android.content.Context
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.CategoryDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class CategoryRepositoryImpl private constructor(
    private val categoryDao: CategoryDao,
    private val categoryRelationDao: CategoryRelationDao,
    private val bookDao: BookDao
) : CategoryRepository {

    companion object {
        fun newInstance(context: Context): CategoryRepository {
            return CategoryRepositoryImpl(
                DatabaseProvider.provideCategorySource(context),
                DatabaseProvider.provideCategoryRelationSource(context),
                DatabaseProvider.provideBookSource(context)
            )
        }
    }

    override fun loadCountOfAllBook(): Flowable<Int> {
        return Flowable.fromCallable { bookDao.selectAllBooks().count() }
    }

    override fun loadCountOfFavoriteBook(): Flowable<Int> {
        return Flowable.fromCallable { bookDao.selectFavoriteBooks().count() }
    }

    override fun loadCategoryItem(): Flowable<List<CategoryItem>> {
        return categoryDao.selectAllCategory()
            .flatMap {
                Flowable.fromCallable {
                    it.asSequence()
                        .map {
                            CategoryItem(
                                it.guid,
                                it.name,
                                categoryRelationDao.selectCategoryRelationCount(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }

    override fun deleteCategory(category: Category): Completable {
        return categoryDao.deleteCategory(category)
    }
}