package com.ejooyoung.pdf_reader.main.category.setting

import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import io.reactivex.rxjava3.core.Flowable

interface SettingCategoryRepository {
    fun selectAllCategory(): Flowable<List<SettingCategoryItem>>
    fun saveCategory(categoryName: String): Flowable<Boolean>
}