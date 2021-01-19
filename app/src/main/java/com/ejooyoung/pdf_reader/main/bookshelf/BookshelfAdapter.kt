package com.ejooyoung.pdf_reader.main.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.main.bookshelf.listener.OnClickBookListener
import com.ejooyoung.pdf_reader.databinding.ItemBookBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.base.widget.ViewHolder

class BookshelfAdapter(
    private val clickListener: OnClickBookListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList: ArrayList<Book> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.binding as ItemBookBinding).book = itemList[position]
        holder.binding.onClickListener = clickListener
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = itemList.size

    override fun getItemId(position: Int): Long {
        return itemList[position].guid.hashCode().toLong()
    }

    fun setItem(itemList: List<Book>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}