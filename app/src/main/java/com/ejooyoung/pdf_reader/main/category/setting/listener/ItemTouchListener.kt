package com.ejooyoung.pdf_reader.main.category.setting.listener

import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.widget.RadioButton
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.MenuDialog
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

class ItemTouchListener private constructor(
    private val menuDialogItemClickListener: MenuDialogItemClickListener
) : OnTouchCategoryListener {

    private lateinit var item: SettingCategoryItem
    private var touchPosX = 0f
    private var touchPosY = 0f

    companion object {
        fun newInstance(menuDialogItemClickListener: MenuDialogItemClickListener): ItemTouchListener {
            return ItemTouchListener(menuDialogItemClickListener)
        }
    }

    override fun onTouch(view: View, item: SettingCategoryItem, event: MotionEvent) {
        touchPosX = event.x
        touchPosY = event.y
        this.item = item
    }

    override fun onClick(view: View, btnRadio: RadioButton) {
        DevLogger.d()
        if (item.editMode.get()) {
            btnRadio.performClick()
        }
        else {
            showMenuDialog(view)
        }
    }

    private fun showMenuDialog(view: View) {
        MenuDialog.Factory(view.context, touchPosX + view.x, touchPosY + view.y)
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

    override fun onLongClick(view: View): Boolean {
        DevLogger.d()
        if (!item.editMode.get()) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
            menuDialogItemClickListener.onStartEditMode()
        }
        return true
    }
}