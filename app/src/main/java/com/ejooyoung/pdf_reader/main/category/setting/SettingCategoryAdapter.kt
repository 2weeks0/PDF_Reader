package com.ejooyoung.pdf_reader.main.category.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemSettingCategoryBinding) {
            settingCategoryItem = itemList[position]
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