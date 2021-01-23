package com.ejooyoung.pdf_reader.main.category.setting.model

import androidx.databinding.ObservableBoolean

data class SettingCategoryItem(
    val id: Long,
    val name: String,
    val count: Int
) {
    val selected: ObservableBoolean = ObservableBoolean()
}