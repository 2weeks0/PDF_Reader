package com.ejooyoung.pdf_reader.main

import android.content.Intent
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface MainRepository {
    fun loadCurrentCategory(): Flowable<CurrentCategory>
    fun saveCurrentCategory(currentCategory: CurrentCategory): Completable
    fun saveBook(data: Intent): Completable
}