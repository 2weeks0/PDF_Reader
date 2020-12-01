package com.ejooyoung.pdf_reader.viewer.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.database.model.Contents
import com.ejooyoung.pdf_reader.databinding.ItemContentsBinding
import com.shockwave.pdfium.PdfDocument

class ContentsListAdapter(
    private val clickListener: (pageIdx: Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList: MutableList<Contents> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.binding as ItemContentsBinding).content = itemList[position]
        holder.binding.clickListener = clickListener
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: List<Contents>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}