package com.ejooyoung.pdf_reader.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.bookshelf.listener.OnClickBookListener
import com.ejooyoung.pdf_reader.databinding.ItemBookBinding
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.base.widget.ViewHolder

class BookshelfAdapter(private val clickListener: OnClickBookListener) : RecyclerView.Adapter<ViewHolder<ViewDataBinding>>() {

    private val itemList: ArrayList<Book> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewDataBinding> {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) {
        (holder.binding as ItemBookBinding).book = itemList[position]
        holder.binding.onClickListener = clickListener
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: List<Book>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}