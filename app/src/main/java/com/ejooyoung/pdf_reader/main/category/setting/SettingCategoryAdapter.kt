package com.ejooyoung.pdf_reader.main.category.setting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.dialog.MenuDialog
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemSettingCategoryBinding
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem

class SettingCategoryAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val itemList = arrayListOf<SettingCategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSettingCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemSettingCategoryBinding) {
            settingCategoryItem = itemList[position]
            layItem.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    DevLogger.d("pos => x: ${event.x + v.x}, y: ${event.y + v.y}")
                    MenuDialog.Factory(v.context, event.x + v.x, event.y + v.y)
                        .setItems(R.layout.item_menu_dialog, R.array.MENU_CATEGORY) { v: View, i: Int ->
                            DevLogger.i("$i clicked!")
                        }
                        .show()
                }
                return@setOnTouchListener false
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].id
    }

    fun setItem(itemList: List<SettingCategoryItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}