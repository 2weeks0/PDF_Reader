package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import com.ejooyoung.pdf_reader.base.repository.CategoryAndRelationRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class SettingCategoryRepositoryImpl private constructor(
    application: Application
) : SettingCategoryRepository {

    private val categoryAndRelationRepository =
        CategoryAndRelationRepositoryImpl.getInstance(application)

    companion object {
        fun newInstance(application: Application): SettingCategoryRepository {
            return SettingCategoryRepositoryImpl(application)
        }
    }

    override fun selectAllCategory(): Flowable<List<SettingCategoryItem>> {
        return categoryAndRelationRepository.selectAllCategory()
            .flatMap {
                Flowable.fromCallable {
                    it.asSequence()
                        .map {
                            SettingCategoryItem(
                                it.guid,
                                it.name,
                                categoryAndRelationRepository.selectCountCategoryRelation(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }

    override fun saveCategory(categoryName: String): Flowable<Boolean> {
        return Flowable.fromCallable {
            val contain =
                categoryAndRelationRepository.containCategory(categoryName)
                    .blockingFirst()
            return@fromCallable if (!contain) {
                categoryAndRelationRepository.saveCategory(Category.valueOf(categoryName))
                    .subscribe()
                true
            } else {
                false
            }
        }
    }

    override fun deleteCategory(categoryGuid: String): Completable {
        return categoryAndRelationRepository.deleteCategoryAndRelationByCategoryGuid(categoryGuid)
    }

    override fun updateCategory(categoryGuid: String, categoryName: String): Flowable<Boolean> {
        return Flowable.fromCallable {
            val contain = categoryAndRelationRepository.containCategory(categoryName)
                .blockingFirst()
            return@fromCallable if (!contain) {
                categoryAndRelationRepository.updateCategory(categoryGuid, categoryName).subscribe()
                true
            }
            else {
                false
            }
        }
    }
}