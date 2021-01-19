package com.ejooyoung.pdf_reader.main.category

import android.content.Context
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.CategoryDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class CategoryRepositoryImpl private constructor(
    private val categoryDao: CategoryDao,
    private val categoryRelationDao: CategoryRelationDao
) : CategoryRepository {

    companion object {
        fun newInstance(context: Context): CategoryRepository {
            return CategoryRepositoryImpl(
                DatabaseProvider.provideCategorySource(context),
                DatabaseProvider.provideCategoryRelationSource(context)
            )
        }
    }

    override fun loadCategoryItem(): Flowable<List<CategoryItem>> {
        return categoryDao.selectAllCategory()
            .flatMap {
                Flowable.fromCallable {
                    it.asSequence()
                        .map {
                            CategoryItem(
                                it.guid.hashCode().toLong(),
                                it.name,
                                categoryRelationDao.selectCategoryRelationCount(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }

    override fun saveCategory(category: Category): Completable {
        return categoryDao.insertCategory(category)
    }

    override fun deleteCategory(category: Category): Completable {
        return categoryDao.deleteCategory(category)
    }
}