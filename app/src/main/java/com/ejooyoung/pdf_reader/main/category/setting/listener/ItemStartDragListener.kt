package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.MotionEvent
import com.ejooyoung.pdf_reader.base.widget.ViewHolder

interface ItemStartDragListener {
    fun onStartDrag(viewHolder: ViewHolder, event: MotionEvent)
}