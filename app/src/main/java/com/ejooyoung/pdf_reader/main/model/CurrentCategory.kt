package com.ejooyoung.pdf_reader.main.model

data class CurrentCategory(
    val type: CurrentCategoryType,
    val guid: String?,
    val name: String?
) {
    companion object {
        private val all = CurrentCategory(CurrentCategoryType.ALL, null, null)
        private val favorites = CurrentCategory(CurrentCategoryType.FAVORITE, null, null)

        @JvmStatic
        fun valueOfAll(): CurrentCategory {
            return all
        }

        @JvmStatic
        fun valueOfFavorite(): CurrentCategory {
            return favorites
        }

        fun valueOf(type: CurrentCategoryType, guid: String?, name: String?): CurrentCategory {
            return CurrentCategory(type, guid, name)
        }
    }
}