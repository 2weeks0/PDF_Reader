package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.View
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

interface MenuDialogItemClickListener {
    fun onAddCategoryToBook(view: View, item: SettingCategoryItem)
    fun onChangeName(view: View, item: SettingCategoryItem)
    fun onDeleteItem(item: SettingCategoryItem)
    fun onStartEditMode() {}
}