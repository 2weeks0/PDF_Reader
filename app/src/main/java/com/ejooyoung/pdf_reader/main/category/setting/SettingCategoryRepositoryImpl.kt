package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Application
import com.ejooyoung.pdf_reader.base.repository.CategoryAndRelationRepositoryImpl
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
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
                                it.guid.hashCode().toLong(),
                                it.name,
                                categoryAndRelationRepository.selectCountCategoryRelation(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }
}