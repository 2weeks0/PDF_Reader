package com.ejooyoung.pdf_reader.main.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemCategoryBinding
import com.ejooyoung.pdf_reader.main.category.listener.OnClickCategoryListener
import com.ejooyoung.pdf_reader.main.category.model.CategoryItem

class CategoryAdapter(
    private val onClickCategoryListener: OnClickCategoryListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList = arrayListOf<CategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemCategoryBinding) {
            categoryItem = itemList[position]
            onClickCategoryListener = this@CategoryAdapter.onClickCategoryListener
            layItem.setOnTouchListener { _, event ->
                this@CategoryAdapter.onClickCategoryListener.onTouchCategory(event.x, event.y)
                return@setOnTouchListener false
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].guid.hashCode().toLong()
    }

    fun setItem(itemList: List<CategoryItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}