package com.ejooyoung.pdf_reader.main.category.setting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemSettingCategoryBinding
import com.ejooyoung.pdf_reader.main.category.setting.listener.ItemStartDragListener
import com.ejooyoung.pdf_reader.main.category.setting.listener.OnItemMoveListener
import com.ejooyoung.pdf_reader.main.category.setting.listener.OnTouchCategoryListener
import com.ejooyoung.pdf_reader.main.category.setting.model.SettingCategoryItem
import java.util.*

class SettingCategoryAdapter(
    private val itemTouchListener: OnTouchCategoryListener,
    private val itemStartDragListener: ItemStartDragListener
) : RecyclerView.Adapter<ViewHolder>(), OnItemMoveListener {

    private val itemList = arrayListOf<SettingCategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSettingCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.btnChk.visibility = View.GONE
        return ViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemSettingCategoryBinding) {
            settingCategoryItem = itemList[position]
            onTouchCategoryListener = this@SettingCategoryAdapter.itemTouchListener
            layItem.setOnTouchListener { v, event ->
                itemTouchListener.onTouch(v, itemList[position], event)
                return@setOnTouchListener false
            }
            ivHandle.setOnTouchListener { v, event ->
                itemStartDragListener.onStartDrag(holder, event)
                return@setOnTouchListener false
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(itemList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].guid.hashCode().toLong()
    }

    fun setItem(itemList: List<SettingCategoryItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}