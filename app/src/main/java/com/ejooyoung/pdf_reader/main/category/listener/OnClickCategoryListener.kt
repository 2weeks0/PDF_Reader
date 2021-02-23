package com.ejooyoung.pdf_reader.main.category.listener

import android.view.View
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem
import com.ejooyoung.pdf_reader.main.model.CurrentCategory

interface OnClickCategoryListener {
    fun onTouchCategory(touchPosX: Float, touchPosY: Float)
    fun onClickCategory(view: View, currentCategory: CurrentCategory)
    fun onLongClickCategory(view: View, categoryItem: CategoryItem): Boolean
}