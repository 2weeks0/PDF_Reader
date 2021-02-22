package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.CategoryDao
import com.ejooyoung.pdf_reader.database.dao.CategoryRelationDao
import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class SettingCategoryRepositoryImpl private constructor(
    private val categoryDao: CategoryDao,
    private val categoryRelationDao: CategoryRelationDao
) : SettingCategoryRepository {

    companion object {
        fun newInstance(application: Application): SettingCategoryRepository {
            return SettingCategoryRepositoryImpl(
                DatabaseProvider.provideCategorySource(application),
                DatabaseProvider.provideCategoryRelationSource(application)
            )
        }
    }

    override fun selectAllCategory(): Flowable<List<SettingCategoryItem>> {
        return categoryDao.selectAllCategory()
            .flatMap {
                Flowable.fromCallable {
                    it.asSequence()
                        .map {
                            SettingCategoryItem(
                                it.guid,
                                it.name,
                                categoryRelationDao.selectCategoryRelationCount(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }

    override fun saveCategory(categoryName: String): Flowable<Boolean> {
        return Flowable.fromCallable {
            val contain =
                categoryDao.containCategory(categoryName)
                    .blockingFirst()
            return@fromCallable if (!contain) {
                categoryDao.insertCategory(Category.valueOf(categoryName, categoryDao.selectCount() + 1))
                    .subscribe()
                true
            } else {
                false
            }
        }
    }

    override fun deleteCategory(categoryGuid: String): Completable {
        return Completable.fromAction {
            categoryDao.deleteCategory(categoryGuid)
            categoryRelationDao.deleteCategoryRelationByCategoryGuid(categoryGuid)
        }
    }

    override fun updateCategory(categoryGuid: String, categoryName: String): Flowable<Boolean> {
        return Flowable.fromCallable {
            val contain = categoryDao.containCategory(categoryName)
                .blockingFirst()
            return@fromCallable if (!contain) {
                categoryDao.updateCategory(categoryGuid, categoryName).subscribe()
                true
            } else {
                false
            }
        }
    }
}