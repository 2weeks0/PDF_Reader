package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.MenuDialog
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

class ItemTouchListener private constructor(
    private val menuDialogItemClickListener: MenuDialogItemClickListener
) : OnTouchCategoryListener {

    private var timeActionDown: Long = 0

    companion object {
        fun newInstance(menuDialogItemClickListener: MenuDialogItemClickListener): ItemTouchListener {
            return ItemTouchListener(menuDialogItemClickListener)
        }
    }

    override fun onTouch(view: View, item: SettingCategoryItem, event: MotionEvent) {
        DevLogger.i("event: ${event.action}")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                view.isPressed = true
                timeActionDown = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                view.isPressed = false
                if (System.currentTimeMillis() - timeActionDown >= ViewConfiguration.getLongPressTimeout()) {
                    onLongClick()
                }
                else {
                    onClick(view, item, event)
                }
            }
        }
    }

    private fun onLongClick() {
        menuDialogItemClickListener.onStartEditMode()
    }

    private fun onClick(view: View, item: SettingCategoryItem, event: MotionEvent) {
        DevLogger.d()
        MenuDialog.Factory(view.context, event.x + view.x, event.y + view.y)
            .setItems(R.layout.item_menu_dialog, R.array.MENU_SETTING_CATEGORY) { v: View, i: Int ->
                when (i) {
                    MenuDialog.SETTING_CATEGORY_CHANGE_NAME ->
                        menuDialogItemClickListener.onChangeName(v, item)
                    MenuDialog.SETTING_CATEGORY_DELETE_ITEM ->
                        menuDialogItemClickListener.onDeleteItem(v, item)
                }
            }
            .show()
    }
}