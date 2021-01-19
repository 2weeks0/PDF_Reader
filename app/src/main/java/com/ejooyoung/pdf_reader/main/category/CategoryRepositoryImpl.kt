package com.ejooyoung.pdf_reader.main.category

import android.content.Context
import com.ejooyoung.pdf_reader.base.repository.CategoryAndRelationRepository
import com.ejooyoung.pdf_reader.base.repository.CategoryAndRelationRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class CategoryRepositoryImpl private constructor(
    private val categoryAndRelationRepository: CategoryAndRelationRepository
) : CategoryRepository {

    companion object {
        fun newInstance(context: Context): CategoryRepository {
            return CategoryRepositoryImpl(
                CategoryAndRelationRepositoryImpl.getInstance(context)
            )
        }
    }

    override fun loadCategoryItem(): Flowable<List<CategoryItem>> {
        return categoryAndRelationRepository.selectAllCategory()
            .flatMap {
                Flowable.fromCallable {
                    it.asSequence()
                        .map {
                            CategoryItem(
                                it.guid.hashCode().toLong(),
                                it.name,
                                categoryAndRelationRepository.selectCountCategoryRelation(it.guid)
                            )
                        }
                        .toList()
                }
            }
    }

    override fun saveCategory(category: Category): Completable {
        return categoryAndRelationRepository.saveCategory(category)
    }

    override fun deleteCategory(category: Category): Completable {
        return categoryAndRelationRepository.deleteCategory(category)
    }
}