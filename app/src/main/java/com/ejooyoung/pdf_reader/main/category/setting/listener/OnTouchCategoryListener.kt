package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

interface OnTouchCategoryListener: View.OnLongClickListener {
    fun onTouch(view: View, item: SettingCategoryItem, event: MotionEvent)
    fun onClick(view: View, btnRadio: RadioButton)
}