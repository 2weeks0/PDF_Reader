package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.View
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

interface MenuDialogItemClickListener {
    fun onChangeName(view: View, item: SettingCategoryItem)
    fun onDeleteItem(view: View, item: SettingCategoryItem)
    fun onStartEditMode()
}