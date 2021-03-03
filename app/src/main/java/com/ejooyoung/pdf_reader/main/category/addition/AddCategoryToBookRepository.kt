package com.ejooyoung.pdf_reader.main.category.addition

import com.ejooyoung.pdf_reader.main.category.addition.model.AddableBook
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface AddCategoryToBookRepository {
    fun loadAddableBookList(categoryGuid: String): Flowable<List<AddableBook>>
    fun saveAddToCategory(categoryGuid: String, bookGuidList: List<AddableBook>): Completable
}