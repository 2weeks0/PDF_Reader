package com.ejooyoung.pdf_reader.bookshelf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.bookshelf.listener.OnClickPdfItemListener
import com.ejooyoung.pdf_reader.databinding.ItemMainPdfBinding
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.util.widget.ViewHolder

class BookshelfAdapter(private val clickListener: OnClickPdfItemListener) : RecyclerView.Adapter<ViewHolder<ViewDataBinding>>() {

    private val itemList: ArrayList<Book> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewDataBinding> {
        val binding = ItemMainPdfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) {
        (holder.binding as ItemMainPdfBinding).book = itemList[position]
        holder.binding.clickListener = clickListener
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: ArrayList<Book>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}