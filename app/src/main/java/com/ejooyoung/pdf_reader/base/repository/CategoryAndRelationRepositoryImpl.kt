package com.ejooyoung.pdf_reader.base.repository

import android.content.Context
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.CategoryDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.Category
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class CategoryAndRelationRepositoryImpl private constructor(
    private val categoryDao: CategoryDao,
    private val categoryRelationDao: CategoryRelationDao
) : CategoryAndRelationRepository {

    companion object {
        private var INSTANCE: CategoryAndRelationRepository? = null

        fun getInstance(context: Context): CategoryAndRelationRepository {
            if (INSTANCE == null) {
                INSTANCE = CategoryAndRelationRepositoryImpl(
                    DatabaseProvider.provideCategorySource(context),
                    DatabaseProvider.provideCategoryRelationSource(context)
                )
            }
            return INSTANCE!!
        }
    }

    override fun selectAllCategory(): Flowable<List<Category>> {
        return categoryDao.selectAllCategory()
    }

    override fun saveCategory(category: Category) {
        return categoryDao.insertCategory(category)
    }

    override fun deleteCategory(category: Category): Completable {
        return categoryDao.deleteCategory(category)
    }

    override fun selectCountCategoryRelation(categoryGuid: String): Int {
        return categoryRelationDao.selectCategoryRelationCount(categoryGuid)
    }

    override fun containCategory(categoryName: String): Flowable<Boolean> {
        return categoryDao.containCategory(categoryName)
    }
}