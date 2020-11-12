package com.ejooyoung.pdf_reader.base.widget

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<T: ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)