package com.ejooyoung.pdf_reader.main.category.model

import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.main.model.CurrentCategoryType

data class CategoryItem(
    val guid: String,
    val name: String,
    val count: Int
) {
    fun toCurrentCategory(): CurrentCategory {
        return CurrentCategory.valueOf(CurrentCategoryType.CUSTOM, guid, name)
    }
}