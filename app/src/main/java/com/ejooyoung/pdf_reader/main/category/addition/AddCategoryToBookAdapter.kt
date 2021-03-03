 package com.ejooyoung.pdf_reader.main.category.addition

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemAddCategoryToBookBinding
import com.ejooyoung.pdf_reader.main.category.addition.listener.OnClickAddableBookListener
import com.ejooyoung.pdf_reader.main.category.addition.model.AddableBook

class AddCategoryToBookAdapter(
    private val onClickAddableBookListener: OnClickAddableBookListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList = arrayListOf<AddableBook>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddCategoryToBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemAddCategoryToBookBinding) {
            addableBook = itemList[position]
            onClickAddableBookListener = this@AddCategoryToBookAdapter.onClickAddableBookListener
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return itemList[position].guid.hashCode().toLong()
    }

    fun setItem(itemList: List<AddableBook>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}