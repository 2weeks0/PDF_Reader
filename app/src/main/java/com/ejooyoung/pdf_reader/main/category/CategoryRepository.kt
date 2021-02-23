package com.ejooyoung.pdf_reader.main.category

import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface CategoryRepository {
    fun loadCountOfAllBook(): Flowable<Int>
    fun loadCountOfFavoriteBook(): Flowable<Int>
    fun loadCategoryItem(): Flowable<List<CategoryItem>>
    fun deleteCategory(categoryGuid: String): Completable
    fun updateCategory(categoryGuid: String, categoryName: String): Flowable<Boolean>
}