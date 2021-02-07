package com.ejooyoung.pdf_reader.main.category.setting.model

import androidx.databinding.ObservableBoolean

data class SettingCategoryItem(
    val guid: String,
    val name: String,
    val count: Int
) {
    val editMode: ObservableBoolean = ObservableBoolean(false)
    val selected: ObservableBoolean = ObservableBoolean(false)

    fun setSelected(boolean: Boolean) {
        this.selected.set(boolean)
    }
}