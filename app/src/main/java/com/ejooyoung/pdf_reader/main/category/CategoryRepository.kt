package com.ejooyoung.pdf_reader.main.category

import com.ejooyoung.pdf_reader.database.model.Category
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface CategoryRepository {
    fun loadCategoryItem(): Flowable<List<CategoryItem>>
    fun saveCategory(category: Category): Completable
    fun deleteCategory(category: Category): Completable
}