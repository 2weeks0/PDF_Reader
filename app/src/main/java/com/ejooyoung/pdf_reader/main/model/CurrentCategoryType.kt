package com.ejooyoung.pdf_reader.main.model

enum class CurrentCategoryType(
    val index: Int
) {
    ALL(0),
    FAVORITE(1),
    CUSTOM(2);

    companion object {
        fun indexOf(index: Int): CurrentCategoryType {
            return values()[index]
        }
    }
}