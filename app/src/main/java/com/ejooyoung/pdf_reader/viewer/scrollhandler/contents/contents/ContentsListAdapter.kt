package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.database.model.Contents
import com.ejooyoung.pdf_reader.databinding.ItemContentsBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.ContentsClickListener

class ContentsListAdapter(
    private val contentsClickListener: ContentsClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList: MutableList<Contents> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.binding as ItemContentsBinding).content = itemList[position]
        holder.binding.contentsClickListener = contentsClickListener
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: List<Contents>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }
}