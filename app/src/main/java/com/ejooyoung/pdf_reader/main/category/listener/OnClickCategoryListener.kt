package com.ejooyoung.pdf_reader.main.category.listener

import android.view.View
import com.ejooyoung.pdf_reader.main.model.CurrentCategory

interface OnClickCategoryListener {
    fun onClickCategory(view: View, currentCategory: CurrentCategory)
}