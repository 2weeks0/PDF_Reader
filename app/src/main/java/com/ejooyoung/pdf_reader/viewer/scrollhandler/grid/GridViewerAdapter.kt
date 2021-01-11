package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemGridThumbnailBinding
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.listener.GridViewerBinder
import com.ejooyoung.pdf_reader.viewer.scrollhandler.grid.listener.GridViewerClickListener

class GridViewerAdapter(
    private val glideRequest: RequestManager,
    viewModel: GridViewerViewModel
) : RecyclerView.Adapter<ViewHolder>() {

    private val loadFlags = mutableSetOf<Int>()
    private val itemList = MutableList<Bitmap?>(viewModel.book.lastPage) { null }
    private val currentPosition = viewModel.book.currentPage
    private val clickListener: GridViewerClickListener = viewModel
    private val binder: GridViewerBinder = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGridThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemGridThumbnailBinding) {
            pos = position
            isCurrentPosition = position == currentPosition
            clickListener = this@GridViewerAdapter.clickListener
            glideRequest = this@GridViewerAdapter.glideRequest
            bitmap = if (!loadFlags.contains(position)) {
                loadFlags.add(position)
                binder.onBindGridViewer(itemList, position) { notifyItemChanged(position) }
                null
            }
            else {
                itemList[position]
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        glideRequest.clear((holder.binding as ItemGridThumbnailBinding).iv)
    }
}