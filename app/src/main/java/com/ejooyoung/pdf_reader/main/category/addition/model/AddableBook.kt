package com.ejooyoung.pdf_reader.main.category.addition.model

import androidx.databinding.ObservableBoolean

data class AddableBook(
    val guid: String,
    val name: String,
    val thumbnailGuid: String
) {
    val selected: ObservableBoolean = ObservableBoolean(false)

    fun setSelected(boolean: Boolean) {
        this.selected.set(boolean)
    }
}