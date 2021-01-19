package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.Category
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface CategoryAndRelationRepository {
    fun selectAllCategory(): Flowable<List<Category>>
    fun saveCategory(category: Category): Completable
    fun deleteCategory(category: Category): Completable
    fun selectCountCategoryRelation(categoryGuid: String): Int
}