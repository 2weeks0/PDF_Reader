package com.ejooyoung.pdf_reader.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.databinding.ItemMainPdfBinding
import com.ejooyoung.pdf_reader.model.PDF
import com.ejooyoung.pdf_reader.util.widget.ViewHolder

class MainAdapter(private val clickListener: OnClickPdfItemListener) : RecyclerView.Adapter<ViewHolder<ViewDataBinding>>() {

    private val itemList: ArrayList<PDF> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewDataBinding> {
        val binding = ItemMainPdfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) {
        (holder.binding as ItemMainPdfBinding).pdf = itemList[position]
        holder.binding.clickListener = clickListener
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: ArrayList<PDF>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}