package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.MotionEvent
import android.view.View
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

interface OnTouchCategoryListener {
    fun onTouch(view: View, item: SettingCategoryItem, event: MotionEvent)
}